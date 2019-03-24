package com.example.designapptest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class utilityRoomAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<utilityRoomModel> utilityRoom;

    public utilityRoomAdapter(Context context, int layout, List<utilityRoomModel> utilityRoom) {
        this.context = context;
        this.layout = layout;
        this.utilityRoom = utilityRoom;
    }

    @Override
    public int getCount() {
        return this.utilityRoom.size();
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
        ImageView img=(ImageView) convertView.findViewById(R.id.img_utility);
        TextView name=(TextView) convertView.findViewById(R.id.txt_nameUtility);
        //gan gia tri
        utilityRoomModel itemUtilityRoom = utilityRoom.get(position);
        img.setImageResource(itemUtilityRoom.getImage());
        name.setText(itemUtilityRoom.getName());

        return convertView;
    }
}
