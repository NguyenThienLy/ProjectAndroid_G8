package com.example.designapptest.Model;

import com.example.designapptest.ClassOther.myFilter;
import com.example.designapptest.Controller.Interfaces.ISearchRoomModel;

import java.util.List;

public class SearchRoomModel {

    List<myFilter> filterList;
    String district;

    public SearchRoomModel(String district,List<myFilter> filterList){
        this.filterList = filterList;
        this.district = district;
    }

    public void searchRoom(ISearchRoomModel searchRoomModelInterface){


    }
}
