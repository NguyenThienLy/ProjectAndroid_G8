package com.example.designapptest.Controller;

import android.content.Context;

import com.example.designapptest.ClassOther.myFilter;
import com.example.designapptest.Controller.Interfaces.ISearchRoomModel;
import com.example.designapptest.Model.RoomModel;
import com.example.designapptest.Model.SearchRoomModel;

import java.util.List;

public class searchViewController {

    Context context;
    SearchRoomModel searchRoomModel;

    public searchViewController(Context context,String district,List<myFilter> filterList) {
        this.context = context;
        this.searchRoomModel = new SearchRoomModel(district,filterList);
    }

    public void loadSearchRoom(){


        //Tạo listener cho firebase
        ISearchRoomModel searchRoomModelInterface = new ISearchRoomModel() {
            int i =0;
            @Override
            public void sendDataRoom(RoomModel roomModel) {
                //Tăng kết quả lên mỗi lần có room mới được tải về
                i++;
                //Set text hiển thị

                //Add thêm vào recycler view

            }
        };

        //Thêm sự kiện listenner cho noderoot
        searchRoomModel.searchRoom(searchRoomModelInterface);
    }
}
