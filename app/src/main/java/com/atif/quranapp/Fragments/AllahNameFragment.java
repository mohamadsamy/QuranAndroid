package com.atif.quranapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.atif.quranapp.Adapter.HomeGridAdapter;
import com.atif.quranapp.Adapter.NameGridAdapter;
import com.atif.quranapp.Helper.FileOpener;
import com.atif.quranapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AllahNameFragment extends Fragment {
    List<String> namesList = new ArrayList<>();

    GridView gridView;
    TextView tvHeader;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_allah_name, null);
        parseData(view);
        return view;
    }
    private void parseData(View view) {
        gridView = view.findViewById(R.id.gridName);
        tvHeader = view.findViewById(R.id.tvNameHeader);
        String name = "";
        Bundle bundle = this.getArguments();
        int choice = 0;
        if (bundle != null) {
            choice = bundle.getInt("NAME_TYPE",-1);
        }
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
            String json = opener.loadJsonFromAssest(name,getContext());
            JSONObject jsonobject = new JSONObject(json);
            JSONArray jarray = (JSONArray) jsonobject.getJSONArray("data");
            for(int i=0;i<jarray.length();i++)
            {
                JSONObject jb =(JSONObject) jarray.get(i);
                String asma = jb.getString("name");
                namesList.add(asma);
            }
            gridView.setAdapter(new NameGridAdapter(getContext(),namesList));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
