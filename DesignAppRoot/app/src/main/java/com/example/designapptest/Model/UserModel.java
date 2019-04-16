package com.example.designapptest.Model;

public class UserModel {
    //Các thuộc tính để render dữ liệu từ fire base

    String avatar,name,phoneNumber;
    boolean isOwner,gender;

    //End Các thuộc tính để render dữ liệu từ fire base

    //Id người dùng ở đây là uid trong firebaseauthen
    String userID;

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


}
