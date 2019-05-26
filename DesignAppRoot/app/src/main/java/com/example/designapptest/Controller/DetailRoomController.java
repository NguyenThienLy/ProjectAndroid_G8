package com.example.designapptest.Controller;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.designapptest.Adapters.AdapterRecyclerMainRoom;
import com.example.designapptest.ClassOther.myFilter;
import com.example.designapptest.Controller.Interfaces.ISearchRoomModel;
import com.example.designapptest.Model.RoomModel;
import com.example.designapptest.Model.RoomViewsModel;
import com.example.designapptest.Model.SearchRoomModel;
import com.example.designapptest.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailRoomController {
    Context context;
    SearchRoomModel searchRoomModel;
    String UID;
    RoomViewsModel roomViewsModel;

    // khai báo các biến liên quan tới load more.
    int quantityRoomsLoaded = 0;
    int quantityRoomsEachTime = 4;

    public DetailRoomController(Context context, String district, List<myFilter> filterList, String UID) {
        this.context = context;
        this.searchRoomModel = new SearchRoomModel(district,filterList);
        this.UID = UID;
        //Khởi tạo
        roomViewsModel = new RoomViewsModel();
    }

    //Hàm thêm vào số lượng view cho phòng
    public void addViews(RoomViewsModel data){
        //Gọi hàm thêm lượt view từ model
        roomViewsModel.addViews(data);
    }

    public void loadSearchRoom(RecyclerView recyclerSearchRoom,String CurrentRoomID){

        final List<RoomModel> roomModelList = new ArrayList<>();

        //Tạo layout cho danh sách trọ
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerSearchRoom.setLayoutManager(layoutManager);

        //Tạo adapter cho recycle view
        final AdapterRecyclerMainRoom adapterRecyclerMainRoom = new AdapterRecyclerMainRoom(context, roomModelList, R.layout.room_element_list_view, UID);
        //Cài adapter cho recycle
        recyclerSearchRoom.setAdapter(adapterRecyclerMainRoom);

        //End tạo layout cho danh sách trọ

        ISearchRoomModel searchRoomModelInterface = new ISearchRoomModel() {
            int i =0;
            @Override
            public void sendDataRoom(RoomModel roomModel,boolean ishadFound) {
                if(ishadFound){
                    //Add thêm vào recycler view
                    //Kiểm tra nếu ID trùng với ID của room hiện tại thì bỏ qua không thêm
                    if(CurrentRoomID.equals(roomModel.getRoomID())){
                        //Do nothing
                    }else {
                        // Load ảnh nén
                        roomModel.setCompressionImageFit(Picasso.get().load(roomModel.getCompressionImage()).fit());

                        roomModelList.add(roomModel);
                        //Thông báo thay đổi dữ liệu
                        adapterRecyclerMainRoom.notifyDataSetChanged();
                    }
                }
                else {

                }
            }

            @Override
            public void setProgressBarLoadMore() {

            }

            @Override
            public void setQuantityTop(int quantity) {

            }
        };

        //Thêm sự kiện listenner cho noderoot
        searchRoomModel.searchRoom(searchRoomModelInterface,
                quantityRoomsLoaded + quantityRoomsEachTime, quantityRoomsLoaded);
    }
}
