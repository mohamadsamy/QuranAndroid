package com.atif.quranapp.Helper;

import android.os.AsyncTask;

import com.atif.quranapp.Activities.DetailsActivity;
import com.atif.quranapp.R;

import java.util.concurrent.TimeUnit;

public class mp3Play extends AsyncTask<String, String, String> {
    DetailsActivity detailsActivity;

    public mp3Play(DetailsActivity detailsActivity) {
        this.detailsActivity = detailsActivity;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... params) {
        try{
            detailsActivity.mediaPlayer.setDataSource(params[0]);
            detailsActivity.mediaPlayer.prepare();
        }
        catch (Exception ex)
        {

        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        detailsActivity.mediaFileLength = detailsActivity.mediaPlayer.getDuration();
        detailsActivity.realtimeLength = detailsActivity.mediaFileLength;
        detailsActivity.tvTotal.setText(String.format("%2d:%2d", TimeUnit.MILLISECONDS.toMinutes((long) detailsActivity.totalDuration),
                TimeUnit.MILLISECONDS.toSeconds((long) detailsActivity.totalDuration) -
                        TimeUnit.MILLISECONDS.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) detailsActivity.totalDuration))));
        if(!detailsActivity.mediaPlayer.isPlaying())
        {
            detailsActivity.mediaPlayer.start();
            detailsActivity.recurive(0);
            detailsActivity.btn_play_pause.setImageResource(R.drawable.ic_pause_button);
        }
        detailsActivity.updateSeekBar();

    }
}