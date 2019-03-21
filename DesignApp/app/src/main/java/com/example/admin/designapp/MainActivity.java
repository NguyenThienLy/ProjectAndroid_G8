package com.example.admin.designapp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    GridView gridView;
    ArrayList<ClassRoom> mydata;
    RoomAdapter roomAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();
        roomAdapter=new RoomAdapter(this,R.layout.layout,mydata);
        gridView.setAdapter(roomAdapter);
        gridView.setClickable(false);
    }
    private void AnhXa()
    {
        gridView=(GridView) findViewById(R.id.gvRoom);
        mydata=new ArrayList<>();
        mydata.add(new ClassRoom(R.drawable.room_1,"Cho thuê phòng trọ giá rẻ","2.5 triệu/phòng","54 Âu Cơ, Bình Thạnh, TP Hồ Chí Minh"));
        mydata.add(new ClassRoom(R.drawable.room_1,"Cho thuê phòng trọ giá rẻ","3.5 triệu/phòng","54 Âu Cơ, Quận 11, TP Hồ Chí Minh"));
        mydata.add(new ClassRoom(R.drawable.room_1,"Cho thuê phòng trọ giá rẻ","2.5 triệu/phòng","54 Âu Cơ, Bình Thạnh, TP Hồ Chí Minh"));
        mydata.add(new ClassRoom(R.drawable.room_1,"Cho thuê phòng trọ giá rẻ","3.5 triệu/phòng","54 Âu Cơ, Quận 11, TP Hồ Chí Minh"));
        mydata.add(new ClassRoom(R.drawable.room_1,"Cho thuê phòng trọ giá rẻ","2.5 triệu/phòng","54 Âu Cơ, Bình Thạnh, TP Hồ Chí Minh"));
        mydata.add(new ClassRoom(R.drawable.room_1,"Cho thuê phòng trọ giá rẻ","3.5 triệu/phòng","54 Âu Cơ, Quận 11, TP Hồ Chí Minh"));
    }
}
