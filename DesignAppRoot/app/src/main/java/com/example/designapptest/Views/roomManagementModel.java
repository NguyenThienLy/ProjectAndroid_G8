package com.example.designapptest.Views;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.designapptest.Controller.MainActivityController;
import com.example.designapptest.Model.RoomModel;
import com.example.designapptest.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class roomManagementModel extends AppCompatActivity {

    String UID;

    RecyclerView recyclerMainRoom;
    MainActivityController mainActivityController;
    List<RoomModel> roomModelList = new ArrayList<>();
    SharedPreferences sharedPreferences2;

    private Integer[] viewData = {356,75,684,464};
    private String[] nameData = {"Phòng trọ 1","Phòng trọ 2","Phòng trọ 3","Phòng trọ 4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_management_user_view);

        recyclerMainRoom = (RecyclerView)findViewById(R.id.recycler_Main_Room);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        sharedPreferences2 = this.getSharedPreferences(LoginView.PREFS_DATA_NAME, MODE_PRIVATE);
        UID = sharedPreferences2.getString(LoginView.SHARE_UID,"n1oc76JrhkMB9bxKxwXrxJld3qH2");

    }

    @Override
    protected void onStart() {
        super.onStart();

        mainActivityController = new MainActivityController(this,sharedPreferences2);
        mainActivityController.ListRoomUser(recyclerMainRoom,UID);
    }
}
