package com.example.designapptest.Views;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.designapptest.Controller.MainActivityController;
import com.example.designapptest.R;

public class adminRoomsWaitForApprovalView extends AppCompatActivity {
    RecyclerView recyclerAdminRoomsWaitForApprovalView;
    MainActivityController mainActivityController;

    SharedPreferences sharedPreferences;
    String UID;

    Toolbar toolbar;

    ProgressBar progressBarAdminRoomsWaitForApproval;
    LinearLayout lnLtQuantityTopAdminRoomsWaitForApproval;

    // Số lượng trả về.
    TextView txtQuantity;

    NestedScrollView nestedScrollAdminRoomsWaitForApprovalView;
    ProgressBar progressBarLoadMoreAdminRoomsWaitForApproval;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verified_rooms_view);

        sharedPreferences = getSharedPreferences(LoginView.PREFS_DATA_NAME, MODE_PRIVATE);
        UID = sharedPreferences.getString(LoginView.SHARE_UID, "n1oc76JrhkMB9bxKxwXrxJld3qH2");

        initControl();
    }

    private void initControl() {
        recyclerAdminRoomsWaitForApprovalView = (RecyclerView) findViewById(R.id.recycler_verified_rooms);

        toolbar = findViewById(R.id.toolbar);

        progressBarAdminRoomsWaitForApproval = (ProgressBar) findViewById(R.id.progress_bar_verified_rooms);
        progressBarAdminRoomsWaitForApproval.getIndeterminateDrawable().setColorFilter(Color.parseColor("#00DDFF"),
                android.graphics.PorterDuff.Mode.MULTIPLY);

        lnLtQuantityTopAdminRoomsWaitForApproval = (LinearLayout) findViewById(R.id.lnLt_quantity_top_verified_rooms);
        txtQuantity = (TextView) findViewById(R.id.txt_quantity_verified_rooms);

        nestedScrollAdminRoomsWaitForApprovalView = (NestedScrollView) findViewById(R.id.nested_scroll_verified_rooms_view);
        progressBarLoadMoreAdminRoomsWaitForApproval = (ProgressBar) findViewById(R.id.progress_bar_load_more_verified_rooms);
        progressBarLoadMoreAdminRoomsWaitForApproval.getIndeterminateDrawable().setColorFilter(Color.parseColor("#00DDFF"),
                android.graphics.PorterDuff.Mode.MULTIPLY);
    }

    private void setView() {
        // Hiện progress bar.
        progressBarAdminRoomsWaitForApproval.setVisibility(View.VISIBLE);
        // Ẩn progress bar load more.
        progressBarLoadMoreAdminRoomsWaitForApproval.setVisibility(View.GONE);
        // Ẩn layout kết quả trả vể.
        lnLtQuantityTopAdminRoomsWaitForApproval.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();

        initData();

        setView();

        mainActivityController = new MainActivityController(this, UID);
        mainActivityController.ListRoomsWaitForApprovalAdminView(recyclerAdminRoomsWaitForApprovalView, txtQuantity,
                progressBarAdminRoomsWaitForApproval, lnLtQuantityTopAdminRoomsWaitForApproval,
                nestedScrollAdminRoomsWaitForApprovalView,
                progressBarLoadMoreAdminRoomsWaitForApproval);
    }

    private void initData() {
        // Thiết lập toolbar
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Phòng trọ đang chờ duyệt");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
