package com.example.designapptest.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.designapptest.R;
import com.example.designapptest.Views.roomModel;

import java.util.List;

public class suggestAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<roomModel> room;

    public suggestAdapter(Context context, int layout, List<roomModel> room) {
        this.context = context;
        this.layout = layout;
        this.room = room;
    }
    @Override
    public int getCount() {
        return room.size();
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
        ImageView image=(ImageView) convertView.findViewById(R.id.img_room);
        TextView name=(TextView) convertView.findViewById(R.id.txtV_name);
        TextView address=(TextView) convertView.findViewById(R.id.txtV_address);
        //gan du lieu
        roomModel itemroom=room.get(position);
        image.setImageResource(itemroom.getImage());
        name.setText(itemroom.getName());
        address.setText(itemroom.getAddress());
        return convertView;
    }
}
