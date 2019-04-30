package com.atif.quranapp.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.atif.quranapp.Model.Details;
import com.atif.quranapp.R;

import java.util.List;

public class DetailsAdapter extends ArrayAdapter<Details> {
    private List<Details> list;
    private Context context;

    public static class ViewHolder{
        TextView txtVerse,txtTranslation;
    }
    public DetailsAdapter(List<Details> list, Context context) {
        super(context, R.layout.item_details,list);
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
        Details model =getItem(position);
        final DetailsAdapter.ViewHolder viewHolder;
        View v;
        if (convertView == null){
            viewHolder = new DetailsAdapter.ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_details,parent,false);
            viewHolder.txtVerse = convertView.findViewById(R.id.tvVerse);
            Typeface customTypeFace = Typeface.createFromAsset(context.getAssets(),"font/pdmssaleemquranfont.ttf");
            viewHolder.txtVerse.setTypeface(customTypeFace);
            viewHolder.txtTranslation = convertView.findViewById(R.id.tvTranslation);
            Typeface customTranslationTypeFace = Typeface.createFromAsset(context.getAssets(),"font/jnn.ttf");
            viewHolder.txtTranslation.setTypeface(customTranslationTypeFace);

            v = convertView;
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (DetailsAdapter.ViewHolder) convertView.getTag();
            v = convertView;
        }
        if (model.getTxtAya().contains("بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيم")) {
            viewHolder.txtVerse.setGravity(Gravity.CENTER);
            viewHolder.txtTranslation.setGravity(Gravity.CENTER);
        } else {
            viewHolder.txtVerse.setGravity(Gravity.START);
            viewHolder.txtTranslation.setGravity(Gravity.START);
        }
        viewHolder.txtVerse.setText(model.getTxtAya());
        viewHolder.txtTranslation.setText(model.getTxtTranslation());
        return v;
    }

}

