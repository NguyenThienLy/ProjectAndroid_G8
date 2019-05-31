package com.example.designapptest.Controller.Interfaces;


import com.example.designapptest.Model.UserModel;

public interface IUserModel {
    public void getListUsers(UserModel valueUser);
    public void setProgressBarLoadMore();
    public void setQuantityTop(int quantity);
    public void setSumHostsAdminView(long quantity);
}
