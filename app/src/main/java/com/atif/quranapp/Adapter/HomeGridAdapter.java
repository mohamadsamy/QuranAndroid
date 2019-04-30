package com.atif.quranapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.atif.quranapp.Activities.AllahNameActivity;
import com.atif.quranapp.Activities.HomeActivity;
import com.atif.quranapp.Activities.IslamicCalendarActivity;
import com.atif.quranapp.Activities.MainActivity;
import com.atif.quranapp.Fragments.AllahNameFragment;
import com.atif.quranapp.Fragments.AzanReminderFragment;
import com.atif.quranapp.Fragments.ClanedarFragment;
import com.atif.quranapp.Fragments.CompassFragment;
import com.atif.quranapp.Fragments.ContactFragment;
import com.atif.quranapp.Fragments.DonationFragment;
import com.atif.quranapp.Fragments.QuranFragment;
import com.atif.quranapp.Fragments.RamzanFragment;
import com.atif.quranapp.Fragments.VideoFragment;
import com.atif.quranapp.Fragments.ZakatFragment;
import com.atif.quranapp.R;

import static com.atif.quranapp.Helper.Shared.GLOBLE_NAVIGATION;

public class HomeGridAdapter extends BaseAdapter{

    String [] names;
    Context context;
    int [] images;
    private static LayoutInflater inflater=null;
    public HomeGridAdapter(Context homeActivity, String[] names, int[] images) {
        this.names=names;
        context=homeActivity;
        this.images=images;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder
    {
        TextView _text;
        ImageView  _img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.item_home_grid, null);
        holder._text =(TextView) rowView.findViewById(R.id.tvHome);
        holder._img =(ImageView) rowView.findViewById(R.id.ivHome);

        holder._text.setText(names[position]);
        holder._img.setImageResource(images[position]);

        rowView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent;
                Fragment fragment;
                Bundle bundle = new Bundle();
                switch (position){
                    case 0:
                        //islamic calendar
                        GLOBLE_NAVIGATION.loadFragment(new ClanedarFragment());
                        break;
                    case 1:
                        //Al-Quran
                        GLOBLE_NAVIGATION.loadFragment(new QuranFragment());
                        break;
                    case 2:
                        GLOBLE_NAVIGATION.navigation.setSelectedItemId(R.id.navigation_azan);
                        GLOBLE_NAVIGATION.loadFragment(new AzanReminderFragment());
                        break;
                    case 3:
                        GLOBLE_NAVIGATION.navigation.setSelectedItemId(R.id.navigation_donation);
                        GLOBLE_NAVIGATION.loadFragment(new DonationFragment());
                        break;
                    case 4:
                        GLOBLE_NAVIGATION.loadFragment(new CompassFragment());
                        break;
                    case 5:
                        GLOBLE_NAVIGATION.loadFragment(new VideoFragment());
                        break;
                    case 6:
                        GLOBLE_NAVIGATION.loadFragment(new RamzanFragment());
                        break;
                    case 7:

                        break;
                    case 8:
                        //Allah Name
                        bundle.putInt("NAME_TYPE",0);
                        fragment = new AllahNameFragment();
                        fragment.setArguments(bundle);
                        GLOBLE_NAVIGATION.loadFragment(fragment);
                        break;
                    case 9:
                        //Muhammad Saw Name
                        bundle.putInt("NAME_TYPE",1);
                        fragment = new AllahNameFragment();
                        fragment.setArguments(bundle);
                        GLOBLE_NAVIGATION.loadFragment(fragment);
                        break;
                    case 10:
                        GLOBLE_NAVIGATION.loadFragment(new ContactFragment());
                        break;
                    case 11:
                        break;
                    case 12:
                        break;
                    case 13:
                        break;
                    case 14:
                        break;
                    case 15:
                        break;
                    case 16:
                        break;
                    case 17:
                        GLOBLE_NAVIGATION.loadFragment(new ZakatFragment());
                        break;

                }

            }
        });

        return rowView;
    }

}