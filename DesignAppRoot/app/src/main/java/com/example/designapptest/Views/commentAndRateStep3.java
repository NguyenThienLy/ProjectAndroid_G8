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

public class commentAndRateStep3  extends Fragment {
    TextView txtQuantityMyComments;
    ProgressBar progressBarMyComments;
    LinearLayout lnLyQuantityTopMyComments;

    NestedScrollView nestedScrollMyComments;
    ProgressBar progressBarLoadMoreMyComments;

    View viewCommentAndRateStep3;

    RecyclerView recyclerMyComments;

    RoomModel roomModel;

    SharedPreferences sharedPreferences;
    String UID;

    CommentController commentController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        viewCommentAndRateStep3 = inflater.inflate(R.layout.comment_and_rate_step_3_room_detail_view, container, false);

        // Lấy đối tượng roomModel truyền từ room_detail
        roomModel = ((commentAndRateMain)this.getActivity()).getRoomModelInfo();
        sharedPreferences = ((commentAndRateMain)this.getActivity()).getSharedPreferences(LoginView.PREFS_DATA_NAME, Context.MODE_PRIVATE);
        UID = sharedPreferences.getString(LoginView.SHARE_UID,"n1oc76JrhkMB9bxKxwXrxJld3qH2");

        initControl();

        setAdapter();

        return viewCommentAndRateStep3;
    }

    @Override
    public void onStart() {
        super.onStart();

        setView();
    }

    public commentAndRateStep3() {

    }

    private void initControl() {
        recyclerMyComments = (RecyclerView) viewCommentAndRateStep3.findViewById(R.id.recycler_my_comment_and_rate);

        txtQuantityMyComments = (TextView) viewCommentAndRateStep3.findViewById(R.id.txt_quantity_my_comments_detail_room);
        lnLyQuantityTopMyComments = (LinearLayout) viewCommentAndRateStep3.findViewById(R.id.lnLt_quantity_top_my_comments_detail_room);
        progressBarMyComments = (ProgressBar) viewCommentAndRateStep3.findViewById(R.id.progress_bar_my_comments_detail_room);
        progressBarMyComments.getIndeterminateDrawable().setColorFilter(Color.parseColor("#00DDFF"),
                android.graphics.PorterDuff.Mode.MULTIPLY);

        nestedScrollMyComments = (NestedScrollView) viewCommentAndRateStep3.findViewById(R.id.nested_scroll_my_comments_detail_room);
        progressBarLoadMoreMyComments = (ProgressBar) viewCommentAndRateStep3.findViewById(R.id.progress_bar_load_more_my_comments_detail_room);
        progressBarLoadMoreMyComments.getIndeterminateDrawable().setColorFilter(Color.parseColor("#00DDFF"),
                android.graphics.PorterDuff.Mode.MULTIPLY);
    }

    public void setView() {
        // set view
        progressBarMyComments.setVisibility(View.VISIBLE);
        lnLyQuantityTopMyComments.setVisibility(View.GONE);
        progressBarLoadMoreMyComments.setVisibility(View.GONE);
    }

    public void setAdapter() {
        commentController = new CommentController((commentAndRateMain)this.getActivity(), UID);
        commentController.ListMyRoomComments(recyclerMyComments, roomModel, progressBarMyComments,
                lnLyQuantityTopMyComments, txtQuantityMyComments,
                nestedScrollMyComments, progressBarLoadMoreMyComments);
    }
}
