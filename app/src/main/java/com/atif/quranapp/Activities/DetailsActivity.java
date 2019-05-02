package com.atif.quranapp.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.atif.quranapp.Adapter.DetailsAdapter;
import com.atif.quranapp.Adapter.SurahAdapter;
import com.atif.quranapp.Helper.FileOpener;
import com.atif.quranapp.Helper.mp3Play;
import com.atif.quranapp.Model.Details;
import com.atif.quranapp.Model.Surah;
import com.atif.quranapp.R;
import com.atif.quranapp.Reciever.DownloadReceiver;
import com.atif.quranapp.Services.DownloadService;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.builder.MultiFileDownloader;
import com.krishna.fileloader.listener.MultiFileDownloadListener;
import com.krishna.fileloader.request.MultiFileLoadRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class DetailsActivity extends AppCompatActivity implements MediaPlayer.OnBufferingUpdateListener,MediaPlayer.OnCompletionListener {
    public List<Details> list = new ArrayList<>();
    public List<Integer> durationList = new ArrayList<>();
    public List<String> playlist = new ArrayList<>();
    public ArrayList<String> nameList = new ArrayList<>();
    Details model;
    int surah_number;
    int i=0;
    public int downloadingIndex = 0;

    public static String PUBLIC_SHARED_PREF = "quran_pref";

    public static String link = "http://192.168.8.102/";
    public static String mainLink = "https://download.quranicaudio.com/verses/Alafasy/mp3/";

    //UI Elements
    ListView listView;
    public ImageView btn_play_pause;
    public SeekBar seekBar;
    public TextView tvDuration,tvTotal;

    public MediaPlayer mediaPlayer;
    public int mediaFileLength;
    public int realtimeLength;
    final Handler handler = new Handler();
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},19);
        }
        init();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 19:
                parseData();
                inilizeAudio();
                break;
        }
    }

    private void init() {
        surah_number = getIntent().getIntExtra("SURAH_NUMBER",1);
        listView = findViewById(R.id.detailList);

        seekBar = (SeekBar)findViewById(R.id.seekBar);
        seekBar.setMax(99);
        tvDuration = (TextView)findViewById(R.id.tvDuration);
        tvTotal = (TextView)findViewById(R.id.tvTotalDuration);

        btn_play_pause = findViewById(R.id.ivPlay);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnCompletionListener(this);


    }

    private void inilizeAudio() {
        StringRequest request = new StringRequest(Request.Method.GET, "http://staging.quran.com:3000/api/v3/chapters/" +
                surah_number + "/verses?recitation=7&translations=21&language=en&page=" + "2" + "&limit=50&text_type=words",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("verses");
                            for (int i=0;i<array.length();i++){
                                JSONObject jsonObject = (JSONObject) array.get(i);
                                String outerArray = jsonObject.getString("audio");
                                jsonObject = new JSONObject(outerArray);
                                String url = jsonObject.getString("url");
                                int duration = jsonObject.getInt("duration");
                                durationList.add(duration);
                            }
                            /*mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(playlist.get(0)));
                            //mediaPlayer.start();
                            mediaPlayer = MediaPlayer.create(getApplicationContext(),Uri.parse(playlist.get(++i)));
                            timer = new Timer();
                            if (playlist.size()>1) playNext();*/
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    private void parseData() {
        try
        {
            FileOpener opener = new FileOpener();
            String json = opener.loadJsonFromAssest("surah_"+surah_number+".json",getApplicationContext());
            String t_json = opener.loadJsonFromAssest("t_surah_"+surah_number+".json",getApplicationContext());
            JSONObject jsonobject = new JSONObject(json);
            JSONObject t_jsonobject = new JSONObject(t_json);
            JSONArray jarray = jsonobject.getJSONArray("data");
            JSONArray t_jarray = t_jsonobject.getJSONArray("data");
            JSONObject jb =(JSONObject) jarray.get(0);
            JSONObject verseAray = jb.getJSONObject("verse");

            int index = 0;
            boolean ayaDone = false;
            for(int i=0;i<verseAray.length();i++)
            {
                model = new Details();
                JSONObject tObject =(JSONObject) t_jarray.get(index);
                String verse = verseAray.getString("verse_"+String.valueOf(i))+" \u06DD";
                int aya = tObject.getInt("aya");
                String translation;
                if(aya == 1 && surah_number != 1 && !ayaDone){
                    durationList.add(6);
                    playlist.add(mainLink+"001001.mp3");
                    translation = "شروع الله کا نام لے کر جو بڑا مہربان نہایت رحم والا ہے";
                    ayaDone = true;
                } else {
                    nameList.add(String.format("%03d",surah_number)+String.format("%03d",aya)+".mp3");
                    playlist.add(mainLink+String.format("%03d",surah_number)+String.format("%03d",aya)+".mp3");
                    translation = tObject.getString("text");
                    index++;
                }
                model.setTxtTranslation(translation);
                model.setTxtAya(verse);
                list.add(model);
            }
            listView.setAdapter(new DetailsAdapter(list,getApplicationContext()));
            /*new CountDownTimer(1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    for (int i=0;i<list.size();i++){
                        Details d = list.get(i);
                        String txt = d.getTxtAya();
                        durationList.add(txt.length());
                    }
                }
            }.start();*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void playNext() {
        mediaPlayer.reset();
        new mp3Play(DetailsActivity.this).execute(getFilesDir() + "/" + nameList.get(++i));
        /*timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mediaPlayer.start();
                if (playlist.size() > i+1) {
                    try {
                        mediaPlayer.reset();
                        mediaPlayer = MediaPlayer.create(getApplicationContext(),Uri.parse(playlist.get(++i)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    playNext();
                }
            }
        },durationList.get(i-1));*/
    }

    public void recurive(final int index){
        /*updateView(index);
        new CountDownTimer(durationList.get(index)*100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (index<durationList.size()-1) {
                    recurive(index+1);
                }
            }
        }.start();*/
    }
    public void updateView(int index){

        if (listView.getLastVisiblePosition()-2 >= index){
            listView.smoothScrollToPosition(index+3);
        }

        View v = listView.getChildAt(index -
                listView.getFirstVisiblePosition());

        if(v == null)
            return;


        TextView tv = (TextView) v.findViewById(R.id.tvVerse);
        tv.setTextColor(getResources().getColor(R.color.colorPrimary));

        TextView tvTranslation = v.findViewById(R.id.tvTranslation);
        tvTranslation.setTextColor(getResources().getColor(R.color.colorPrimary));

        try {
            View v1 = listView.getChildAt(index-1 -
                    listView.getFirstVisiblePosition());

            if(v1 == null)
                return;

            TextView tv1 = (TextView) v1.findViewById(R.id.tvVerse);
            tv1.setTextColor(getResources().getColor(R.color.black));

            TextView tvTranslation1 = v1.findViewById(R.id.tvTranslation);
            tvTranslation1.setTextColor(getResources().getColor(R.color.black));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }



    }

    public void play(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences(PUBLIC_SHARED_PREF, Context.MODE_PRIVATE);
        int j = 0 ;
        for (String name:nameList){
            if(!sharedPreferences.getBoolean(name,false)){
                Toast.makeText(getApplicationContext(), "Not Downloaded "+name, Toast.LENGTH_SHORT).show();
                downloadingIndex = j;
                downloadMedia();
                break;
            }
            j++;
        }


        new mp3Play(DetailsActivity.this).execute(getFilesDir() + "/" + nameList.get(0));

    }

    public void downloadMedia(){
        Intent intent = new Intent(this, DownloadService.class);
        intent.putExtra("url", playlist.get(downloadingIndex));
        intent.putExtra("name", nameList.get(downloadingIndex));
        Log.e("Downloading....",nameList.get(downloadingIndex));
        intent.putExtra("receiver", new DownloadReceiver(new Handler(),DetailsActivity.this));
        startService(intent);
        downloadingIndex = downloadingIndex+1;
    }

    public void updateSeekBar() {
        seekBar.setProgress((int)(((float)mediaPlayer.getCurrentPosition() / mediaFileLength)*100));
        if ((int)(((float)mediaPlayer.getCurrentPosition() / mediaFileLength)*100)>99) {
            if (i<nameList.size()) {
                playNext();
            }
        }
        if(mediaPlayer.isPlaying())
        {
            Runnable updater = new Runnable() {
                @Override
                public void run() {
                    try {
                        updateSeekBar();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    realtimeLength-=1000;
                    tvDuration.setText(String.format("%d:%d",TimeUnit.MILLISECONDS.toMinutes(realtimeLength),
                            TimeUnit.MILLISECONDS.toSeconds(realtimeLength) -
                                    TimeUnit.MILLISECONDS.toSeconds(TimeUnit.MILLISECONDS.toMinutes(realtimeLength))));

                }

            };
            handler.postDelayed(updater,1000); // 1 second
        }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        seekBar.setSecondaryProgress(percent);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        btn_play_pause.setImageResource(R.drawable.ic_play_button);

    }
}

