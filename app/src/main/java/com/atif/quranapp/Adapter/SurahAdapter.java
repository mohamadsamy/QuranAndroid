package com.atif.quranapp.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.atif.quranapp.Model.Surah;
import com.atif.quranapp.R;

import java.util.ArrayList;
import java.util.List;

public class SurahAdapter extends ArrayAdapter<Surah> {
    private List<Surah> list;
    private Context context;

    public static class ViewHolder{
        TextView txtName;
    }
    public SurahAdapter(List<Surah> list, Context context) {
        super(context, R.layout.item_surah_list,list);
        this.list = list;
        this.context = context;
    }
    @Override
    public int getCount() {
        return list.size();
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Surah model =getItem(position);
        SurahAdapter.ViewHolder viewHolder;
        View v;
        if (convertView == null){
            viewHolder = new SurahAdapter.ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_surah_list,parent,false);
            viewHolder.txtName = convertView.findViewById(R.id.tvSurahName);
            Typeface customTypeFace = Typeface.createFromAsset(context.getAssets(),"font/abel.ttf");
            viewHolder.txtName.setTypeface(customTypeFace);
            v = convertView;
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (SurahAdapter.ViewHolder) convertView.getTag();
            v = convertView;
        }
        viewHolder.txtName.setText(model.getSurah_name());
        return v;
    }

}
