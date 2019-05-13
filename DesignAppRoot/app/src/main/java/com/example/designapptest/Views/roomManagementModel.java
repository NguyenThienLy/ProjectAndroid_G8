package com.example.designapptest.Views;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.designapptest.Controller.MainActivityController;
import com.example.designapptest.Model.RoomModel;
import com.example.designapptest.R;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class roomManagementModel extends AppCompatActivity {

    String UID;

    RecyclerView recyclerMainRoom;
    MainActivityController mainActivityController;
    List<RoomModel> roomModelList = new ArrayList<>();

    PieChart pieChart;

    SharedPreferences sharedPreferences2;


    private Integer[] viewData = {356,75,684,464};
    private String[] nameData = {"Phòng trọ 1","Phòng trọ 2","Phòng trọ 3","Phòng trọ 4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_management_user_view);

        recyclerMainRoom = (RecyclerView)findViewById(R.id.recycler_Main_Room);
        pieChart=(PieChart) findViewById(R.id.chart_view);

        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Biểu đồ View");
        pieChart.setCenterTextSize(10);

        addDataSet(pieChart);
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int pos = Math.round(h.getX());
                Toast.makeText(roomManagementModel.this,nameData[pos],Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        sharedPreferences2 = this.getSharedPreferences(LoginView.PREFS_DATA_NAME, MODE_PRIVATE);
        UID = sharedPreferences2.getString(LoginView.SHARE_UID,"n1oc76JrhkMB9bxKxwXrxJld3qH2");

    }
    private void addDataSet(PieChart pieChart) {
        ArrayList<PieEntry> yEntry=new ArrayList<>();
        ArrayList<String> xEntry=new ArrayList<>();

        for (int i=0;i<viewData.length;i++)
        {
            yEntry.add(new PieEntry(viewData[i],i));
        }
        for (int i=0;i<nameData.length;i++)
        {
            xEntry.add(nameData[i]);
        }

        PieDataSet pieDataSet=new PieDataSet(yEntry,"Số người xem");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        ArrayList<Integer> colors=new ArrayList<>();
        colors.add(Color.rgb(0,255,255));
        colors.add(Color.rgb(255,0,0));
        colors.add(Color.rgb(255,255,0));
        colors.add(Color.rgb(255,0,255));

        pieDataSet.setColors(colors);

        PieData pieData =new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    @Override
    protected void onStart() {
        super.onStart();

        mainActivityController = new MainActivityController(this,sharedPreferences2);
        mainActivityController.ListRoomUser(recyclerMainRoom,UID);
    }
}
