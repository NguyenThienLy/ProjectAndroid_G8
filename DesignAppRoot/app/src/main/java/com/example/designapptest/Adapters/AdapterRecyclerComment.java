package com.example.designapptest.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.designapptest.Model.CommentModel;
import com.example.designapptest.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class AdapterRecyclerComment extends RecyclerView.Adapter<AdapterRecyclerComment.ViewHolder> {

    Context context;
    int layout;
    List<CommentModel> CommentModelList;

    public AdapterRecyclerComment(Context context, int layout, List<CommentModel> CommentModelList) {
        this.context = context;
        this.layout = layout;
        this.CommentModelList = CommentModelList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_avt_comment_room_detail;
        TextView txt_name_comment_room_detail, txt_quantityLike_comment_room_detail,
                txt_rate_comment_room_detail, txt_title_comment_room_detail, txt_content_comment_room_detail,
                txt_time_comment_room_detail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img_avt_comment_room_detail = (ImageView) itemView.findViewById(R.id.img_avt_comment_room_detail);
            txt_name_comment_room_detail = (TextView) itemView.findViewById(R.id.txt_name_comment_room_detail);
            txt_quantityLike_comment_room_detail = (TextView) itemView.findViewById(R.id.txt_quantityLike_comment_room_detail);
            txt_rate_comment_room_detail = (TextView) itemView.findViewById(R.id.txt_rate_comment_room_detail);
            txt_title_comment_room_detail = (TextView) itemView.findViewById(R.id.txt_title_comment_room_detail);
            txt_content_comment_room_detail = (TextView) itemView.findViewById(R.id.txt_content_comment_room_detail);
            txt_time_comment_room_detail = (TextView) itemView.findViewById(R.id.txt_time_comment_room_detail);
        }
    }

    @NonNull
    @Override
    public AdapterRecyclerComment.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterRecyclerComment.ViewHolder viewHolder, int i) {
        final CommentModel commentModel = CommentModelList.get(i);

        //Gán các giá trị vào giao diện
        viewHolder.txt_name_comment_room_detail.setText(commentModel.getUserComment().getName());
        viewHolder.txt_quantityLike_comment_room_detail.setText(commentModel.getLikes() + "");
        viewHolder.txt_rate_comment_room_detail.setText(commentModel.getStars() + "");
        viewHolder.txt_title_comment_room_detail.setText(commentModel.getTitle());
        viewHolder.txt_content_comment_room_detail.setText(commentModel.getContent());
        viewHolder.txt_time_comment_room_detail.setText(commentModel.getTime());

        //Dowload hình ảnh cho user
        StorageReference storageReference = FirebaseStorage
                .getInstance().getReference()
                .child("Users")
                .child(commentModel.getUserComment().getAvatar());

        final long ONE_MEGABYTE = 1024 * 1024;
        storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                //Tạo ảnh bitmap từ byte
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                viewHolder.img_avt_comment_room_detail.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        //End Dowload hình ảnh cho user
    }

    @Override
    public int getItemCount() {
        int comments = CommentModelList.size();
        if (comments > 5) {
            return 5;
        } else {
            return comments;
        }
    }


}
