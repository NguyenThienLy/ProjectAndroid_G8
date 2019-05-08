package com.example.designapptest.Controller;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.designapptest.Adapters.AdapterRecyclerFindRoom;
import com.example.designapptest.Controller.Interfaces.IFindRoomModel;
import com.example.designapptest.Model.FindRoomModel;
import com.example.designapptest.R;

import java.util.ArrayList;
import java.util.List;

public class FindRoomController {
    Context context;
    FindRoomModel findRoomModel;

    public FindRoomController(Context context){
        this.context=context;
        findRoomModel = new FindRoomModel();
    }

    public void ListFindRoom(RecyclerView recyclerFindRoom){
        final List<FindRoomModel> findRoomModelist = new ArrayList<>();

        //Tạo layout cho danh sách tìm phòng trọ;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerFindRoom.setLayoutManager(layoutManager);

        //Tạo adapter cho recycle view
        final AdapterRecyclerFindRoom adapterRecyclerFindRoom = new AdapterRecyclerFindRoom(context, findRoomModelist, R.layout.element_list_find_room_view);
        //Cài adapter cho recycle
        recyclerFindRoom.setAdapter(adapterRecyclerFindRoom);
        //End tạo layout cho danh sách tìm phòng trọ.

        //Tạo interface để truyền dữ liệu lên từ model
        IFindRoomModel iFindRoomModel = new IFindRoomModel() {
            @Override
            public void getListFindRoom(FindRoomModel valueRoom) {
                //Thêm vào trong danh sách trọ
                findRoomModelist.add(valueRoom);

                //Thông báo là đã có thêm dữ liệu
                adapterRecyclerFindRoom.notifyDataSetChanged();
            }
        };

        //Gọi hàm lấy dữ liệu trong model
        findRoomModel.ListFindRoom(iFindRoomModel);
    }
}
