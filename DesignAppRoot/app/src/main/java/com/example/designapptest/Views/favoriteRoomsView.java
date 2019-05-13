package com.example.designapptest.Views;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.designapptest.Controller.MainActivityController;
import com.example.designapptest.R;

public class favoriteRoomsView extends AppCompatActivity {
    RecyclerView recyclerMyFavoriteRoom;
    MainActivityController mainActivityController;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_rooms);

        sharedPreferences = getSharedPreferences("currentUserId", MODE_PRIVATE);

        recyclerMyFavoriteRoom = (RecyclerView) findViewById(R.id.recycler_favorite_rooms);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mainActivityController = new MainActivityController(this, sharedPreferences);
        mainActivityController.getListFavoriteRooms(recyclerMyFavoriteRoom);
    }
}
