package com.atif.quranapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.atif.quranapp.Activities.DetailsActivity;
import com.atif.quranapp.Adapter.HomeGridAdapter;
import com.atif.quranapp.Adapter.SurahAdapter;
import com.atif.quranapp.Helper.FileOpener;
import com.atif.quranapp.Model.Surah;
import com.atif.quranapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuranFragment extends Fragment {

    List<Surah> list = new ArrayList<>();
    Surah model;

    //UI Elements
    ListView listView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, null);
        init(view);
        parseData();
        return view;
    }
    private void init(View view) {
        listView = view.findViewById(R.id.deviceList);
        View header = getLayoutInflater().inflate(R.layout.surah_header_view,null);
        listView.addHeaderView(header);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DetailsActivity.class);
                intent.putExtra("SURAH_NUMBER",position);
                startActivity(intent);
            }
        });
    }
    private void parseData() {
        try
        {
            FileOpener opener = new FileOpener();
            String json = opener.loadJsonFromAssest("json_surah_list.json",getContext());
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
            listView.setAdapter(new SurahAdapter(list,getContext()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
