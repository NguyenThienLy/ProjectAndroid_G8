package com.example.designapptest.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.designapptest.Adapters.AdapterRecyclerMainRoom;
import com.example.designapptest.ClassOther.myFilter;
import com.example.designapptest.Controller.Interfaces.ISearchRoomModel;
import com.example.designapptest.Model.RoomModel;
import com.example.designapptest.Model.SearchRoomModel;
import com.example.designapptest.R;

import java.util.ArrayList;
import java.util.List;

public class searchViewController {

    Context context;
    SearchRoomModel searchRoomModel;
    SharedPreferences sharedPreferences;

    public searchViewController(Context context, String district, List<myFilter> filterList, SharedPreferences sharedPreferences) {
        this.context = context;
        this.searchRoomModel = new SearchRoomModel(district,filterList);
        this.sharedPreferences =sharedPreferences;
    }

    public void loadSearchRoom(RecyclerView recyclerSearchRoom, TextView txtNumberRoom, ProgressBar progressBarLoad){

        final List<RoomModel> roomModelList = new ArrayList<>();

        //Tạo layout cho danh sách trọ
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerSearchRoom.setLayoutManager(layoutManager);

        //Tạo adapter cho recycle view
        final AdapterRecyclerMainRoom adapterRecyclerMainRoom = new AdapterRecyclerMainRoom(context, roomModelList, R.layout.room_element_list_view, sharedPreferences);
        //Cài adapter cho recycle
        recyclerSearchRoom.setAdapter(adapterRecyclerMainRoom);

        //End tạo layout cho danh sách trọ

        ISearchRoomModel searchRoomModelInterface = new ISearchRoomModel() {
            int i =0;
            @Override
            public void sendDataRoom(RoomModel roomModel,boolean ishadFound) {

                if(ishadFound){
                    //Add thêm vào recycler view
                    roomModelList.add(roomModel);
                    //Thông báo thay đổi dữ liệu
                    adapterRecyclerMainRoom.notifyDataSetChanged();

                    //Ẩn Progessbar và hiện text
                    progressBarLoad.setVisibility(View.GONE);
                    //Tăng kết quả lên mỗi lần có room mới được tải về
                    i++;
                    //Set text hiển thị
                    txtNumberRoom.setText(i+" Kết quả");
                    txtNumberRoom.setVisibility(View.VISIBLE);
                }
                else {
                    progressBarLoad.setVisibility(View.GONE);
                    //Set text hiển thị
                    txtNumberRoom.setText(i+" Kết quả");
                    txtNumberRoom.setVisibility(View.VISIBLE);
                }

            }
        };

        //Thêm sự kiện listenner cho noderoot
        searchRoomModel.searchRoom(searchRoomModelInterface);
    }
}
