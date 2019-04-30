package com.atif.quranapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;

import com.atif.quranapp.Adapter.HomeGridAdapter;
import com.atif.quranapp.Adapter.NameGridAdapter;
import com.atif.quranapp.Adapter.SurahAdapter;
import com.atif.quranapp.Helper.FileOpener;
import com.atif.quranapp.Model.Surah;
import com.atif.quranapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AllahNameActivity extends AppCompatActivity {

    List<String> namesList = new ArrayList<>();

    GridView gridView;
    TextView tvHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allah_name);
        parseData();
    }
    private void parseData() {
        gridView = findViewById(R.id.gridName);
        tvHeader = findViewById(R.id.tvNameHeader);
        String name = "";
        int choice = getIntent().getIntExtra("NAME_TYPE",-1);
        switch (choice){
            case 0:
                name = "json_names_allah.json";
                tvHeader.setText("99 Names of Allah");
                break;
            case 1:
                name = "json_names_muhammad_saw.json";
                tvHeader.setText("99 Names of Muhammad(ﷺ)");
                break;
        }
        try
        {
            FileOpener opener = new FileOpener();
            String json = opener.loadJsonFromAssest(name,getApplicationContext());
            JSONObject jsonobject = new JSONObject(json);
            JSONArray jarray = (JSONArray) jsonobject.getJSONArray("data");
            for(int i=0;i<jarray.length();i++)
            {
                JSONObject jb =(JSONObject) jarray.get(i);
                String asma = jb.getString("name");
                namesList.add(asma);
            }
            gridView.setAdapter(new NameGridAdapter(this,namesList));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
