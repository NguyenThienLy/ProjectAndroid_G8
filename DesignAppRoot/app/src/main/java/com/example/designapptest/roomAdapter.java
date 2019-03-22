package com.example.designapptest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class roomAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<roomModel> room;

    public roomAdapter(Context context, int layout, List<roomModel> room) {
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
        ImageView img=(ImageView) convertView.findViewById(R.id.img_room);
        TextView name=(TextView) convertView.findViewById(R.id.txt_name);
        TextView price=(TextView) convertView.findViewById(R.id.txt_price);
        TextView address=(TextView) convertView.findViewById(R.id.txt_address);
        TextView quantityMember = (TextView) convertView.findViewById(R.id.txt_quantityMember);
        TextView quantityComment = (TextView) convertView.findViewById(R.id.txt_quantityComment);
        TextView type = (TextView) convertView.findViewById(R.id.txt_type);
        //gan gia tri
        roomModel itemroom=room.get(position);
        img.setImageResource(itemroom.getImage());
        name.setText(itemroom.getName());
        price.setText(itemroom.getPrice());
        address.setText(itemroom.getAddress());
        quantityMember.setText(String.valueOf(itemroom.getQuantityMember()));
        quantityComment.setText(String.valueOf(itemroom.getQuantityComment()));
        type.setText(itemroom.getType());

        return convertView;
    }
}
