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

        ISearchRoomModel searchRoomModelInterface = new ISearchRoomModel() {
            @Override
            public void sendDataRoom(RoomModel roomModel) {

            }
        };

        searchRoomModel.searchRoom(searchRoomModelInterface);
    }
}
