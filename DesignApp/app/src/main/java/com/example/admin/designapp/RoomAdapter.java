package com.example.admin.designapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RoomAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<ClassRoom> room;

    public RoomAdapter(Context context, int layout, List<ClassRoom> room) {
        this.context = context;
        this.layout = layout;
        this.room = room;
    }

    @Override
    public int getCount() {
        return this.room.size();
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
        ImageView hinh=(ImageView) convertView.findViewById(R.id.imgRoom);
        TextView ten=(TextView) convertView.findViewById(R.id.txtName);
        TextView gia=(TextView) convertView.findViewById(R.id.txtPrice);
        TextView diachi=(TextView) convertView.findViewById(R.id.txtAddress);
        //gan gia tri
        ClassRoom itemroom=room.get(position);
        hinh.setImageResource(itemroom.getImage());
        ten.setText(itemroom.getName());
        gia.setText(itemroom.getPrice());
        diachi.setText(itemroom.getAddress());

        return convertView;
    }
}
