package com.example.designapptest.Views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.example.designapptest.R;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapGesture;
import com.here.android.mpa.mapping.MapMarker;
import com.here.android.mpa.mapping.SupportMapFragment;
import com.here.android.mpa.search.ErrorCode;
import com.here.android.mpa.search.GeocodeRequest;
import com.here.android.mpa.search.GeocodeResult;
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
    ProgressBar progessBarLoadMap;

    FloatingSearchView searchView;

    //Biến lưu tọa độ để truyền về lại màn hình đăng phòng
    double latitude = 10.776927;
    double longtitude = 106.637588;

    //Biến lưu địa chỉ để truyền về lại màn hình đăng phòng
    //Quận
    String District="";

    //Đường
    String Street ="";

    //Thành phố
    String City="";

    //Số nhà
    String No="";

    TextView txtStreet,txtWard,txtDistrict,txtNo;


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

    private void Search(String Query) {
        //Đảm bảo người dùng chỉ search trong thành phố HCM
        Query = Query + ", Thành phố Hồ Chí Minh";


        //Tìm kiếm xung quanh điểm này
        GeoCoordinate area = new GeoCoordinate(latitude, longtitude);

        GeocodeRequest request = new GeocodeRequest(Query).setSearchArea(area,5000);
        request.execute(new ResultListener<List<GeocodeResult>>() {
            @Override
            public void onCompleted(List<GeocodeResult> geocodeResults, ErrorCode errorCode) {
                if(errorCode!=ErrorCode.NONE){
                    Log.e("HERE", errorCode.toString());
                }else {
                    for(GeocodeResult result:geocodeResults){
                        //Lấy ra tọa độ trả về
                        GeoCoordinate location = result.getLocation().getCoordinate();

                        //Thay đổi địa chỉ của marker trên map
                        dropMarker(location,true);
                    }
                }
            }
        });
    }

    private void updateView(){
        txtDistrict.setText(District.equals("")?"Không rõ":District);
        txtWard.setText("Không rõ");
        txtNo.setText(No.equals("")?"Không rõ":No);
        txtStreet.setText(Street.equals("")?"Không rõ":Street);
    }

    private void initControl(){

        progessBarLoadMap= findViewById(R.id.progess_bar_load_map);
        //Đổi màu progessBar
        progessBarLoadMap.getIndeterminateDrawable().setColorFilter(Color.parseColor("#00DDFF"),
                android.graphics.PorterDuff.Mode.MULTIPLY);
        progessBarLoadMap.setVisibility(View.VISIBLE);

        txtDistrict = findViewById(R.id.txt_district);
        txtNo =findViewById(R.id.txt_no);
        txtStreet = findViewById(R.id.txt_street);
        txtWard = findViewById(R.id.txt_ward);

        btnExit = findViewById(R.id.btn_exit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Kiểm tra nếu người dùng không chọn vào Thành phố Hồ chí minh
                if(City.contains("Hồ Chí Minh")){
                    Intent intent=new Intent();
                    latitude = marker.getCoordinate().getLatitude();
                    longtitude = marker.getCoordinate().getLongitude();

                    //Truyền về thông tin hiện tại trên map
                    intent.putExtra(postRoomStep1.SHARE_LATITUDE,latitude);
                    intent.putExtra(postRoomStep1.SHARE_LONGTITUDE,longtitude);

                    //Thông tin về vị trí vật lý
                    intent.putExtra(postRoomStep1.SHARE_DISTRICT,District);
                    intent.putExtra(postRoomStep1.SHARE_STREET,Street);
                    intent.putExtra(postRoomStep1.SHARE_NO,No);

                    //End truyền về thông tin hiện tại trên map

                    setResult(AppCompatActivity.RESULT_OK,intent);
                    finish();
                }
                else {
                    Toast.makeText(PopupChooseLocation.this,"Vui lòng chọn các địa chỉ ở HCM",Toast.LENGTH_LONG).show();
                }

            }
        });

        searchView = findViewById(R.id.floating_search_view);

        searchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

            }

            @Override
            public void onSearchAction(String currentQuery) {
                //Zoom map đến địa chỉ cần tìm
                if(currentQuery.isEmpty()){
                    Toast.makeText(PopupChooseLocation.this,"Vui lòng không để trống địa chỉ",Toast.LENGTH_LONG).show();
                }else {
                    Search(currentQuery);
                }
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
        getWindow().setLayout((int)(width*.9),(int)(height*.9));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y=-20;

        getWindow().setAttributes(params);
    }

    //hàm kiểm tra quyền của ứng dụng
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

    //Hàm thay đổi vị trí của marker
    public void dropMarker(GeoCoordinate position,boolean isSearch) {
        if(marker != null) {
            map.removeMapObject(marker);
        }

        marker = new MapMarker();
        marker.setCoordinate(position);
        map.addMapObject(marker);

        //Nếu search thì sẽ chuyển màn hình sang vị trí tìm thấy
        if(isSearch){
            //Zome đến vị trí mới
            map.setCenter(new GeoCoordinate(position.getLatitude(), position.getLongitude(), 0.0), Map.Animation.NONE);
            map.setZoomLevel(15.0);
        }

        //Thay đổi vị trí của vị trí vật lý
        triggerRevGeocodeRequest(position);
    }


    //Hàm khởi tạo map Engine
    private void initMapEngine() {

        setContentView(R.layout.activity_popup_choose_location);
        initControl();
        // Search for the map fragment to finish setup by calling init().
        mapFragment = getMapFragment();
        // Set path of isolated disk cache
        String diskCacheRoot = Environment.getExternalStorageDirectory().getPath()
                + File.separator + ".isolated-here-maps";
        //Địa chỉ để lưu trữ map trên bộ nhớ đệm
        String intentName = "com.example.designapptest";
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
            //Hiển thị map
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

                        //Thêm vào vị trí trong lần đầu tiên hiển thị lên
                        triggerRevGeocodeRequest(new GeoCoordinate(latitude,longtitude));

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
                                //Xóa marker cũ thêm vào marker mới và thay đổi vị trí tương ứng
                                dropMarker(position,false);
                                return false;
                            }
                        },0,false);

                        //Ẩn progess load đi
                        progessBarLoadMap.setVisibility(View.GONE);
                    } else {
                        progessBarLoadMap.setVisibility(View.GONE);
                        Toast.makeText(PopupChooseLocation.this,"Lỗi khi tải map",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void triggerRevGeocodeRequest(GeoCoordinate coordinate) {
        ReverseGeocodeRequest revGecodeRequest = new ReverseGeocodeRequest(coordinate);
        revGecodeRequest.execute(new ResultListener<Location>() {
            @Override
            public void onCompleted(Location location, ErrorCode errorCode) {
                if (errorCode == ErrorCode.NONE) {
                    //Lưu lại địa chỉ để trả về
                    District = location.getAddress().getDistrict();
                    City = location.getAddress().getCity();
                    Street = location.getAddress().getStreet();
                    String test = District+City+Street+No;
                    Log.d("check", test);
                    //Loại bỏ đường và Hẻm trong chuỗi trả về
                    Street = Street.replace("ĐƯỜNG","");
                    Street =Street .replace("HẺM","");
                    //remove space
                    Street = Street.trim();

                    No = location.getAddress().getHouseNumber();

                    //Update hiển thị
                    updateView();

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
                //initialize();
                //Khởi tạo map eng
                initMapEngine();
                //Gọi hàm thay đổi kích thước của màn hình
                changeDisplay();

                break;
        }
    }

    //Khởi tạo map
    private void initialize() {
        setContentView(R.layout.activity_popup_choose_location);
        initControl();
        // Search for the map fragment to finish setup by calling init().
        mapFragment = getMapFragment();
        // Set up disk cache path for the map service for this application
        boolean success = com.here.android.mpa.common.MapSettings.setIsolatedDiskCacheRootPath(
                getApplicationContext().getExternalFilesDir(null) + File.separator + ".here-maps",
                "com.example.designapptest");
        if (!success) {
            Toast.makeText(getApplicationContext(), "Unable to set isolated disk cache path.", Toast.LENGTH_LONG);
        } else {

            //Hiển thị map
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

                        //Thêm vào vị trí trong lần đầu tiên hiển thị lên
                        triggerRevGeocodeRequest(new GeoCoordinate(latitude,longtitude));



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
                                //Xóa marker cũ thêm vào marker mới và thay đổi vị trí tương ứng
                                dropMarker(position,false);
                                return false;
                            }
                        },0,false);

                        //Ẩn progess load đi
                        progessBarLoadMap.setVisibility(View.GONE);
                    } else {
                        progessBarLoadMap.setVisibility(View.GONE);
                        Toast.makeText(PopupChooseLocation.this,"Lỗi khi tải map",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
