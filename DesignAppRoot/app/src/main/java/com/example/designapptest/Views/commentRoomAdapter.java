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

public class commentRoomAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<commentRoomModel> lstCommentRoom;

    public commentRoomAdapter(Context context, int layout, List<commentRoomModel>commentRoom) {
        this.context = context;
        this.layout = layout;
        this.lstCommentRoom = commentRoom;
    }

    @Override
    public int getCount() {
        return this.lstCommentRoom.size();
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

            holder.img = (ImageView) view.findViewById(R.id.img_avt_comment_room_detail);
            holder.name = (TextView) view.findViewById(R.id.txt_name_comment_room_detail);
            holder.quantityLike = (TextView) view.findViewById(R.id.txt_quantityLike_comment_room_detail);
            holder.rate = (TextView) view.findViewById(R.id.txt_rate_comment_room_detail);
            holder.headComment = (TextView) view.findViewById(R.id.txt_title_comment_room_detail);
            holder.mainComment = (TextView) view.findViewById(R.id.txt_content_comment_room_detail);

            view.setTag(holder);
        }

        //gan gia tri
        commentRoomModel itemCommentRoom = lstCommentRoom.get(position);

        ViewHolder holder = (ViewHolder) view.getTag();

        holder.img.setImageResource(itemCommentRoom.getImage());
        holder.name.setText(itemCommentRoom.getName());
        holder.quantityLike.setText(String.valueOf(itemCommentRoom.getQuantityLike()));
        holder.rate.setText(String.valueOf(itemCommentRoom.getRate()));
        holder.headComment.setText(itemCommentRoom.getHeadComment());
        holder.mainComment.setText(itemCommentRoom.getMainComment());

        return view;
    }

    class ViewHolder {
        ImageView img;
        TextView name;
        TextView quantityLike;
        TextView rate;
        TextView headComment;
        TextView mainComment;
    }
}
