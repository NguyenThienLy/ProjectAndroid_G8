package com.example.designapptest.Model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RoomViewsModel {
    String time;
    String userID;
    String roomID;

    //Node root của firebase
    private DatabaseReference nodeRoot;

    public  RoomViewsModel(){
        nodeRoot = FirebaseDatabase.getInstance().getReference();
    }

    public RoomViewsModel(String time, String userID, String roomID) {
        this.time = time;
        this.userID = userID;
        this.roomID = roomID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    //Thêm vào 1 lượt view mới cho phòng
    public void addViews(RoomViewsModel roomViewsModel){
        //Duyệt vào node RoomViews
        DatabaseReference nodeRoomViews = nodeRoot.child("RoomViews");

        //Thêm vào một lượng view mới, một user khi vào xem một phòng thì chỉ tính là 1 lượt view duy nhất
        nodeRoomViews.child(roomViewsModel.getRoomID()).child(roomViewsModel.getUserID()).setValue(roomViewsModel.getTime());
    }
}
