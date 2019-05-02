package com.example.designapptest.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.designapptest.Model.LocationModel;
import com.example.designapptest.R;

import java.util.List;

public class locationAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<LocationModel> lstLocation;

    public locationAdapter(Context context, int layout, List<LocationModel> lstLocation) {
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
        LocationModel itemLocation = lstLocation.get(position);

        ViewHolder holder = (ViewHolder) view.getTag();

        holder.image.setImageResource(itemLocation.getImage());
        holder.name.setText(itemLocation.getCounty());
        holder.room.setText(itemLocation.getRoomNumber()+" Phòng");

        return view;
    }

    class ViewHolder {
        ImageView image;
        TextView name;
        TextView room;
    }
}
