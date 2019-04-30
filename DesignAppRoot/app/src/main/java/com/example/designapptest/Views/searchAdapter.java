package com.example.designapptest.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.designapptest.R;

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
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            ViewHolder holder = new ViewHolder();

            holder.type = (TextView) view.findViewById(R.id.r_btn_search);

            view.setTag(holder);
        }

        ViewHolder holder = (ViewHolder) view.getTag();

        holder.type.setText(typeSearch[position]);

        return view;
    }

    class ViewHolder {
        TextView type;
    }
}
