package com.example.designapptest.Views;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class roomManagementModel extends AppCompatActivity {
    RecyclerView recyclerMainRoom;
    MainActivityController mainActivityController;
    List<RoomModel> roomModelList = new ArrayList<>();

    PieChart pieChart;

    ProgressBar progressBarMyRooms;
    LinearLayout lnLtQuantityTopMyRooms;
    // Số lượng trả về.
    TextView txtQuantityMyRooms;

    NestedScrollView nestedScrollMyRoomsView;
    ProgressBar progressBarLoadMoreMyRooms;

    SharedPreferences sharedPreferences;
    String UID;

    private Integer[] viewData = {356,75,684,464};
    private String[] nameData = {"Phòng trọ 1","Phòng trọ 2","Phòng trọ 3","Phòng trọ 4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_management_user_view);

        sharedPreferences = this.getSharedPreferences(LoginView.PREFS_DATA_NAME, MODE_PRIVATE);
        UID = sharedPreferences.getString(LoginView.SHARE_UID,"n1oc76JrhkMB9bxKxwXrxJld3qH2");

        initControl();
    }

    private void initControl() {
        recyclerMainRoom = (RecyclerView)findViewById(R.id.recycler_Main_Room);
        pieChart=(PieChart) findViewById(R.id.chart_view);

        progressBarMyRooms = (ProgressBar) findViewById(R.id.progress_bar_my_rooms);
        progressBarMyRooms.getIndeterminateDrawable().setColorFilter(Color.parseColor("#00DDFF"),
                android.graphics.PorterDuff.Mode.MULTIPLY);

        lnLtQuantityTopMyRooms = (LinearLayout) findViewById(R.id.lnLt_quantity_top_my_rooms);
        txtQuantityMyRooms = (TextView) findViewById(R.id.txt_quantity_my_rooms);

        nestedScrollMyRoomsView = (NestedScrollView) findViewById(R.id.nested_scroll_my_rooms);
        progressBarLoadMoreMyRooms = (ProgressBar) findViewById(R.id.progress_bar_load_more_my_rooms);
        progressBarLoadMoreMyRooms.getIndeterminateDrawable().setColorFilter(Color.parseColor("#00DDFF"),
                android.graphics.PorterDuff.Mode.MULTIPLY);
    }

    private void initData() {
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
//                Toast.makeText(roomManagementModel.this,nameData[pos],Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
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

    private void setView() {
        // Hiện progress bar.
        progressBarMyRooms.setVisibility(View.VISIBLE);
        // Ẩn progress bar load more.
        progressBarLoadMoreMyRooms.setVisibility(View.GONE);
        // Ẩn layout kết quả trả vể.
        lnLtQuantityTopMyRooms.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();

        initData();

        setView();

        mainActivityController = new MainActivityController(this, UID);
        mainActivityController.ListRoomUser(recyclerMainRoom, txtQuantityMyRooms, progressBarMyRooms,
                lnLtQuantityTopMyRooms, nestedScrollMyRoomsView, progressBarLoadMoreMyRooms);
    }
}
