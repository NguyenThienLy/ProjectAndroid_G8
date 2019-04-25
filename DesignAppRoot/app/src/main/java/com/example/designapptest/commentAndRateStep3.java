package com.example.designapptest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.designapptest.Controller.CommentController;
import com.example.designapptest.Model.RoomModel;

public class commentAndRateStep3  extends Fragment {
    View viewCommentAndRateStep3;

    RecyclerView recycler_my_comment_room_detail;

    RoomModel roomModel;

    SharedPreferences sharedPreferences;
    CommentController commentController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        viewCommentAndRateStep3 = inflater.inflate(R.layout.comment_and_rate_step_3_room_detail_view, container, false);

        // Lấy đối tượng roomModel truyền từ room_detail
        roomModel = ((commentAndRateMain)this.getActivity()).getRoomModelInfo();
        sharedPreferences = ((commentAndRateMain) this.getActivity()).getSharedPreferences();

        initControl();

        setAdapter();

        return viewCommentAndRateStep3;
    }

    public commentAndRateStep3() {

    }

    private void initControl() {
        recycler_my_comment_room_detail = (RecyclerView) viewCommentAndRateStep3.findViewById(R.id.recycler_my_comment_and_rate);
    }

    public void setAdapter() {
        commentController = new CommentController((commentAndRateMain)this.getActivity(), sharedPreferences);
        commentController.ListMyRoomComments(recycler_my_comment_room_detail, roomModel);
    }
}
