package com.example.designapptest.Controller.Interfaces;

import com.example.designapptest.Model.CommentModel;

import java.security.PublicKey;
import java.util.List;

public interface IRoomCommentModel {
    public void getListRoomComments(CommentModel valueComment);
    public void makeToast(String message);
    public void setView();
    public void setLinearLayoutTopAllComments(List<CommentModel> listCommentsModel);
    public void setProgressBarLoadMore();
    public void setQuantityComments(int quantity);
}
