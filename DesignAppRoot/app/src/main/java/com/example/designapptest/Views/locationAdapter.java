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
    private List<locationModel> lstLocation;

    public locationAdapter(Context context, int layout, List<locationModel> lstLocation) {
        this.context = context;
        this.layout = layout;
        this.lstLocation = lstLocation;
    }

    @Override
    public int getCount() {

        return this.lstLocation.size();
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

            holder.image = (ImageView) view.findViewById(R.id.img_location);
            holder.name = (TextView) view.findViewById(R.id.txt_name_location);
            holder.room = (TextView) view.findViewById(R.id.txt_room_location);

            view.setTag(holder);
        }

        //gan gia tri
        locationModel itemLocation = lstLocation.get(position);

        ViewHolder holder = (ViewHolder) view.getTag();

        holder.image.setImageResource(itemLocation.getImage());
        holder.name.setText(itemLocation.getName());
        holder.room.setText(itemLocation.getRoom());

        return view;
    }

    class ViewHolder {
        ImageView image;
        TextView name;
        TextView room;
    }
}
