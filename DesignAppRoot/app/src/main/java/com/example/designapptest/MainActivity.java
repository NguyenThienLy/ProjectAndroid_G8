package com.example.designapptest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    GridView grVRoom;
    ListView lstVRoom;
    ArrayList<roomModel> mydata;
    com.example.designapptest.roomAdapter roomAdapterGid;
    com.example.designapptest.roomAdapter roomAdapterList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*initData();
        roomAdapterGid=new roomAdapter(this,R.layout.rom_element_grid_view,mydata);
        roomAdapterList=new roomAdapter(this,R.layout.rom_element_list_view,mydata);
        grVRoom.setAdapter(roomAdapterGid);
        lstVRoom.setAdapter(roomAdapterList);
        grVRoom.setClickable(false);
        lstVRoom.setClickable(false);*/
    }
    private void initData()
    {
        grVRoom=(GridView) findViewById(R.id.grV_room);
        lstVRoom=(ListView) findViewById(R.id.lstV_room);

        mydata=new ArrayList<>();

        mydata.add(new roomModel(R.drawable.room_1,"Cho thuê phòng trọ giá rẻ","2.5 triệu/phòng","54 Âu Cơ, Bình Thạnh, TP Hồ Chí Minh", 8, 256, "PHÒNG TRỌ"));
        mydata.add(new roomModel(R.drawable.room_1,"Cho thuê phòng trọ giá rẻ","3.5 triệu/phòng","54 Âu Cơ, Quận 11, TP Hồ Chí Minh", 6, 18, "PHÒNG TRỌ"));
        mydata.add(new roomModel(R.drawable.room_1,"Cho thuê phòng trọ giá rẻ","2.5 triệu/phòng","54 Âu Cơ, Bình Thạnh, TP Hồ Chí Minh", 5, 365, "CHUNG CƯ"));
        mydata.add(new roomModel(R.drawable.room_1,"Cho thuê phòng trọ giá rẻ","3.5 triệu/phòng","54 Âu Cơ, Quận 11, TP Hồ Chí Minh", 4, 256, "PHÒNG TRỌ"));
        mydata.add(new roomModel(R.drawable.room_1,"Cho thuê phòng trọ giá rẻ","2.5 triệu/phòng","54 Âu Cơ, Bình Thạnh, TP Hồ Chí Minh", 6, 28, "KÍ TÚC XÁ"));
        mydata.add(new roomModel(R.drawable.room_1,"Cho thuê phòng trọ giá rẻ","3.5 triệu/phòng","54 Âu Cơ, Quận 11, TP Hồ Chí Minh", 7, 147, "PHÒNG TRỌ"));
    }
}
