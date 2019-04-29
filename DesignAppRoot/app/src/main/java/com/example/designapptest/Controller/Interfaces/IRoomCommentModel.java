package com.example.designapptest.Controller.Interfaces;

import com.example.designapptest.Model.CommentModel;

import java.util.List;

public interface IRoomCommentModel {
    public void getListRoomComments(CommentModel valueComment);
    public void refreshListRoomComments();
}
