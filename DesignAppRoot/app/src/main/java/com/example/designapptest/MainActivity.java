package com.example.designapptest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    GridView grVRoom;
    GridView grVLocation;
    ListView lstVRoom;
    ArrayList<roomModel> mydata;
    ArrayList<locationModel> datalocation;
    com.example.designapptest.roomAdapter roomAdapterGid;
    com.example.designapptest.roomAdapter roomAdapterList;
    com.example.designapptest.locationAdapter locationAdapter;
    com.example.designapptest.searchAdapter searchAdapter;
    Button btnChooseSearch;
    ListView lstVSearch;
    String[] dataSearch={"Vị trí","Giá cả","Số","Tiện nghi","Map"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();
        roomAdapterGid=new roomAdapter(this,R.layout.room_element_grid_view,mydata);
        roomAdapterList=new roomAdapter(this,R.layout.room_element_list_view,mydata);
        locationAdapter=new locationAdapter(this,R.layout.row_element_grid_view_location,datalocation);
        searchAdapter=new searchAdapter(this,R.layout.search_element_list_view,dataSearch);
        grVRoom.setAdapter(roomAdapterGid);
        lstVRoom.setAdapter(roomAdapterList);
        grVRoom.setClickable(false);
        grVLocation.setAdapter(locationAdapter);
        lstVRoom.setClickable(false);
        lstVSearch.setAdapter(searchAdapter);

        btnChooseSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lstVSearch.setVisibility(View.VISIBLE);
            }
        });
    }
    private void AnhXa()
    {
        grVRoom=(GridView) findViewById(R.id.grV_room);
        lstVRoom=(ListView) findViewById(R.id.lstV_room);
        grVLocation=(GridView) findViewById(R.id.grV_location);
        btnChooseSearch=(Button) findViewById(R.id.btn_choose_search);
        lstVSearch=(ListView) findViewById(R.id.lstV_search);
        mydata=new ArrayList<>();
        mydata.add(new roomModel(R.drawable.image_room,"Cho thuê phòng trọ giá rẻ","2.5 triệu/phòng","54 Âu Cơ, Bình Thạnh, TP Hồ Chí Minh", 8, 256, "PHÒNG TRỌ"));
        mydata.add(new roomModel(R.drawable.image_room,"Cho thuê phòng trọ giá rẻ","3.5 triệu/phòng","54 Âu Cơ, Quận 11, TP Hồ Chí Minh", 6, 18, "PHÒNG TRỌ"));
        mydata.add(new roomModel(R.drawable.image_room,"Cho thuê phòng trọ giá rẻ","2.5 triệu/phòng","54 Âu Cơ, Bình Thạnh, TP Hồ Chí Minh", 5, 365, "CHUNG CƯ"));
        mydata.add(new roomModel(R.drawable.image_room,"Cho thuê phòng trọ giá rẻ","3.5 triệu/phòng","54 Âu Cơ, Quận 11, TP Hồ Chí Minh", 4, 256, "PHÒNG TRỌ"));
        mydata.add(new roomModel(R.drawable.image_room,"Cho thuê phòng trọ giá rẻ","2.5 triệu/phòng","54 Âu Cơ, Bình Thạnh, TP Hồ Chí Minh", 6, 28, "KÍ TÚC XÁ"));
        mydata.add(new roomModel(R.drawable.image_room,"Cho thuê phòng trọ giá rẻ","3.5 triệu/phòng","54 Âu Cơ, Quận 11, TP Hồ Chí Minh", 7, 147, "PHÒNG TRỌ"));
        datalocation=new ArrayList<locationModel>();
        datalocation.add(new locationModel(R.drawable.image_binh_thanh,"Bình Thạnh","6856 phòng"));
        datalocation.add(new locationModel(R.drawable.image_thu_duc,"Quận 1","4875 phòng"));
        datalocation.add(new locationModel(R.drawable.image_quan_nhat,"Thủ Đức","4229 phòng"));
    }
}
