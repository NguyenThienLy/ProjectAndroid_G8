package com.example.designapptest.Views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.example.designapptest.Controller.MainActivityController;
import com.example.designapptest.Model.RoomModel;
import com.example.designapptest.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.Context.MODE_PRIVATE;

public class MainActivity extends Fragment {

    //Qui thêm vào
    RecyclerView recyclerMainRoom;
    RecyclerView recyclerGridMainRoom;
    MainActivityController mainActivityController;
    ProgressBar progressBarMain;
    //End Qui thêm vào

   // GridView grVRoom;
    GridView grVLocation;

    EditText edTSearch;

    //Them vao de test
     FusedLocationProviderClient client;

    SharedPreferences sharedPreferences;
    String UID;

    Toolbar toolbar;

    CollapsingToolbarLayout collapsingToolbarLayout;

    //Layout
    View layout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getActivity().getSharedPreferences(LoginView.PREFS_DATA_NAME, MODE_PRIVATE);
        UID = sharedPreferences.getString(LoginView.SHARE_UID,"n1oc76JrhkMB9bxKxwXrxJld3qH2");

        requestPermission();
        client = LocationServices.getFusedLocationProviderClient(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.activity_main, container, false);

        initControl();

        clickSearchRoom();

        return layout;
    }

    private void initControl() {

        grVLocation = (GridView) layout.findViewById(R.id.grV_location);


        edTSearch = (EditText) layout.findViewById(R.id.edT_search);


        //Qui them vào
        recyclerMainRoom = (RecyclerView)layout.findViewById(R.id.recycler_Main_Room);
        recyclerGridMainRoom = (RecyclerView)layout.findViewById(R.id.recycler_Grid_Main_Room);
        progressBarMain = (ProgressBar)layout.findViewById(R.id.Progress_Main);
        progressBarMain.getIndeterminateDrawable().setColorFilter(Color.parseColor("#00DDFF"),
                android.graphics.PorterDuff.Mode.MULTIPLY);

    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(getActivity(),new String[]{ACCESS_FINE_LOCATION},1);
    }


    private void clickSearchRoom(){
        edTSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("checkclick", "onClick: ");
                Intent intentSearchLocation = new Intent(getContext(),location_search.class);
                startActivity(intentSearchLocation);
            }
        });
    }

    //Load dữ liệu vào List danh sách trong lần đầu chạy
    @Override
    public void onStart() {
        super.onStart();

        RoomModel.getListFavoriteRoomsId(UID);

        mainActivityController = new MainActivityController(getContext(), UID);
        mainActivityController.ListMainRoom(recyclerMainRoom,recyclerGridMainRoom,progressBarMain);

        //Load top địa điểm nhiều phòng
        mainActivityController.loadTopLocation(grVLocation);
    }
    //End load dữ liệu vào danh sách trong lần đầu chạy
}
