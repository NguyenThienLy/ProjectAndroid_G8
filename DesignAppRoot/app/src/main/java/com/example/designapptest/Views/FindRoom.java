package com.example.designapptest.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.designapptest.Controller.FindRoomController;
import com.example.designapptest.R;

public class FindRoom extends AppCompatActivity {

    RecyclerView recyclerFindRoom;

    FindRoomController findRoomController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_room_view);

        initControl();
    }

    private void initControl() {
        recyclerFindRoom = (RecyclerView) findViewById(R.id.recycler_find_Room);
    }

    //Load dữ liệu vào List danh sách trong lần đầu chạy
    @Override
    protected void onStart() {
        super.onStart();

        findRoomController = new FindRoomController(this);
        findRoomController.ListFindRoom(recyclerFindRoom);
    }
    //End load dữ liệu vào danh sách trong lần đầu chạy
}
