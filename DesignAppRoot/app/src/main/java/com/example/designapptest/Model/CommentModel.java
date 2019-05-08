package com.example.designapptest.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.designapptest.Controller.Interfaces.IRoomCommentModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class CommentModel implements Parcelable { // Linh thêm

    String title;
    String content;
    String time;
    String user;
    long likes, stars;
    String commentID;

    //Chủ bình luận
    UserModel userComment;

    protected CommentModel(Parcel in) {
        title = in.readString();
        content = in.readString();
        time = in.readString();
        user = in.readString();
        likes = in.readLong();
        stars = in.readLong();
        commentID = in.readString();
        userComment = in.readParcelable(UserModel.class.getClassLoader());
    }

    public static final Creator<CommentModel> CREATOR = new Creator<CommentModel>() {
        @Override
        public CommentModel createFromParcel(Parcel in) {
            return new CommentModel(in);
        }

        @Override
        public CommentModel[] newArray(int size) {
            return new CommentModel[size];
        }
    };

    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public UserModel getUserComment() {
        return userComment;
    }

    public void setUserComment(UserModel userComment) {
        this.userComment = userComment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public long getStars() {
        return stars;
    }

    public void setStars(long stars) {
        this.stars = stars;
    }

    //Biến lưu root của firebase, lưu ý để biến là private
    private DatabaseReference nodeRoot;

    public CommentModel() {
        //Trả về node gốc của database
        nodeRoot = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(time);
        dest.writeString(user);
        dest.writeLong(likes);
        dest.writeLong(stars);
        dest.writeString(commentID);
        dest.writeParcelable(userComment, flags);
    }

    public void ListRoomComments(final IRoomCommentModel roomCommentModelInterface, final RoomModel roomModel) {
        //Tạo listen cho firebase
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // refresh lại list comment thành rỗng.
                roomCommentModelInterface.refreshListRoomComments();

                //Thêm danh sách bình luận của phòng trọ
                DataSnapshot dataSnapshotRoomComments = dataSnapshot.child("RoomComments");

                List<CommentModel> tempCommentList = new ArrayList<CommentModel>();

                //Duyệt tất cả các giá trị trong node tương ứng
                for (DataSnapshot RoomCommentsValue : dataSnapshotRoomComments.getChildren()) {
                    String roomId = RoomCommentsValue.getKey();

                    if (roomModel.getRoomID().equals(roomId)) {
                        DataSnapshot dataSnapshotComment = dataSnapshotRoomComments.child(roomId);
                        for (DataSnapshot CommentValue : dataSnapshotComment.getChildren()) {
                            CommentModel commentModel = CommentValue.getValue(CommentModel.class);
                            commentModel.setCommentID(CommentValue.getKey());

                            //Duyệt user tương ứng để lấy ra thông tin user bình luận
                            UserModel tempUser = dataSnapshot.child("Users").child(commentModel.getUser()).getValue(UserModel.class);
                            commentModel.setUserComment(tempUser);
                            //End duyệt user tương ứng để lấy ra thông tin user bình luận

                            tempCommentList.add(commentModel);

                            //Kích hoạt interface
                            roomCommentModelInterface.getListRoomComments(commentModel);
                        }

                        roomModel.setListCommentRoom(tempCommentList);
                        break;
                    }
                }
                //End Thêm danh sách bình luận của phòng trọ
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        //Gán sự kiện listen cho nodeRoot
        nodeRoot.addValueEventListener(valueEventListener);
    }

    public void ListMyRoomComments(final IRoomCommentModel roomCommentModelInterface, final RoomModel roomModel, final SharedPreferences sharedPreferences) {
        //Tạo listen cho firebase
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Refresh lại list comment của tôi thành rỗng.
                roomCommentModelInterface.refreshListRoomComments();

                //Thêm danh sách bình luận của phòng trọ
                DataSnapshot dataSnapshotRoomComments = dataSnapshot.child("RoomComments");
                String currentUserId = sharedPreferences.getString("currentUserId", "");

                //Duyệt tất cả các giá trị trong node tương ứng
                for (DataSnapshot RoomCommentsValue : dataSnapshotRoomComments.getChildren()) {
                    String roomId = RoomCommentsValue.getKey();

                    if (roomModel.getRoomID().equals(roomId)) {
                        DataSnapshot dataSnapshotComment = dataSnapshotRoomComments.child(roomId);
                        for (DataSnapshot CommentValue : dataSnapshotComment.getChildren()) {
                            CommentModel commentModel = CommentValue.getValue(CommentModel.class);
                            commentModel.setCommentID(CommentValue.getKey());

                            if (currentUserId.equals(commentModel.getUser())) {
                                //Duyệt user tương ứng để lấy ra thông tin user bình luận
                                UserModel tempUser = dataSnapshot.child("Users").child(commentModel.getUser()).getValue(UserModel.class);
                                commentModel.setUserComment(tempUser);
                                //End duyệt user tương ứng để lấy ra thông tin user bình luận

                                //Kích hoạt interface
                                roomCommentModelInterface.getListRoomComments(commentModel);
                            }
                        }

                        break;
                    }
                }
                //End Thêm danh sách bình luận của phòng trọ
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        //Gán sự kiện listen cho nodeRoot
        nodeRoot.addValueEventListener(valueEventListener);
    }

    public void addComment(CommentModel newCommentModel, String roomId, final Context context,
                           TextView txtTitle, TextView txtContent) {
        DatabaseReference nodeComment = FirebaseDatabase.getInstance().getReference().child("RoomComments");
        String commentId = nodeComment.child(roomId).push().getKey();

        nodeComment.child(roomId).child(commentId).setValue(newCommentModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Post comment successfully", Toast.LENGTH_SHORT).show();

                    //
                    txtTitle.setText("");
                    txtContent.setText("");
                }
            }
        });
    }
}
