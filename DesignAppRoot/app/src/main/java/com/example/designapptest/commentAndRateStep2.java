package com.example.designapptest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.designapptest.Adapters.AdapterRecyclerComment;
import com.example.designapptest.Adapters.AdapterRecyclerRateStar;
import com.example.designapptest.Model.RoomModel;

public class commentAndRateStep2 extends Fragment {
    View viewCommentAndRateStep2;

    RecyclerView recycler_comment_room_detail_all;
    AdapterRecyclerComment adapterRecyclerComment;

    RoomModel roomModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        viewCommentAndRateStep2 = inflater.inflate(R.layout.comment_and_rate_step_2_room_detail_view, container, false);
        roomModel = ((commentAndRateMain)this.getActivity()).getRoomModelInfo();

        initControl();

        setAdapter();

        return viewCommentAndRateStep2;
    }

    public commentAndRateStep2() {

    }

    private void initControl() {
        recycler_comment_room_detail_all = (RecyclerView) viewCommentAndRateStep2.findViewById(R.id.recycler_comment_and_rate_all);
    }

    private void setAdapter() {
        RecyclerView.LayoutManager layoutManagerComment = new LinearLayoutManager((commentAndRateMain)getContext());
        recycler_comment_room_detail_all.setLayoutManager(layoutManagerComment);
        adapterRecyclerComment = new AdapterRecyclerComment((commentAndRateMain)getContext(), R.layout.comment_element_grid_room_detail_view, roomModel.getListCommentRoom());
        recycler_comment_room_detail_all.setAdapter(adapterRecyclerComment);
        adapterRecyclerComment.notifyDataSetChanged();
    }
}