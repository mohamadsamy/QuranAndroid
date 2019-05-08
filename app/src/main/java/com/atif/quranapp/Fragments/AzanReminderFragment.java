package com.atif.quranapp.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.atif.quranapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.atif.quranapp.Helper.Shared.ASAR;
import static com.atif.quranapp.Helper.Shared.DUHAR;
import static com.atif.quranapp.Helper.Shared.FAJAR;
import static com.atif.quranapp.Helper.Shared.ISHA;
import static com.atif.quranapp.Helper.Shared.MAGHIB;
import static com.atif.quranapp.Helper.Shared.SUNRISE;

public class AzanReminderFragment extends Fragment {
    public static final String inputFormat = "HH:mm";
    SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat, Locale.US);
    private Date date;
    private SharedPreferences sharedPreferences;
    private TextView tvPrayer,tvPrayerTime,tvFajar,tvSunrise,tvDuhar,tvAsar,tvMaghrib,tvIsha;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_azan, null);
        sharedPreferences = getContext().getSharedPreferences("PRAYER_SHARED", Context.MODE_PRIVATE);
        init(view);
        loadTimes();
        return view;
    }

    private void init(View view) {
        tvPrayer = view.findViewById(R.id.tvNextPrayer);
        tvPrayerTime = view.findViewById(R.id.tvPrayerTime);
        tvFajar = view.findViewById(R.id.tvFajar);
        tvSunrise = view.findViewById(R.id.tvSunrise);
        tvDuhar = view.findViewById(R.id.tvDuhar);
        tvAsar = view.findViewById(R.id.tvAsar);
        tvMaghrib = view.findViewById(R.id.tvMaghrib);
        tvIsha = view.findViewById(R.id.tvIsha);
    }

    private void loadTimes() {
        compareDates();
        tvFajar.setText("  "+FAJAR+"  ");
        tvSunrise.setText("  "+SUNRISE+"  ");
        tvDuhar.setText("  "+DUHAR+"  ");
        tvAsar.setText("  "+ASAR+"  ");
        tvMaghrib.setText("  "+MAGHIB+"  ");
        tvIsha.setText("  "+ISHA+"  ");
    }
    public void compareDates(){
        Calendar now = Calendar.getInstance();

        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);

        date = parseDate(hour + ":" + minute);
        if ( parseDate(FAJAR).before(date)) {
            tvPrayer.setText("SUNRISE");
            tvPrayerTime.setText(SUNRISE);
        }
        if (parseDate(SUNRISE).before(date)){
            tvPrayer.setText("DUHAR");
            tvPrayerTime.setText(DUHAR);
        }if (parseDate(DUHAR).before(date)){
            tvPrayer.setText("ASAR");
            tvPrayerTime.setText(ASAR);

        }if (parseDate(ASAR).before(date)){
            tvPrayer.setText("MAGHIB");
            tvPrayerTime.setText(MAGHIB);
        }if (parseDate(ISHA).before(date)){
            tvPrayer.setText("ISHA");
            tvPrayerTime.setText(SUNRISE);
        }if (parseDate(ISHA).before(date)){
            tvPrayer.setText("ISHA");
            tvPrayerTime.setText(ISHA);
        }
    }


    private Date parseDate(String date) {

        try {
            return inputParser.parse(date);
        } catch (java.text.ParseException e) {
            return new Date(0);
        }
    }
}
