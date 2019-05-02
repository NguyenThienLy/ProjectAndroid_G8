package com.example.designapptest.Controller;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.example.designapptest.Adapters.AdapterRecyclerMainRoom;
import com.example.designapptest.Controller.Interfaces.ILocationModel;
import com.example.designapptest.Controller.Interfaces.IMainRoomModel;
import com.example.designapptest.Model.LocationModel;
import com.example.designapptest.Model.RoomModel;
import com.example.designapptest.R;
import com.example.designapptest.Views.locationAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivityController {
    Context context;
    RoomModel roomModel;
    LocationModel locationModel;
    public MainActivityController(Context context){
        this.context=context;
        roomModel = new RoomModel();
        locationModel = new LocationModel();
    }

    public void ListMainRoom(RecyclerView recyclerMainRoom,RecyclerView recyclerViewGridMainRoom ,final ProgressBar progressBarMain){
        final List<RoomModel> roomModelList = new ArrayList<>();

        //Tạo layout cho danh sách trọ tìm kiếm nhiều nhất
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerMainRoom.setLayoutManager(layoutManager);

        //Tạo adapter cho recycle view
        final AdapterRecyclerMainRoom adapterRecyclerMainRoom = new AdapterRecyclerMainRoom(context, roomModelList, R.layout.room_element_list_view);
        //Cài adapter cho recycle
        recyclerMainRoom.setAdapter(adapterRecyclerMainRoom);
        //End tạo layout cho danh sách trọ tìm kiếm nhiều nhất

        //Tạo layout cho danh sách trọ được hiển thị theo dạng grid phía dưới
        RecyclerView.LayoutManager layoutManagerGrid = new GridLayoutManager(context,2);
        recyclerViewGridMainRoom.setLayoutManager(layoutManagerGrid);

        //Tạo adapter cho recycle view
        final AdapterRecyclerMainRoom adapterRecyclerGridMainRoom = new AdapterRecyclerMainRoom(context, roomModelList, R.layout.room_element_grid_view);
        //Cài adapter cho recycle
        recyclerViewGridMainRoom.setAdapter(adapterRecyclerGridMainRoom);
        //End Tạo layout cho danh sách trọ được hiển thị theo dạng grid phía dưới

        //Tạo interface để truyền dữ liệu lên từ model
        IMainRoomModel iMainRoomModel = new IMainRoomModel() {
            @Override
            public void getListMainRoom(RoomModel valueRoom) {
                //Thêm vào trong danh sách trọ
                roomModelList.add(valueRoom);

                //Thông báo là đã có thêm dữ liệu
                adapterRecyclerMainRoom.notifyDataSetChanged();
                adapterRecyclerGridMainRoom.notifyDataSetChanged();
                progressBarMain.setVisibility(View.GONE);
            }
        };

        //Gọi hàm lấy dữ liệu trong model
        roomModel.ListRoom(iMainRoomModel);
    }

    public void loadTopLocation(GridView grVLocation){

        //Tạo mới mảng cho gridview
        List<LocationModel> datalocation = new ArrayList<LocationModel>();

        //Tạo adapter cho gridview
        locationAdapter adapter = new locationAdapter(context, R.layout.row_element_grid_view_location, datalocation);
        grVLocation.setAdapter(adapter);

        //Tạo mới interface đề truyền dữ liệu lên từ model
        ILocationModel iLocationModel = new ILocationModel() {
            @Override
            public void getListTopRoom(List<LocationModel> topLocation) {
                datalocation.addAll(topLocation);
                adapter.notifyDataSetChanged();
            }
        };

        //Gọi hàm lấy dữ liệu và truyền vào interface
        locationModel.topLocation(iLocationModel);
    }
}
