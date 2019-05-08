package com.atif.quranapp.Fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.atif.quranapp.Adapter.HomeGridAdapter;
import com.atif.quranapp.R;
import com.atif.quranapp.Reciever.AlamBroadCast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import in.srain.cube.views.GridViewWithHeaderAndFooter;

import static com.atif.quranapp.Helper.Shared.FAJAR;
import static com.atif.quranapp.Helper.Shared.*;


public class HomeFragment extends Fragment {

    GridViewWithHeaderAndFooter gridView;
    SharedPreferences sharedPreferences;
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
    private Date date;
    private LocalDateTime inputParserpublic;
    static final String inputFormat = "HH:mm";
    SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat, Locale.US);
    private TextView tvPrayer,tvPrayerTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, null);
        gridView = view.findViewById(R.id.gridHome);
        setGridViewHeaderAndFooter();
        gridView.setAdapter(new HomeGridAdapter(getContext(),categoryNames,categoryImages));
        sharedPreferences = getContext().getSharedPreferences("PRAYER_SHARED",Context.MODE_PRIVATE);
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");
        String formattedDate = df.format(c);
        Log.e("Date",formattedDate);
        TODAY_DAY = formattedDate;
        loadAlarm();
        if(!sharedPreferences.getBoolean(formattedDate,false)){
            loadAlarm();
        } else {
            loadTime(formattedDate);
        }
        //setAlarm();
        return view;
    }

    private void loadTime(String date) {

        FAJAR = sharedPreferences.getString(date+"-Fajr","");
        SUNRISE = sharedPreferences.getString(date+"-Sunrise","");
        DUHAR = sharedPreferences.getString(date+"-Dhuhr","");
        ASAR = sharedPreferences.getString(date+"-Asr","");
        MAGHIB = sharedPreferences.getString(date+"-Maghrib","");
        ISHA = sharedPreferences.getString(date+"-Isha","");
        compareDates();
    }

    private void loadAlarm() {
        //http://api.aladhan.com/v1/calendarByCity?city=sargodha&country=pakistan
        StringRequest request = new StringRequest(Request.Method.GET, "http://api.aladhan.com/v1/calendarByCity?city=sargodha&country=pakistan",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("data");
                            for (int i=0;i<array.length();i++){
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                JSONObject jsonObject = (JSONObject) array.get(i);
                                JSONObject jsonObject1 = (JSONObject) array.get(i);
                                String outerArray = jsonObject.getString("timings");
                                jsonObject = new JSONObject(outerArray);
                                String dateArray = jsonObject1.getString("date");
                                jsonObject1 = new JSONObject(dateArray);
                                String date = jsonObject1.getString("readable");
                                Long timestamp = jsonObject1.getLong("timestamp");
                                Log.e("timestamp", String.valueOf(timestamp));
                                setAlarm(timestamp);
                                String time = jsonObject.getString("Fajr");
                                editor.putString(date+"-Fajr",trim(time));
                                time = jsonObject.getString("Sunrise");
                                editor.putString(date+"-Sunrise",trim(time));
                                time = jsonObject.getString("Dhuhr");
                                editor.putString(date+"-Dhuhr",trim(time));
                                time = jsonObject.getString("Asr");
                                editor.putString(date+"-Asr",trim(time));
                                time = jsonObject.getString("Maghrib");
                                editor.putString(date+"-Maghrib",trim(time));
                                time = jsonObject.getString("Isha");
                                editor.putString(date+"-Isha",trim(time));
                                editor.putBoolean(date,true);
                                editor.commit();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }

    private void setAlarm(Long timestamp) {
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlamBroadCast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),0,intent,0);
        alarmManager.setRepeating(AlarmManager.RTC, timestamp,AlarmManager.RTC_WAKEUP,pendingIntent);
    }
    private void setGridViewHeaderAndFooter() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View headerView = layoutInflater.inflate(R.layout.home_header_view, null, false);
        gridView.addHeaderView(headerView);
        tvPrayer = headerView.findViewById(R.id.tvNextPrayer);
        tvPrayerTime = headerView.findViewById(R.id.tvPrayerTime);
    }
    public String trim(String string){
        String[] array = string.split(" ");
        return  array[0];
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
