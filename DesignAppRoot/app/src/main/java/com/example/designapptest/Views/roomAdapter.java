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

public class roomAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<roomModel> lstRoom;

    public roomAdapter(Context context, int layout, List<roomModel> lstRoom) {
        this.context = context;
        this.layout = layout;
        this.lstRoom = lstRoom;
    }

    @Override
    public int getCount() {
        return this.lstRoom.size();
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

            holder.img = (ImageView) view.findViewById(R.id.img_room);
            holder.name = (TextView) view.findViewById(R.id.txt_name);
            holder.price = (TextView) view.findViewById(R.id.txt_price);
            holder.address = (TextView) view.findViewById(R.id.txt_address);
            holder.quantityMember = (TextView) view.findViewById(R.id.txt_quantityMember);
            holder.quantityComment = (TextView) view.findViewById(R.id.txt_quantityComment);
            holder.type = (TextView) view.findViewById(R.id.txt_type);

            view.setTag(holder);
        }

        //gan gia tri
        roomModel itemRoom = lstRoom.get(position);

        ViewHolder holder = (ViewHolder) view.getTag();

        holder.img.setImageResource(itemRoom.getImage());
        holder.name.setText(itemRoom.getName());
        holder.price.setText(itemRoom.getPrice());
        holder.address.setText(itemRoom.getAddress());
        holder.quantityMember.setText(String.valueOf(itemRoom.getQuantityMember()));
        holder.quantityComment.setText(String.valueOf(itemRoom.getQuantityComment()));
        holder.type.setText(itemRoom.getType());

        return view;
    }

    class ViewHolder {
        ImageView img;
        TextView name;
        TextView price;
        TextView address;
        TextView quantityMember;
        TextView quantityComment;
        TextView type;
    }
}
