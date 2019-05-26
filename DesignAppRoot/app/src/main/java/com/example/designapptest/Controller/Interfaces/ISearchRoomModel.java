package com.example.designapptest.Controller.Interfaces;

import com.example.designapptest.Model.RoomModel;

public interface ISearchRoomModel {
    public void sendDataRoom(RoomModel roomModel,boolean ishadFound);
    public void setProgressBarLoadMore();
    public void setQuantityTop(int quantity);
}
