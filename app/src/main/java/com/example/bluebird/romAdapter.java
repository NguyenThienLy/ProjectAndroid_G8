package com.example.bluebird;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class romAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<roomModel> arrRoom;

    public romAdapter(Context context, int layout, ArrayList<roomModel> arrRoom) {
        this.context = context;
        this.layout = layout;
        this.arrRoom = arrRoom;
    }

    @Override
    public int getCount() {
        if (arrRoom != null)
            return arrRoom.size();

        return 0;
    }

    @Override
    public Object getItem(int position) {
        return arrRoom.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout,null);

        ImageView img = (ImageView)  convertView.findViewById(R.id.imgvAvatar);
        TextView type = (TextView)  convertView.findViewById(R.id.txtType);
        TextView quantityMember = (TextView)  convertView.findViewById(R.id.txtQuantityMember);
        TextView quantityComment = (TextView)  convertView.findViewById(R.id.txtQuantityComment);
        TextView name = (TextView)  convertView.findViewById(R.id.txtName);
        TextView price = (TextView)  convertView.findViewById(R.id.txtPrice);
        TextView area = (TextView)  convertView.findViewById(R.id.txtArea);
        TextView address = (TextView)  convertView.findViewById(R.id.txtAddress);

        roomModel model = arrRoom.get(position);

        img.setImageResource(model.getImage());
        type.setText(model.getType());
        quantityMember.setText(model.getQuantityMember());
        quantityComment.setText(model.getQuantityComment());
        name.setText(model.getName());
        price.setText(model.getPrice());
        area.setText(model.getArea());
        address.setText(model.getAddress());

        return convertView;
    }
}
