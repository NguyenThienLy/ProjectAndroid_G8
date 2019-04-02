package com.example.designapptest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView= inflater.inflate(layout,null);
        //anh xa
        ImageView img = (ImageView) convertView.findViewById(R.id.img_avt_comment_room_detail);
        TextView name = (TextView) convertView.findViewById(R.id.txt_name_comment_room_detail);
        TextView quantityLike = (TextView) convertView.findViewById(R.id.txt_quantityLike_comment_room_detail);
        TextView rate = (TextView) convertView.findViewById(R.id.txt_rate_comment_room_detail);
        TextView headComment = (TextView) convertView.findViewById(R.id.txt_headComment_comment_room_detail);
        TextView mainComment = (TextView) convertView.findViewById(R.id.txt_mainComment_comment_room_detail);

        //gan gia tri
        commentRoomModel itemCommentRoom = lstCommentRoom.get(position);

        img.setImageResource(itemCommentRoom.getImage());
        name.setText(itemCommentRoom.getName());
        quantityLike.setText(String.valueOf(itemCommentRoom.getQuantityLike()));
        rate.setText(String.valueOf(itemCommentRoom.getRate()));
        headComment.setText(itemCommentRoom.getHeadComment());
        mainComment.setText(itemCommentRoom.getMainComment());

        return convertView;
    }
}
