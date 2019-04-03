package com.example.designapptest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class searchAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    String[] typeSearch;

    public searchAdapter(Context context, int layout, String[] typeSearch) {
        this.context = context;
        this.layout = layout;
        this.typeSearch = typeSearch;
    }

    @Override
    public int getCount() {
        return typeSearch.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView= inflater.inflate(layout,null);
        //anh xa
        TextView type=(TextView) convertView.findViewById(R.id.r_btn_search);
        //gan gia tri
        type.setText(typeSearch[position]);
        return convertView;
    }
}
