package com.example.designapptest.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.designapptest.Adapters.AdapterRecyclerMainRoom;
import com.example.designapptest.ClassOther.myFilter;
import com.example.designapptest.Controller.Interfaces.ISearchRoomModel;
import com.example.designapptest.Model.RoomModel;
import com.example.designapptest.Model.SearchRoomModel;
import com.example.designapptest.R;

import java.util.ArrayList;
import java.util.List;

public class DetailRoomController {
    Context context;
    SearchRoomModel searchRoomModel;
    String UID;

    public DetailRoomController(Context context, String district, List<myFilter> filterList, String UID) {
        this.context = context;
        this.searchRoomModel = new SearchRoomModel(district,filterList);
        this.UID = UID;
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
                        roomModelList.add(roomModel);
                        //Thông báo thay đổi dữ liệu
                        adapterRecyclerMainRoom.notifyDataSetChanged();
                    }
                }
                else {

                }
            }
        };

        //Thêm sự kiện listenner cho noderoot
        searchRoomModel.searchRoom(searchRoomModelInterface);
    }
}
