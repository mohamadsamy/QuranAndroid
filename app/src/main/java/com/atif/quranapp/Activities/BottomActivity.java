package com.atif.quranapp.Activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.atif.quranapp.R;

public class BottomActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private ActionBar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom);

        toolbar = getSupportActionBar();
        toolbar.setTitle("Anwar-e-Madina Grand Mosque");


    }



}
