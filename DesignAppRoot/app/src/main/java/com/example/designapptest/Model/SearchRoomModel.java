package com.example.designapptest.Model;

import android.support.annotation.NonNull;

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

import java.util.ArrayList;
import java.util.List;

public class SearchRoomModel {

    String district;
    DatabaseReference nodeRoot;

    private GenderFilter genderFilter;
    private PriceFilter priceFilter;
    private TypeFilter typeFilter;
    private List<ConvenientFilter> convenientFilterList;

    public SearchRoomModel(String district,List<myFilter> filterList){

        //Lọc ra từng loại filter
        genderFilter=null;
        priceFilter=null;
        typeFilter=null;
        convenientFilterList = new ArrayList<ConvenientFilter>();

        for(myFilter dataFilter:filterList){
            if(dataFilter instanceof GenderFilter){
                genderFilter =(GenderFilter) dataFilter;
            }
            if(dataFilter instanceof PriceFilter){
                priceFilter =(PriceFilter) dataFilter;
            }
            if(dataFilter instanceof TypeFilter){
                typeFilter =(TypeFilter) dataFilter;
            }
            if(dataFilter instanceof ConvenientFilter){
                convenientFilterList.add((ConvenientFilter) dataFilter);
            }

        }
        //End lọc ra từng loại filter

        this.district = district;
        nodeRoot = FirebaseDatabase.getInstance().getReference();
    }

    public void searchRoom(ISearchRoomModel searchRoomModelInterface){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot snapShotLocationRoom = dataSnapshot.child("LocationRoom").child(district);

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


    private void filterData(DataSnapshot snapShotRoot,String RoomID,ISearchRoomModel iSearchRoomModel){

        //Mảng lưu xem đã thỏa mãn hết các điều kiện chưa
        boolean[] arrCheck ={false,false,false};

        //Lấy ra dữ liệu thô nhất của phòng
        RoomModel roomModel = snapShotRoot.child("Room").child(RoomID).getValue(RoomModel.class);

        //Lọc dữ liệu
        //Lọc theo giới tính và số room
        if(genderFilter!=null){
            if(roomModel.isGender() == genderFilter.isGender() && roomModel.getMaxNumber() >= genderFilter.getMaxNumber()){
                arrCheck[0]=true;
            }
            else {
                arrCheck[0]=false;
            }
        }
        else {
            arrCheck[0]=true;
        }

        //Lọc theo giá
        if(priceFilter!=null){
            if(roomModel.getRentalCosts() >=priceFilter.getMinPrice() && roomModel.getRentalCosts() <=priceFilter.getMaxPrice()){
                arrCheck[1]=true;
            }
            else {
                arrCheck[1]=false;
            }
        }
        else {
            arrCheck[1]=true;
        }

        //Lọc theo loại phòng
        if (typeFilter != null) {
            if (roomModel.getTypeID().equals(typeFilter.getId())) {
                arrCheck[2]=true;
            } else {
                arrCheck[2]=false;
            }
        }
        else
            {
            arrCheck[2]=true;
        }


        if(arrCheck[0] ==true && arrCheck[1] ==true && arrCheck[2] ==true){
            //Lọc dữ liệu theo tiện ích

            //Lấy ra danh sách tiện ích của room
            List<String> lisConvenientID = new ArrayList<String>();
            DataSnapshot NodeRoomConvenient =  snapShotRoot.child("RoomConvenients").child(RoomID);
            for (DataSnapshot convenientID:NodeRoomConvenient.getChildren()){
                lisConvenientID.add(convenientID.getValue(String.class));
            }

            //Kiểm tra có chứa đầy đủ điều kiện không
            //Lấy ra danh sách ID tương ứng
            List<String> stringListID = new ArrayList<String>();
            for (ConvenientFilter convenientFilter:convenientFilterList){
                stringListID.add(convenientFilter.getId());
            }

            //Kiểm tra xem chứa đủ hết ID hay không
            if(lisConvenientID.containsAll(stringListID)){
                //Lấy ra toàn bộ dữ liệu tương ứng với Room

                //Set room ID
                roomModel.setRoomID(RoomID);

                //Set loại phòng trọ
                String tempType = snapShotRoot.child("RoomTypes")
                        .child(roomModel.getTypeID())
                        .getValue(String.class);
                roomModel.setRoomType(tempType);
                //End set loại phòng trọ

                //Lấy ra danh sách link hình của phòng trọ
                List<String> tempImageList = new ArrayList<String>();
                DataSnapshot nodeRoomImage =  snapShotRoot.child("RoomImages").child(RoomID);
                for (DataSnapshot ImageLink:nodeRoomImage.getChildren()){
                    tempImageList.add(ImageLink.getValue(String.class));
                }
                roomModel.setListImageRoom(tempImageList);
                //End lấy ra danh sách link hình phòng trọ

                //Thêm danh sách bình luận của phòng trọ
                DataSnapshot dataSnapshotCommentRoom = snapShotRoot.child("RoomComments").child(RoomID);
                List<CommentModel> tempCommentList = new ArrayList<CommentModel>();
                //Duyệt tất cả các giá trị trong node tương ứng
                for (DataSnapshot CommentValue : dataSnapshotCommentRoom.getChildren()) {
                    CommentModel commentModel = CommentValue.getValue(CommentModel.class);
                    commentModel.setCommentID(CommentValue.getKey());
                    //Duyệt user tương ứng để lấy ra thông tin user bình luận
                    UserModel tempUser = snapShotRoot.child("Users").child(commentModel.getUser()).getValue(UserModel.class);
                    commentModel.setUserComment(tempUser);
                    //End duyệt user tương ứng để lấy ra thông tin user bình luận

                    tempCommentList.add(commentModel);
                }

                roomModel.setListCommentRoom(tempCommentList);
                //End thêm danh sách bình luận của phòng trọ

                //Thêm danh sách tiện nghi của phòng trọ

                DataSnapshot dataSnapshotConvenientRoom = snapShotRoot.child("RoomConvenients").child(RoomID);
                List<ConvenientModel> tempConvenientList = new ArrayList<ConvenientModel>();
                //Duyệt tất cả các giá trị trong node tương ứng
                for (DataSnapshot valueConvenient : dataSnapshotConvenientRoom.getChildren()) {
                    String convenientId = valueConvenient.getValue(String.class);
                    ConvenientModel convenientModel = snapShotRoot.child("Convenients").child(convenientId).getValue(ConvenientModel.class);
                    convenientModel.setConvenientID(convenientId);

                    tempConvenientList.add(convenientModel);
                }

                roomModel.setListConvenientRoom(tempConvenientList);
                //End thêm danh sách tiện nghi của phòng trọ

                //Thêm thông tin chủ sở hữu cho phòng trọ
                UserModel tempUser = snapShotRoot.child("Users").child(roomModel.getOwner()).getValue(UserModel.class);
                roomModel.setRoomOwner(tempUser);
                //End thêm thông tin chủ sở hữu cho phòng trọ

                //Kích hoạt interface truyền dữ liệu qua

                iSearchRoomModel.sendDataRoom(roomModel,true);
            }
            else {
                //Gửi thông báo không tìm thấy phòng trọ nào
                iSearchRoomModel.sendDataRoom(roomModel,false);
            }
        }
        else {
            //Gửi thông báo không tìm thấy phòng trọ nào
            iSearchRoomModel.sendDataRoom(roomModel,false);
        }
        //End lọc dữ liệu
    }
}
