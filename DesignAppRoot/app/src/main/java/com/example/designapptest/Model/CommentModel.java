package com.example.designapptest.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class CommentModel implements Parcelable { // Linh thêm

    String title;
    String content;
    String time;
    String user;
    long likes,stars;
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

    public CommentModel() {

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
}
