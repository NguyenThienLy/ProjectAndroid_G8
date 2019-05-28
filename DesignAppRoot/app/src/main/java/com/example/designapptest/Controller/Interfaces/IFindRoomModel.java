package com.example.designapptest.Controller.Interfaces;

import com.example.designapptest.Model.FindRoomModel;

public interface IFindRoomModel {
    public void getListFindRoom(FindRoomModel valueRoom);

    public void getSuccessNotify(int quantity);

    public void setProgressBarLoadMore();

    public void addSuccessNotify();
}
