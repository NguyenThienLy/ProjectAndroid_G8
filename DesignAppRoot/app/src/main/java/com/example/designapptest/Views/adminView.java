package com.example.designapptest.Views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.designapptest.ClassOther.myFilter;
import com.example.designapptest.Controller.DetailRoomController;
import com.example.designapptest.Controller.MainActivityController;
import com.example.designapptest.Controller.UserController;
import com.example.designapptest.R;

import java.util.ArrayList;
import java.util.List;

public class adminView extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences sharedPreferences;
    String UID;

    TextView txtSumViewsAdminView, txtSumHostsAdminView, txtSumRoomsAdminView;

    LinearLayout lnLtRoomsAdminView, lnLtHostsAdminView, lnLtReportsAdminView, lnLtRoomsWaitForApprovalAdminView;

    UserController userController;
    MainActivityController mainActivityController;
    DetailRoomController detailRoomController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_view);

        sharedPreferences = getSharedPreferences(LoginView.PREFS_DATA_NAME, MODE_PRIVATE);
        UID = sharedPreferences.getString(LoginView.SHARE_UID, "n1oc76JrhkMB9bxKxwXrxJld3qH2");

        initControl();

        setClick();
    }

    private void initControl() {
        txtSumViewsAdminView = (TextView) findViewById(R.id.txt_sum_views_admin_view);
        txtSumHostsAdminView = (TextView) findViewById(R.id.txt_sum_hosts_admin_view);
        txtSumRoomsAdminView = (TextView) findViewById(R.id.txt_sum_rooms_admin_view);

        lnLtRoomsAdminView = (LinearLayout) findViewById(R.id.lnLt_rooms_admin_view);
        lnLtHostsAdminView = (LinearLayout) findViewById(R.id.lnLt_hosts_admin_view);
        lnLtReportsAdminView = (LinearLayout) findViewById(R.id.lnLt_reports_admin_view);
        lnLtRoomsWaitForApprovalAdminView = (LinearLayout) findViewById(R.id.lnLt_rooms_wait_for_approval_admin_view);
    }

    private void setClick() {
        lnLtRoomsAdminView.setOnClickListener(this);
        lnLtHostsAdminView.setOnClickListener(this);
        lnLtReportsAdminView.setOnClickListener(this);
        lnLtRoomsWaitForApprovalAdminView.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        userController = new UserController(this);
        userController.getSumHosts(txtSumHostsAdminView);

        mainActivityController = new MainActivityController(this, UID);
        mainActivityController.getSumRooms(txtSumRoomsAdminView);

        List<myFilter> filterList = new ArrayList<>();
        detailRoomController = new DetailRoomController(this, "", filterList, UID);
        detailRoomController.getSumViews(txtSumViewsAdminView);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.lnLt_rooms_admin_view:
                Intent iRooms = new Intent(adminView.this, adminRoomsView.class);
                startActivity(iRooms);
                break;
            case R.id.lnLt_hosts_admin_view:
                Intent iHosts = new Intent(adminView.this, adminHostsView.class);
                startActivity(iHosts);
                break;
            case R.id.lnLt_reports_admin_view:
                Intent iReports = new Intent(adminView.this, adminReportRoomView.class);
                startActivity(iReports);
                break;
            case R.id.lnLt_rooms_wait_for_approval_admin_view:
                Intent iRoomsWaitForApproval = new Intent(adminView.this, adminRoomsWaitForApprovalView.class);
                startActivity(iRoomsWaitForApproval);
                break;
        }
    }
}