package com.example.designapptest.Views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.designapptest.R;
import com.here.android.mpa.common.ApplicationContext;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.MapEngine;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapGesture;
import com.here.android.mpa.mapping.MapMarker;
import com.here.android.mpa.mapping.SupportMapFragment;
import com.here.android.mpa.search.ErrorCode;
import com.here.android.mpa.search.Location;
import com.here.android.mpa.search.ResultListener;
import com.here.android.mpa.search.ReverseGeocodeRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;

public class PopupChooseLocation extends AppCompatActivity {
    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;

    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[] {
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE};
    Map map = null;
    private SupportMapFragment mapFragment = null;
    MapMarker marker = null;

    Button btnExit;

    double latitude = 10.776927;
    double longtitude = 106.637588;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Thay đổi kích thước của màn hình

        //Lấy ra Intent
        Intent intent = getIntent();
        if(intent!=null){
            //Lấy ra tọa độ
            latitude = intent.getDoubleExtra(postRoomStep1.SHARE_LATITUDE,0.0);
            longtitude = intent.getDoubleExtra(postRoomStep1.SHARE_LONGTITUDE,0.0);
        }
        checkPermissions();

    }

    private void initControl(){
        btnExit = findViewById(R.id.btn_exit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent();
                latitude = marker.getCoordinate().getLatitude();
                longtitude = marker.getCoordinate().getLongitude();

                intent.putExtra(postRoomStep1.SHARE_LATITUDE,latitude);
                intent.putExtra(postRoomStep1.SHARE_LONGTITUDE,longtitude);

                setResult(AppCompatActivity.RESULT_OK,intent);
                finish();
            }
        });
    }

    private SupportMapFragment getMapFragment() {
        return (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapfragment);
    }

    //Hàm thay đổi kích thước của màn hình
    private void changeDisplay(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int)(width*.95),(int)(height*.9));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y=-20;

        getWindow().setAttributes(params);
    }

    protected void checkPermissions() {

        final List<String> missingPermissions = new ArrayList<String>();
        // check all required dynamic permissions
        for (final String permission : REQUIRED_SDK_PERMISSIONS) {
            final int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }
        if (!missingPermissions.isEmpty()) {
            // request all missing permissions
            final String[] permissions = missingPermissions
                    .toArray(new String[missingPermissions.size()]);
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            final int[] grantResults = new int[REQUIRED_SDK_PERMISSIONS.length];
            Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED);
            onRequestPermissionsResult(REQUEST_CODE_ASK_PERMISSIONS, REQUIRED_SDK_PERMISSIONS,
                    grantResults);
        }
    }

    private void initialize() {
        setContentView(R.layout.activity_popup_choose_location);
        initControl();
        // Search for the map fragment to finish setup by calling init().
        mapFragment = getMapFragment();
        // Set up disk cache path for the map service for this application
        boolean success = com.here.android.mpa.common.MapSettings.setIsolatedDiskCacheRootPath(
                getApplicationContext().getExternalFilesDir(null) + File.separator + ".here-maps",
                "com.here.android.tutorial.MapService");
        if (!success) {
            Toast.makeText(getApplicationContext(), "Unable to set isolated disk cache path.", Toast.LENGTH_LONG);
        } else {
            mapFragment.init(new OnEngineInitListener() {
                @Override
                public void onEngineInitializationCompleted(OnEngineInitListener.Error error) {
                    if (error == OnEngineInitListener.Error.NONE) {
                        map = mapFragment.getMap();
                        map.setCenter(new GeoCoordinate(latitude, longtitude, 0.0), Map.Animation.NONE);
                        map.setZoomLevel(15.0);

                        //Thêm vào marker hiện tại là tọa độ hiện tại
                        marker = new MapMarker();
                        marker.setCoordinate(new GeoCoordinate(latitude, longtitude, 0.0));

                        //Thêm vào mapMarker
                        map.addMapObject(marker);
                        //Thêm even cho map
                        mapFragment.getMapGesture().addOnGestureListener(new MapGesture.OnGestureListener.OnGestureListenerAdapter() {
                            @Override
                            public boolean onTapEvent(PointF p) {
                                //Even khi Tap vào màn hình
                                return false;
                            }
                            @Override
                            public boolean onLongPressEvent(PointF p) {
                                //Even khi chạm lâu vào màn hình
                                GeoCoordinate position = map.pixelToGeo(p);
                                dropMarker(position);
                                return false;
                            }
                        },0,false);
                    } else {
                        System.out.println("ERROR: Cannot initialize Map Fragment");
                    }
                }
            });
        }
    }

    //Hàm thay đổi vị trí của marker
    public void dropMarker(GeoCoordinate position) {
        if(marker != null) {
            map.removeMapObject(marker);
        }
        marker = new MapMarker();
        marker.setCoordinate(position);
        map.addMapObject(marker);
    }

    private void initMapEngine() {
        // Set path of isolated disk cache
        String diskCacheRoot = Environment.getExternalStorageDirectory().getPath()
                + File.separator + ".isolated-here-maps";
        String intentName = "";
        try {
            ApplicationInfo ai = this.getPackageManager().getApplicationInfo(this.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            intentName = bundle.getString("INTENT_NAME");

        } catch (PackageManager.NameNotFoundException e) {
            Log.e(this.getClass().toString(), "Failed to find intent name, NameNotFound: " + e.getMessage());
        }
        boolean success = com.here.android.mpa.common.MapSettings.setIsolatedDiskCacheRootPath(diskCacheRoot, intentName);

        if (!success) {
        } else {
            MapEngine.getInstance().init(new ApplicationContext(this), new OnEngineInitListener() {
                @Override
                public void onEngineInitializationCompleted(Error error) {
                    Log.d("mycheck", error+"");
                    //Toast.makeText(PopupChooseLocation.this, "Map Engine initialized with error code:" + error, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void triggerRevGeocodeRequest() {
        GeoCoordinate coordinate = new GeoCoordinate(49.25914, -123.00777);
        ReverseGeocodeRequest revGecodeRequest = new ReverseGeocodeRequest(coordinate);
        revGecodeRequest.execute(new ResultListener<Location>() {
            @Override
            public void onCompleted(Location location, ErrorCode errorCode) {
                if (errorCode == ErrorCode.NONE) {

                } else {

                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                for (int index = permissions.length - 1; index >= 0; --index) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {

                        Toast.makeText(this, "Required permission '" + permissions[index]
                                + "' not granted, exiting", Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }
                }
                //Khởi tại UI nếu thỏa hết permission
                initialize();
                //Khởi tạo map eng
                initMapEngine();
                //Gọi hàm thay đổi kích thước của màn hình
                changeDisplay();
                //triggerRevGeocodeRequest();
                break;
        }
    }
}
