package com.example.designapptest.Controller;

import com.example.designapptest.Model.UserModel;

public class UserController {
    UserModel userModel;

    public UserController() {
        userModel = new UserModel();
    }

    public void addUser(UserModel newUserModel, String uid) {
        userModel.addUser(newUserModel, uid);
    }
}
