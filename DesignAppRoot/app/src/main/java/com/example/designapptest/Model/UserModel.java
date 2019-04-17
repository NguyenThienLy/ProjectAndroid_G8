package com.example.designapptest.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserModel implements Parcelable { // Linh thêm
    //Các thuộc tính để render dữ liệu từ fire base

    String avatar,name,phoneNumber;
    boolean isOwner,gender;

    //End Các thuộc tính để render dữ liệu từ fire base

    //Id người dùng ở đây là uid trong firebaseauthen
    String userID;

    protected UserModel(Parcel in) {
        avatar = in.readString();
        name = in.readString();
        phoneNumber = in.readString();
        isOwner = in.readByte() != 0;
        gender = in.readByte() != 0;
        userID = in.readString();
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    //hàm khởi tạo rỗng
    public UserModel(){

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(avatar);
        dest.writeString(name);
        dest.writeString(phoneNumber);
        dest.writeByte((byte) (isOwner ? 1 : 0));
        dest.writeByte((byte) (gender ? 1 : 0));
        dest.writeString(userID);
    }
}
