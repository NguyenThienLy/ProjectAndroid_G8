package com.example.designapptest.Views;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.designapptest.Model.RoomModel;
import com.example.designapptest.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class directMapRoomDetail extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap map;
    RoomModel roomModel;
    Button btnDirectMap;

    FusedLocationProviderClient client;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.direct_map_room_detail);

        roomModel = getIntent().getParcelableExtra("phongtro");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_map);
        mapFragment.getMapAsync(this);

        //Them vao de test
        requestPermission();
        client = LocationServices.getFusedLocationProviderClient(this);

        initControl();
        initData();
        showDiectMap();
    }

    private void initControl() {
        btnDirectMap = (Button) findViewById(R.id.btn_directMap);

        toolbar = findViewById(R.id.toolbar);
    }

    private void initData() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(roomModel.getName());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        float zoomLevel = (float) 18.0;
        String longAddress = roomModel.getApartmentNumber() + " " + roomModel.getStreet() + ", "
                + roomModel.getWard() + ", " + roomModel.getCounty() + ", " + roomModel.getCity();
        LatLng roomLocation = new LatLng(roomModel.getLatitude(), roomModel.getLongtitude());

        map.addMarker(new MarkerOptions()
                .position(roomLocation)
                .title(roomModel.getName())
                .snippet(longAddress)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_png_show_map_40)))
                .showInfoWindow();
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(roomLocation, zoomLevel));
    }

    private void showDiectMap() {
        //Qui them vao de test'ca
        btnDirectMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Kiểm tra quuyền
                if (ActivityCompat.checkSelfPermission(directMapRoomDetail.this, ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

                    return;
                } else {
                    //Neu du quyen thi lay toa do ma show map
                    client.getLastLocation().addOnSuccessListener(directMapRoomDetail.this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                double srLatitude = location.getLatitude();
                                double srLongtitude = location.getLongitude();

                                drawGoogleMap(srLatitude, srLongtitude, roomModel.getLatitude(), roomModel.getLongtitude());
                            } else {

                            }
                        }
                    });
                }
            }
        });
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }

    private void drawGoogleMap(double srLatitude, double srLongtitude, double desLatitude, double desLongtitude) {
        Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?saddr=" + srLatitude + "," + srLongtitude + "&daddr=" + desLatitude + "," + desLongtitude);

        boolean isAppInstalled = appInstalledOrNot("com.google.android.apps.maps");

        if (isAppInstalled) {
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);

            Toast.makeText(this, "Kết nối tới Google Map thành công !", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Kết nối tới Google Map thất bại !", Toast.LENGTH_LONG).show();
            return;
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }
}
