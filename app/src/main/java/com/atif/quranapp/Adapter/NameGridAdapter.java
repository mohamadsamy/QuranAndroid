package com.atif.quranapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.atif.quranapp.Activities.AllahNameActivity;
import com.atif.quranapp.Activities.HomeActivity;
import com.atif.quranapp.Activities.MainActivity;
import com.atif.quranapp.R;

import java.util.List;

public class NameGridAdapter extends BaseAdapter{

    List<String> names;
    Context context;
    private static LayoutInflater inflater=null;
    public NameGridAdapter(Context homeActivity, List<String> names) {
        this.names=names;
        context=homeActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return names.size();
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

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        NameGridAdapter.Holder holder=new NameGridAdapter.Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.item_name, null);
        holder._text =(TextView) rowView.findViewById(R.id.tvName);

        holder._text.setText(names.get(position));

        return rowView;
    }

}