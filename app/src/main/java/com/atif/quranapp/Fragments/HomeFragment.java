package com.atif.quranapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.atif.quranapp.Adapter.HomeGridAdapter;
import com.atif.quranapp.R;

import in.srain.cube.views.GridViewWithHeaderAndFooter;


public class HomeFragment extends Fragment {

    GridViewWithHeaderAndFooter gridView;
    public static String[] categoryNames = {
            "Islamic Calendear",
            "Al-Quran",
            "Azan Reminder",
            "Donation",
            "Qibla",
            "360 View videos",
            "Ramzan Time table",
            "Daily Hades",
            "99 Names of Allah",
            "99 Names of Muhammad(ﷺ)",
            "Contact Us",
            "Direction to Mosque",
            "Daily Update",
            "Event Update",
            "Common Duas",
            "Namaz Rukhats",
            "How to Say Eid Salah",
            "Zakat Calculator"
    };
    public static int[] categoryImages = {
            R.drawable.ic_islamic_calender,
            R.drawable.ic_al_quran,
            R.drawable.ic_azan_reminder,
            R.drawable.ic_donation,
            R.drawable.ic_qibla,
            R.drawable.ic_360,
            R.drawable.ic_time_table,
            R.drawable.ic_daily_hades,
            R.drawable.ic_name_allah,
            R.drawable.ic_name_muhammad_saw,
            R.drawable.ic_contact,
            R.drawable.ic_direction,
            R.drawable.ic_daily_update,
            R.drawable.ic_event_updater,
            R.drawable.ic_dua,
            R.drawable.ic_namaz_ruakt,
            R.drawable.ic_eid,
            R.drawable.ic_zakat_calculator,
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, null);
        gridView = view.findViewById(R.id.gridHome);
        setGridViewHeaderAndFooter();
        gridView.setAdapter(new HomeGridAdapter(getContext(),categoryNames,categoryImages));
        return view;
    }
    private void setGridViewHeaderAndFooter() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View headerView = layoutInflater.inflate(R.layout.home_header_view, null, false);
        gridView.addHeaderView(headerView);

    }
}
