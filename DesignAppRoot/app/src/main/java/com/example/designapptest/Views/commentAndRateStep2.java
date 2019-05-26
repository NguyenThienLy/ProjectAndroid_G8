package com.example.designapptest.Views;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.designapptest.Controller.CommentController;
import com.example.designapptest.Model.RoomModel;
import com.example.designapptest.R;
import com.example.designapptest.Views.commentAndRateMain;

public class commentAndRateStep2 extends Fragment {
    LinearLayout lnLtTopAllComments;
    TextView txtRoomGreatReview, txtRoomPrettyGoodReview, txtRoomMediumReview, txtRoomBadReview;

    TextView txtQuantityAllComments;
    ProgressBar progressBarAllComments;
    LinearLayout lnLyQuantityTopAllComments;

    NestedScrollView nestedScrollAllComments;
    ProgressBar progressBarLoadMoreAllComments;

    View viewCommentAndRateStep2;

    RecyclerView recyclerCommentRoomDetailAll;

    RoomModel roomModel;

    SharedPreferences sharedPreferences;
    String UID;

    CommentController commentController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        viewCommentAndRateStep2 = inflater.inflate(R.layout.comment_and_rate_step_2_room_detail_view, container, false);

        // Lấy đối tượng roomModel truyền từ room_detail
        roomModel = ((commentAndRateMain)this.getActivity()).getRoomModelInfo();
        sharedPreferences = ((commentAndRateMain)this.getActivity()).getSharedPreferences(LoginView.PREFS_DATA_NAME, Context.MODE_PRIVATE);
        UID = sharedPreferences.getString(LoginView.SHARE_UID,"n1oc76JrhkMB9bxKxwXrxJld3qH2");

        initControl();

        setAdapter();

        return viewCommentAndRateStep2;
    }

    @Override
    public void onStart() {
        super.onStart();

        setView();
    }

    public commentAndRateStep2() {

    }

    private void initControl() {
        recyclerCommentRoomDetailAll = (RecyclerView) viewCommentAndRateStep2.findViewById(R.id.recycler_comment_and_rate_all);

        txtRoomGreatReview = (TextView) viewCommentAndRateStep2.findViewById(R.id.txt_roomGreatReview_all);
        txtRoomPrettyGoodReview = (TextView) viewCommentAndRateStep2.findViewById(R.id.txt_roomPrettyGoodReview_all);
        txtRoomMediumReview = (TextView) viewCommentAndRateStep2.findViewById(R.id.txt_roomMediumReview_all);
        txtRoomBadReview = (TextView) viewCommentAndRateStep2.findViewById(R.id.txt_roomBadReview_all);
        lnLtTopAllComments = (LinearLayout) viewCommentAndRateStep2.findViewById(R.id.lnLt_top_all_comments_detail_room);

        txtQuantityAllComments = (TextView) viewCommentAndRateStep2.findViewById(R.id.txt_quantity_all_comments_detail_room);
        lnLyQuantityTopAllComments = (LinearLayout) viewCommentAndRateStep2.findViewById(R.id.lnLt_quantity_top_all_comments_detail_room);
        progressBarAllComments = (ProgressBar) viewCommentAndRateStep2.findViewById(R.id.progress_bar_all_comments_detail_room);
        progressBarAllComments.getIndeterminateDrawable().setColorFilter(Color.parseColor("#00DDFF"),
                android.graphics.PorterDuff.Mode.MULTIPLY);

        nestedScrollAllComments = (NestedScrollView) viewCommentAndRateStep2.findViewById(R.id.nested_scroll_all_comments_detail_room);
        progressBarLoadMoreAllComments = (ProgressBar) viewCommentAndRateStep2.findViewById(R.id.progress_bar_load_more_all_comments_detail_room);
        progressBarLoadMoreAllComments.getIndeterminateDrawable().setColorFilter(Color.parseColor("#00DDFF"),
                android.graphics.PorterDuff.Mode.MULTIPLY);
    }

    public void setView() {
        // Set view
        lnLtTopAllComments.setVisibility(View.GONE);
        progressBarAllComments.setVisibility(View.VISIBLE);
        lnLyQuantityTopAllComments.setVisibility(View.GONE);
        progressBarLoadMoreAllComments.setVisibility(View.GONE);
    }

    public void setAdapter() {
        commentController = new CommentController((commentAndRateMain)this.getActivity(), UID);
        commentController.ListRoomComments(recyclerCommentRoomDetailAll, roomModel, txtRoomGreatReview,
                txtRoomPrettyGoodReview, txtRoomMediumReview, txtRoomBadReview, lnLtTopAllComments,
                progressBarAllComments, lnLyQuantityTopAllComments, txtQuantityAllComments,
                nestedScrollAllComments, progressBarLoadMoreAllComments);
    }
}