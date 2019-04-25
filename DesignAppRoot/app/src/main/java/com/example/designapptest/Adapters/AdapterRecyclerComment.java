package com.example.designapptest.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AdapterRecyclerComment extends RecyclerView.Adapter<AdapterRecyclerComment.ViewHolder> {

    Context context;
    int layout;
    List<CommentModel> CommentModelList;
    SharedPreferences sharedPreferences;

    public AdapterRecyclerComment(Context context, int layout, List<CommentModel> CommentModelList, SharedPreferences sharedPreferences) {
        this.context = context;
        this.layout = layout;
        this.CommentModelList = CommentModelList;
        this.sharedPreferences = sharedPreferences;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_avt_comment_room_detail;
        TextView txt_name_comment_room_detail, txt_quantityLike_comment_room_detail,
                txt_rate_comment_room_detail, txt_title_comment_room_detail, txt_content_comment_room_detail,
                txt_time_comment_room_detail, txt_like_comment_room_detail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img_avt_comment_room_detail = (ImageView) itemView.findViewById(R.id.img_avt_comment_room_detail);
            txt_name_comment_room_detail = (TextView) itemView.findViewById(R.id.txt_name_comment_room_detail);
            txt_quantityLike_comment_room_detail = (TextView) itemView.findViewById(R.id.txt_quantityLike_comment_room_detail);
            txt_rate_comment_room_detail = (TextView) itemView.findViewById(R.id.txt_rate_comment_room_detail);
            txt_title_comment_room_detail = (TextView) itemView.findViewById(R.id.txt_title_comment_room_detail);
            txt_content_comment_room_detail = (TextView) itemView.findViewById(R.id.txt_content_comment_room_detail);
            txt_time_comment_room_detail = (TextView) itemView.findViewById(R.id.txt_time_comment_room_detail);
            txt_like_comment_room_detail = (TextView) itemView.findViewById(R.id.txt_like_comment_room_detail);
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

        //
        DatabaseReference nodeInteractiveComment = FirebaseDatabase.getInstance().getReference().child("InteractiveComment");
        final String userId = sharedPreferences.getString("currentUserId", "");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot dataSnapshotLikedComment = dataSnapshot.child(commentModel.getCommentID());
                viewHolder.txt_like_comment_room_detail.setText("Like this");

                for (DataSnapshot valueUserLikeComment : dataSnapshotLikedComment.getChildren()) {
                    String userLikeCommentId = valueUserLikeComment.getKey();
                    if (userLikeCommentId.equals(userId)) {
                        viewHolder.txt_like_comment_room_detail.setText("Liked");
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        nodeInteractiveComment.addValueEventListener(valueEventListener);

        // Bấm thích
        viewHolder.txt_like_comment_room_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeComment(commentModel, viewHolder.txt_like_comment_room_detail);
            }
        });
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

    private void likeComment(CommentModel commentModel, TextView txtLikeComment) {
        DatabaseReference nodeInteractiveComment = FirebaseDatabase.getInstance().getReference().child("InteractiveComment");
        String userId = sharedPreferences.getString("currentUserId", "");
        String commentId = commentModel.getCommentID();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());

        if(txtLikeComment.getText().toString().equals("Like this")) {
            nodeInteractiveComment.child(commentId).child(userId).child("time").setValue(date).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                    }
                }
            });
        } else {
            nodeInteractiveComment.child(commentId).child(userId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                    }
                }
            });
        }
    }
}
