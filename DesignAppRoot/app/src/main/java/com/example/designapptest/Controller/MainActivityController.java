package com.example.designapptest.Controller;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.designapptest.Adapters.AdapterRecyclerMainRoom;
import com.example.designapptest.Controller.Interfaces.IMainRoomModel;
import com.example.designapptest.Model.RoomModel;
import com.example.designapptest.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivityController {
    Context context;
    RoomModel roomModel;
    public MainActivityController(Context context){
        this.context=context;
        roomModel = new RoomModel();
    }

    public void ListMainRoom(RecyclerView recyclerMainRoom){
        final List<RoomModel> roomModelList = new ArrayList<>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);

        recyclerMainRoom.setLayoutManager(layoutManager);

        //Tạo adapter cho recycle view
        final AdapterRecyclerMainRoom adapterRecyclerMainRoom = new AdapterRecyclerMainRoom(roomModelList, R.layout.room_element_list_view);
        //Cài adapter cho recycle
        recyclerMainRoom.setAdapter(adapterRecyclerMainRoom);

        //Tạo interface để truyền dữ liệu lên từ model
        IMainRoomModel iMainRoomModel = new IMainRoomModel() {
            @Override
            public void getListMainRoom(RoomModel valueRoom) {
                //Thêm vào trong danh sách trọ
                roomModelList.add(valueRoom);

                //Thông báo là đã có thêm dữ liệu
                adapterRecyclerMainRoom.notifyDataSetChanged();
            }
        };

        //Gọi hàm lấy dữ liệu trong model
        roomModel.ListRoom(iMainRoomModel);


    }
}
