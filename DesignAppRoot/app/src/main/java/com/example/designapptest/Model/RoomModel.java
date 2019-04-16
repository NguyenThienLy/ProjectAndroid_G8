package com.example.designapptest.Model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.designapptest.Controller.Interfaces.IMainRoomModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RoomModel {
    String address, describe, name, owner, timeCreated;
    long currentNumber, maxNumber;
    double latitude, longtitude, length, width, rentalCosts;
    boolean authentication;
    boolean gender;

    //Đánh giá của người xem trọ
    long medium;
    long great;
    long prettyGood;
    long bad;

    //Mã Phòng trọ
    String roomID;

    //Lưu mảng tên hình trên firebase
    private List<String> listImageRoom;

    //Lưu mảng comment của phòng trọ
    List<CommentModel> listCommentRoom;

    public List<CommentModel> getListCommentRoom() {
        return listCommentRoom;
    }

    public void setListCommentRoom(List<CommentModel> listCommentRoom) {
        this.listCommentRoom = listCommentRoom;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public long getMedium() {
        return medium;
    }

    public void setMedium(long medium) {
        this.medium = medium;
    }

    public long getGreat() {
        return great;
    }

    public void setGreat(long great) {
        this.great = great;
    }

    public long getPrettyGood() {
        return prettyGood;
    }

    public void setPrettyGood(long prettyGood) {
        this.prettyGood = prettyGood;
    }

    public long getBad() {
        return bad;
    }

    public void setBad(long bad) {
        this.bad = bad;
    }


    public List<String> getListImageRoom() {
        return listImageRoom;
    }

    public void setListImageRoom(List<String> listImageRoom) {
        this.listImageRoom = listImageRoom;
    }


    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    //Biến lưu root của firebase, lưu ý để biến là private
    private DatabaseReference nodeRoot;

    //Lưu ý phải có hàm khởi tạo rỗng
    public RoomModel() {
        //Trả về node root của database
        nodeRoot = FirebaseDatabase.getInstance().getReference();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    public long getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(long currentNumber) {
        this.currentNumber = currentNumber;
    }

    public long getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(long maxNumber) {
        this.maxNumber = maxNumber;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getRentalCosts() {
        return rentalCosts;
    }

    public void setRentalCosts(double rentalCosts) {
        this.rentalCosts = rentalCosts;
    }

    public boolean isAuthentication() {
        return authentication;
    }

    public void setAuthentication(boolean authentication) {
        this.authentication = authentication;
    }

    public void ListRoom(final IMainRoomModel mainRoomModelInterface) {

        //Tạo listen cho firebase
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Duyệt vào node Room trên firebase
                DataSnapshot dataSnapshotRoom = dataSnapshot.child("Room");

                //Duyệt hết trong danh sách phòng trọ
                for (DataSnapshot valueRoom : dataSnapshotRoom.getChildren()) {
                    //Lấy ra giá trị ép kiểu qua kiểu RoomModel
                    RoomModel roomModel = valueRoom.getValue(RoomModel.class);
                    //Set mã phòng trọ
                    roomModel.setRoomID(valueRoom.getKey());

                    //Thêm tên danh sách tên hình vào phòng trọ

                    //Duyệt vào node RoomImages trên firebase và duyệt vào node có mã room tương ứng
                    DataSnapshot dataSnapshotImageRoom = dataSnapshot.child("RoomImages").child(valueRoom.getKey());
                    List<String> tempImageList = new ArrayList<String>();
                    //Duyêt tất cả các giá trị của node tương ứng
                    for (DataSnapshot valueImage : dataSnapshotImageRoom.getChildren()) {

                        tempImageList.add(valueImage.getValue(String.class));
                    }

                    //set mảng hình vào list
                    roomModel.setListImageRoom(tempImageList);

                    //End Thêm tên danh sách tên hình vào phòng trọ

                    //Thêm danh sách bình luận của phòng trọ

                    DataSnapshot dataSnapshotCommentRoom = dataSnapshot.child("RoomComments").child(valueRoom.getKey());
                    List<CommentModel> tempCommentList = new ArrayList<CommentModel>();
                    //Duyệt tất cả các giá trị trong node tương ứng
                    for (DataSnapshot CommentValue : dataSnapshotCommentRoom.getChildren()) {
                        CommentModel commentModel = CommentValue.getValue(CommentModel.class);

                        //Duyệt user tương ứng để lấy ra thông tin user bình luận

                        UserModel tempUser = dataSnapshot.child("Users").child(commentModel.getUser()).getValue(UserModel.class);
                        commentModel.setUserComment(tempUser);

                        //End duyệt user tương ứng để lấy ra thông tin user bình luận

                        tempCommentList.add(commentModel);
                    }

                    roomModel.setListCommentRoom(tempCommentList);

                    //End Thêm danh sách bình luận của phòng trọ

                    //Kích hoạt interface
                    mainRoomModelInterface.getListMainRoom(roomModel);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        //Gán sự kiện listen cho nodeRoot
        nodeRoot.addListenerForSingleValueEvent(valueEventListener);
    }

}
