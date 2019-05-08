package com.example.designapptest.Controller;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.designapptest.Model.ReportedRoomModel;

public class ReportedRoomController {
    ReportedRoomModel reportedRoomModel;
    Context context;
    SharedPreferences sharedPreferences;

    public ReportedRoomController(Context context, SharedPreferences sharedPreferences) {
        this.context = context;
        this.reportedRoomModel = new ReportedRoomModel();
        this.sharedPreferences = sharedPreferences;
    }

    public void addReport(ReportedRoomModel reportedRoomModel, String roomId) {
        reportedRoomModel.addReport(reportedRoomModel, roomId, context);
    }
}
