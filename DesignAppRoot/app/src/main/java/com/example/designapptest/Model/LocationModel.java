package com.example.designapptest.Model;

import android.support.annotation.NonNull;

import com.example.designapptest.Controller.Interfaces.ILocationModel;
import com.example.designapptest.Controller.Interfaces.IStringCallBack;
import com.example.designapptest.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LocationModel implements Comparable<LocationModel>  {

    private int image;
    private String county;
    private int roomNumber;

    public LocationModel(){

    }

    public LocationModel(int image,String county,int roomNumber){
        this.image=image;
        this.county=county;
        this.roomNumber=roomNumber;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }



    //Hàm trả về top location
    public void topLocation(ILocationModel locationModelInterface){
        DatabaseReference nodeRoot = FirebaseDatabase.getInstance().getReference();

        DatabaseReference nodeLocationRoom=nodeRoot.child("LocationRoom");

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Tạo list mới
                List<LocationModel> listLocationModel = new ArrayList<LocationModel>();
                //Khởi tạo giá trị cho list
                listLocationModel.add(new LocationModel(R.drawable.avt_jpg_room,"Quận 1",0));
                listLocationModel.add(new LocationModel(R.drawable.avt_jpg_room,"Quận 2",0));
                listLocationModel.add(new LocationModel(R.drawable.avt_jpg_room,"Quận 3",0));
                listLocationModel.add(new LocationModel(R.drawable.avt_jpg_room,"Quận 4",0));
                listLocationModel.add(new LocationModel(R.drawable.avt_jpg_room,"Quận 5",0));
                listLocationModel.add(new LocationModel(R.drawable.avt_jpg_room,"Quận 6",0));
                listLocationModel.add(new LocationModel(R.drawable.avt_jpg_room,"Quận 7",0));
                listLocationModel.add(new LocationModel(R.drawable.avt_jpg_room,"Quận 8",0));
                listLocationModel.add(new LocationModel(R.drawable.avt_jpg_room,"Quận 9",0));
                listLocationModel.add(new LocationModel(R.drawable.avt_jpg_room,"Quận 10",0));
                listLocationModel.add(new LocationModel(R.drawable.avt_jpg_room,"Quận 11",0));
                listLocationModel.add(new LocationModel(R.drawable.avt_jpg_room,"Quận 12",0));
                listLocationModel.add(new LocationModel(R.drawable.avt_jpg_room,"Quận Thủ Đức",0));
                listLocationModel.add(new LocationModel(R.drawable.avt_jpg_room,"Quận Gò Vấp",0));
                listLocationModel.add(new LocationModel(R.drawable.avt_jpg_room,"Quận Bình Thạnh",0));
                listLocationModel.add(new LocationModel(R.drawable.avt_jpg_room,"Quận Tân Bình",0));
                listLocationModel.add(new LocationModel(R.drawable.avt_jpg_room,"Quận Tân Phú",0));
                listLocationModel.add(new LocationModel(R.drawable.avt_jpg_room,"Quận Phú Nhuận",0));
                listLocationModel.add(new LocationModel(R.drawable.avt_jpg_room,"Quận Bình Tân",0));
                //End khởi tạo giá trị cho list

                //Lặp và thêm số lượng phòng vào trong list
                for(int i=0;i<19;i++){
                    DataSnapshot DStemp = dataSnapshot.child(listLocationModel.get(i).getCounty());
                    for (DataSnapshot dataWard:DStemp.getChildren()){
                        for(DataSnapshot dataStreet:dataWard.getChildren()){
                            int currentNumber = listLocationModel.get(i).getRoomNumber();
                            listLocationModel.get(i).setRoomNumber((int) (currentNumber+dataStreet.getChildrenCount()));
                        }
                    }
                }

                //Sắp xếp lại list theo thứ tự tăng dần của số phòng trọ
                Collections.sort(listLocationModel);

                //Tạo mới data và gửi
                List<LocationModel> dataSend= new ArrayList<LocationModel>();
                dataSend.add(listLocationModel.get(18));
                dataSend.add(listLocationModel.get(17));
                dataSend.add(listLocationModel.get(16));

                //Kích hoạt interface
                locationModelInterface.getListTopRoom(dataSend);
                //End tạo mới data và gửi
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        nodeLocationRoom.addListenerForSingleValueEvent(valueEventListener);
    }

    public void Top_1_Location(IStringCallBack iStringCallBack){
        DatabaseReference nodeRoot = FirebaseDatabase.getInstance().getReference();

        DatabaseReference nodeLocationRoom=nodeRoot.child("LocationRoom");

        ValueEventListener valueEventListener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int count =0;
                String Top_1_location = "";
                int Top_Child=0;
                for(DataSnapshot SnapShotDicstrict:dataSnapshot.getChildren()){
                    count++;
                    int currentNumber = 0;
                    for(DataSnapshot SnapShotWarn:SnapShotDicstrict.getChildren()){
                       for(DataSnapshot SnapShotStreet:SnapShotWarn.getChildren()){
                           //Lấy ra số phòng trên đường đó
                           currentNumber+=SnapShotStreet.getChildrenCount();
                       }
                    }
                    if(currentNumber>Top_Child){
                        Top_Child=currentNumber;
                        Top_1_location = SnapShotDicstrict.getKey();
                    }
                    if(count == SnapShotDicstrict.getChildrenCount()){
                        iStringCallBack.sendString(Top_1_location);
                    }
                    currentNumber=0;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        nodeLocationRoom.addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    public int compareTo(LocationModel o) {

        return this.getRoomNumber() - o.getRoomNumber();
    }
}
