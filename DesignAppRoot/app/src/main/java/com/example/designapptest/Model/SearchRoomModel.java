package com.example.designapptest.Model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.designapptest.ClassOther.ConvenientFilter;
import com.example.designapptest.ClassOther.GenderFilter;
import com.example.designapptest.ClassOther.PriceFilter;
import com.example.designapptest.ClassOther.TypeFilter;
import com.example.designapptest.ClassOther.myFilter;
import com.example.designapptest.Controller.Interfaces.ISearchRoomModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class SearchRoomModel {

    List<myFilter> filterList;
    String district;
    DatabaseReference nodeRoot;

    private GenderFilter genderFilter;
    private PriceFilter priceFilter;
    private TypeFilter typeFilter;
    private List<ConvenientFilter> convenientFilterList;

    public SearchRoomModel(String district,List<myFilter> filterList){

        //Lọc ra từng loại filter

        

        //End lọc ra từng loại filter

        this.filterList = filterList;
        this.district = district;
        nodeRoot = FirebaseDatabase.getInstance().getReference();
    }

    public void searchRoom(ISearchRoomModel searchRoomModelInterface){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot snapShotLocationRoom = dataSnapshot.child("LocationRoom").child("Quận Gò Vấp");

                for(DataSnapshot snapshotward:snapShotLocationRoom.getChildren()){
                    for (DataSnapshot snapShotStreet:snapshotward.getChildren()){
                        for(DataSnapshot snapShotRoom:snapShotStreet.getChildren()){
                            //Lấy ra thông tin Room theo ID truyền vào
                            filterData(dataSnapshot,snapShotRoom.getValue().toString(),searchRoomModelInterface);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        nodeRoot.addListenerForSingleValueEvent(valueEventListener);
    }

    private void filterData(DataSnapshot snapshotRoot,String RoomID,ISearchRoomModel iSearchRoomModel){

        //Lấy ra dữ liệu thô nhất của phòng
        RoomModel roomModel = snapshotRoot.child("Room").child(RoomID).getValue(RoomModel.class);

        Log.d("mycheck", roomModel.getName());
        //Lọc dữ liệu

        //Gửi giữ liệu qua main
        iSearchRoomModel.sendDataRoom(roomModel);
    }
}
