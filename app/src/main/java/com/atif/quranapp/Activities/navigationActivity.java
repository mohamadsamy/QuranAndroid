package com.atif.quranapp.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.atif.quranapp.Fragments.AllahNameFragment;
import com.atif.quranapp.Fragments.AzanReminderFragment;
import com.atif.quranapp.Fragments.ClanedarFragment;
import com.atif.quranapp.Fragments.DonationFragment;
import com.atif.quranapp.Fragments.HomeFragment;
import com.atif.quranapp.Fragments.QuranFragment;
import com.atif.quranapp.Fragments.RamzanFragment;
import com.atif.quranapp.R;

import static com.atif.quranapp.Helper.Shared.GLOBLE_NAVIGATION;

public class navigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GLOBLE_NAVIGATION = this;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        HomeFragment fragment = new HomeFragment();
        loadFragment(fragment);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment;
        Bundle bundle = new Bundle();
        switch (id){
            case R.id.nav_calendar:
                loadFragment(new ClanedarFragment());
                break;
            case R.id.nav_quran:
                loadFragment(new QuranFragment());
                break;
            case R.id.nav_name_allah:
                bundle.putInt("NAME_TYPE",0);
                fragment = new AllahNameFragment();
                fragment.setArguments(bundle);
                loadFragment(fragment);
                break;
            case R.id.nav_name_muhammad_saw:
                //Muhammad Saw Name
                bundle.putInt("NAME_TYPE",1);
                fragment = new AllahNameFragment();
                fragment.setArguments(bundle);
                loadFragment(fragment);
                break;
            case R.id.nav_azan:
                navigation.setSelectedItemId(R.id.navigation_azan);
                loadFragment(new AzanReminderFragment());
                break;
            case R.id.nav_donation:
                navigation.setSelectedItemId(R.id.navigation_donation);
                loadFragment(new DonationFragment());
                break;
            case R.id.nav_ramzam:
                loadFragment(new RamzanFragment());
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_azan:
                    loadFragment(new AzanReminderFragment());
                    return true;
                case R.id.navigation_qible:
                    return true;
                case R.id.navigation_donation:
                    loadFragment(new DonationFragment());
                    return true;
            }
            return false;
        }
    };

    public boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

}
