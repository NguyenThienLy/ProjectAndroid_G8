package com.example.designapptest.Views;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.designapptest.Controller.MainActivityController;
import com.example.designapptest.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends Activity{

    //Qui thêm vào
    RecyclerView recyclerMainRoom;
    RecyclerView recyclerGridMainRoom;
    MainActivityController mainActivityController;
    ProgressBar progressBarMain;
    //End Qui thêm vào

   // GridView grVRoom;
    GridView grVLocation;

    //ListView lstVRoom;
    ListView lstVSuggest;
    ListView lstVSearch;

    ArrayList<roomModel> mydata;

    com.example.designapptest.Views.locationAdapter locationAdapter;
    com.example.designapptest.Views.searchAdapter searchAdapter;
    suggestAdapter suggestAdapterList;

    Button btnChooseSearch;
    Button btnPostRoom;
    //Qui them vao
    Button btnMapView;

    String[] dataSearch = {"Vị trí", "Giá cả", "Số người", "Tiện nghi", "Map"};
    EditText edTSearch;

    //Them vao de test
     FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initControl();

        elementRoom();

        postRoom();

        //Them vao de test
        requestPermission();
        client = LocationServices.getFusedLocationProviderClient(this);
    }

    private void initControl() {

        grVLocation = (GridView) findViewById(R.id.grV_location);

        btnPostRoom = (Button) findViewById(R.id.btn_postRoom_main_room);

        //qui them vao
        btnMapView =(Button)findViewById(R.id.btn_Map_View);


        edTSearch = (EditText) findViewById(R.id.edT_search);

        //Qui them vào
        recyclerMainRoom = (RecyclerView)findViewById(R.id.recycler_Main_Room);
        recyclerGridMainRoom = (RecyclerView)findViewById(R.id.recycler_Grid_Main_Room);
        progressBarMain = (ProgressBar)findViewById(R.id.Progress_Main);
    }


    private void elementRoom() {

    }

    private void postRoom() {
        btnPostRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), postRoomAdapter.class);
                startActivity(intent);
            }
        });

        //Qui them vao de test'ca
        btnMapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Kiểm tra quuyền
                if(ActivityCompat.checkSelfPermission(MainActivity.this, ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

                    return;
                }
                else{
                    //Neu du quyen thi lay toa do ma show map
                    client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if(location!=null){
                                double srLatitude = location.getLatitude();
                                double srLongtitude =location.getLongitude();
                                drawGoogleMap(srLatitude,srLongtitude,10.772413,106.673585);
                            }
                            else {
                            }
                        }
                    });
                }
            }
        });
    }

    private void drawGoogleMap(double srLatitude,double srLongtitude,double desLatitude,double desLongtitude){
        Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?saddr="+srLatitude+","+srLongtitude+"&daddr="+desLatitude+","+desLongtitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},1);
    }
    //Load dữ liệu vào List danh sách trong lần đầu chạy
    @Override
    protected void onStart() {
        super.onStart();

        mainActivityController = new MainActivityController(this);
        mainActivityController.ListMainRoom(recyclerMainRoom,recyclerGridMainRoom,progressBarMain);

        //Load top địa điểm nhiều phòng
        mainActivityController.loadTopLocation(grVLocation);
    }
    //End load dữ liệu vào danh sách trong lần đầu chạy
}
