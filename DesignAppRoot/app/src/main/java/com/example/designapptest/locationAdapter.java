package com.example.designapptest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class locationAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<locationModel> location;

    public locationAdapter(Context context, int layout, List<locationModel> location) {
        this.context = context;
        this.layout = layout;
        this.location = location;
    }

    @Override
    public int getCount() {
        return location.size();
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
        ImageView image=(ImageView) convertView.findViewById(R.id.img_location);
        TextView name=(TextView) convertView.findViewById(R.id.txt_name_location);
        TextView room=(TextView) convertView.findViewById(R.id.txt_room_location);
        //gan gia tri
        locationModel itemlocation=location.get(position);
        image.setImageResource(itemlocation.getImage());
        name.setText(itemlocation.getName());
        room.setText(itemlocation.getRoom());
        return convertView;
    }
}
