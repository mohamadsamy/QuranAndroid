package com.atif.quranapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.atif.quranapp.Adapter.SurahAdapter;
import com.atif.quranapp.Helper.FileOpener;
import com.atif.quranapp.Model.Surah;
import com.atif.quranapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Surah> list = new ArrayList<>();
    Surah model;

    //UI Elements
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {    
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        parseData();
    }

    private void init() {
        listView = findViewById(R.id.deviceList);
        View header = getLayoutInflater().inflate(R.layout.surah_header_view,null);
        listView.addHeaderView(header);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),DetailsActivity.class);
                intent.putExtra("SURAH_NUMBER",position);
                startActivity(intent);
            }
        });
    }

    private void parseData() {
        try
        {
            FileOpener opener = new FileOpener();
            String json = opener.loadJsonFromAssest("json_surah_list.json",getApplicationContext());
            JSONObject jsonobject = new JSONObject(json);
            JSONArray jarray = (JSONArray) jsonobject.getJSONArray("data");
            for(int i=0;i<jarray.length();i++)
            {
                JSONObject jb =(JSONObject) jarray.get(i);
                String name = jb.getString("title");
                model = new Surah();
                model.setSurah_name(name);
                list.add(model);
                Log.e("Surah_Title",name);
            }
            listView.setAdapter(new SurahAdapter(list,getApplicationContext()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
