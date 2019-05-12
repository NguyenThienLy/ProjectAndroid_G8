package com.example.designapptest.Controller.Interfaces;

import android.graphics.drawable.Drawable;

import com.example.designapptest.Model.RoomModel;

import java.util.List;

public interface IMainRoomModel {
    public void getListMainRoom(RoomModel valueRoom);
    public void refreshListFavoriteRoom();
    public void makeToast(String message);
    public void setIconFavorite(int iconRes);
}
