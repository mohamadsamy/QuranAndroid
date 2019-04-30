package com.atif.quranapp.Fragments;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.atif.quranapp.Adapter.RamzanAdapter;
import com.atif.quranapp.Model.Surah;
import com.atif.quranapp.Model.TimeTable;
import com.atif.quranapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RamzanFragment extends Fragment {
    List<TimeTable> list = new ArrayList<>();
    String[] listSehri = {
            "03:41 am","03:40 am","03:41 am","03:38 am","03:37 am","03:35 am","03:34 am","03:32 am","03:31 am","03:30 am",
            "03:29 am","03:28 am","03:27 am","03:26 am","03:25 am","03:24 am","03:24 am","03:23 am","03:22 am","03:21 am",
            "03:20 am","03:20 am","03:19 am","03:18 am","03:18 am","03:17 am","03:16 am","03:16 am","03:15 am","03:15 am",
    };
    String[] listAftari = {
            "06:55 pm","06:56 pm","06:57 pm","06:58 pm","06:58 pm","06:59 pm","07:00 pm","07:01 pm","07:01 pm","07:02 pm",
            "07:03 pm","07:04 pm","07:04 pm","07:05 pm","07:06 pm","07:07 pm","07:07 pm","07:08 pm","07:09 pm","07:09 pm",
            "07:10 pm","07:11 pm","07:11 pm","07:12 pm","07:13 pm","07:13 pm","07:14 pm","07:14 pm","07:15 pm","07:16 pm",
    };
    TimeTable model;
    String dt = "05-05-2019";
    ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_ramzan, null);
        init(view);
        loadList();
        return view;
    }

    private void init(View view) {
        listView = view.findViewById(R.id.ramzanList);
    }

    private void loadList() {
        for (int i=0;i<listSehri.length;i++){
            model = new TimeTable();
            model.setRamzan(String.valueOf(i+1));
            model.setSehri(listSehri[i]);
            model.setAftari(listAftari[i]);
            String[] dateArray = dateFormate().split("-");
            model.setDate(dateArray[0]);
            list.add(model);
        }
        listView.setAdapter(new RamzanAdapter(list,getContext(),"1"));
    }
    private String dateFormate(){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(dt));
            c.add(Calendar.DATE, 1);
            dt = sdf.format(c.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dt;
    }

}