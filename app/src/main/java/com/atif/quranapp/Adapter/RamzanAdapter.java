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
import com.atif.quranapp.Model.TimeTable;
import com.atif.quranapp.R;

import java.util.List;

public class RamzanAdapter extends ArrayAdapter<TimeTable> {
    private List<TimeTable> list;
    private Context context;
    String currentDate;

    public static class ViewHolder{
        TextView txtDate;
        TextView txtRamzan;
        TextView txtSehri;
        TextView txtAftari;
    }
    public RamzanAdapter(List<TimeTable> list, Context context,String currentDate) {
        super(context, R.layout.item_ramzan,list);
        this.list = list;
        this.context = context;
        this.currentDate = currentDate;
    }
    @Override
    public int getCount() {
        return list.size();
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TimeTable model =getItem(position);
        RamzanAdapter.ViewHolder viewHolder;
        View v;
        if (convertView == null){
            viewHolder = new RamzanAdapter.ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_ramzan,parent,false);
            viewHolder.txtDate = convertView.findViewById(R.id.tvRamzanDate);
            viewHolder.txtRamzan = convertView.findViewById(R.id.tvRamzanHDate);
            viewHolder.txtSehri = convertView.findViewById(R.id.tvRamzanSehri);
            viewHolder.txtAftari = convertView.findViewById(R.id.tvRamzanAftari);
            v = convertView;
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (RamzanAdapter.ViewHolder) convertView.getTag();
            v = convertView;
        }
        if (model.getRamzan().equals(currentDate)){
            viewHolder.txtDate.setTextColor(context.getResources().getColor(R.color.secondyColor));
            viewHolder.txtRamzan.setTextColor(context.getResources().getColor(R.color.secondyColor));
            viewHolder.txtSehri.setTextColor(context.getResources().getColor(R.color.secondyColor));
            viewHolder.txtAftari.setTextColor(context.getResources().getColor(R.color.secondyColor));
        } else {
            viewHolder.txtDate.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            viewHolder.txtRamzan.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            viewHolder.txtSehri.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            viewHolder.txtAftari.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }
        viewHolder.txtDate.setText(model.getDate());
        viewHolder.txtRamzan.setText(model.getRamzan());
        viewHolder.txtSehri.setText(model.getSehri());
        viewHolder.txtAftari.setText(model.getAftari());
        return v;
    }

}
