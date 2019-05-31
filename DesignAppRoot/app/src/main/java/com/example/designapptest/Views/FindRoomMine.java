package com.example.designapptest.Views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.designapptest.Controller.FindRoomController;
import com.example.designapptest.Model.FindRoomFilterModel;
import com.example.designapptest.R;

public class FindRoomMine extends AppCompatActivity {
    // ID object truyen qua find room

    RecyclerView recyclerFindRoomMine;

    FindRoomController findRoomController;

    FindRoomFilterModel findRoomFilterModel;

    ProgressBar progressBarFindRoomMine;

    // Số lượng kết quả trả về.
    TextView txtResultReturnMine;

    //
    LinearLayout lLayHaveResultReturnMine;

    Toolbar toolbar;

    // Biến báo load lại find room.
    boolean flagFindRoom = true;

    SharedPreferences sharedPreferences;
    String UID;

    //Layout
    View layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_room_mine_view);

        // Lấy id của người dùng hiện tại
        sharedPreferences = getSharedPreferences(LoginView.PREFS_DATA_NAME, MODE_PRIVATE);
        UID = sharedPreferences.getString(LoginView.SHARE_UID, "n1oc76JrhkMB9bxKxwXrxJld3qH2");

        initControl();

        findRoomController = new FindRoomController(this);
    }

    //Load dữ liệu vào List danh sách trong lần đầu chạy
    @Override
    public void onStart() {
        super.onStart();

        initData();

        findRoomController.ListFindRoomMine(UID, recyclerFindRoomMine, progressBarFindRoomMine, txtResultReturnMine, lLayHaveResultReturnMine);
    }
    //End load dữ liệu vào danh sách trong lần đầu chạy

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initControl() {
       toolbar = (Toolbar) findViewById(R.id.toolbar);

        progressBarFindRoomMine = (ProgressBar) findViewById(R.id.progress_find_room_mine);
        progressBarFindRoomMine.getIndeterminateDrawable().setColorFilter(Color.parseColor("#00DDFF"),
                android.graphics.PorterDuff.Mode.MULTIPLY);

        recyclerFindRoomMine = (RecyclerView) findViewById(R.id.recycler_find_Room_mine);
        txtResultReturnMine = (TextView) findViewById(R.id.txt_resultReturn_find_room_mine);
        lLayHaveResultReturnMine = (LinearLayout) findViewById(R.id.lLay_haveResultReturn_find_room_mine);
    }

    private void initData() {
        // Gán các giá trị toolbar.
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Tìm phòng ở ghép của tôi");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
}
