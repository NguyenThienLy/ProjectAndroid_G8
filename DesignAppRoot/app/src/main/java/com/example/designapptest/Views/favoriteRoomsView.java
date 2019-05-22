package com.example.designapptest.Views;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.designapptest.Controller.MainActivityController;
import com.example.designapptest.R;

public class favoriteRoomsView extends AppCompatActivity {
    RecyclerView recyclerMyFavoriteRoom;
    MainActivityController mainActivityController;
    SharedPreferences sharedPreferences;
    String UID;

    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_rooms);

        sharedPreferences = getSharedPreferences(LoginView.PREFS_DATA_NAME, MODE_PRIVATE);
        UID = sharedPreferences.getString(LoginView.SHARE_UID,"n1oc76JrhkMB9bxKxwXrxJld3qH2");

        recyclerMyFavoriteRoom = (RecyclerView) findViewById(R.id.recycler_favorite_rooms);
        toolbar = findViewById(R.id.toolbar);

        // Thiết lập toolbar
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Phòng trọ yêu thích");
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

    @Override
    protected void onStart() {
        super.onStart();

        mainActivityController = new MainActivityController(this, UID);
        mainActivityController.getListFavoriteRooms(recyclerMyFavoriteRoom);
    }
}
