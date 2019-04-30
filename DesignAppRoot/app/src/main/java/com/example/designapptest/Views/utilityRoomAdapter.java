package com.example.designapptest.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.designapptest.R;

import java.util.List;

public class utilityRoomAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<utilityRoomModel> lstUtilityRoom;

    public utilityRoomAdapter(Context context, int layout, List<utilityRoomModel> utilityRoom) {
        this.context = context;
        this.layout = layout;
        this.lstUtilityRoom = utilityRoom;
    }

    @Override
    public int getCount() {
        return this.lstUtilityRoom.size();
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

            holder.img = (ImageView) view.findViewById(R.id.img_utility_room_detail);
            holder.name = (TextView) view.findViewById(R.id.txt_nameUtility_utility_room_detail);

            view.setTag(holder);
        }

        utilityRoomModel itemUtilityRoom = lstUtilityRoom.get(position);

        ViewHolder holder = (ViewHolder) view.getTag();

        holder.img.setImageResource(itemUtilityRoom.getImage());
        holder.name.setText(itemUtilityRoom.getName());

        return view;
    }

    class ViewHolder {
        ImageView img;
        TextView name;
    }
}
