package com.example.designapptest;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    GridView grVRoom;
    GridView grVLocation;
    ListView lstVRoom;
    ListView lstVSuggest;
    ArrayList<roomModel> mydata;
    ArrayList<locationModel> datalocation;
    com.example.designapptest.roomAdapter roomAdapterGid;
    com.example.designapptest.roomAdapter roomAdapterList;
    com.example.designapptest.locationAdapter locationAdapter;
    com.example.designapptest.searchAdapter searchAdapter;
    com.example.designapptest.suggestAdapter suggestAdapterList;
    Button btnChooseSearch;
    ListView lstVSearch;
    String[] dataSearch={"Vị trí","Giá cả","Số người","Tiện nghi","Map"};
    EditText edTSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();
        roomAdapterGid=new roomAdapter(this,R.layout.room_element_grid_view,mydata);
        roomAdapterList=new roomAdapter(this,R.layout.room_element_list_view,mydata);
        suggestAdapterList=new suggestAdapter(this,R.layout.suggest_element_list_view,mydata);
        locationAdapter=new locationAdapter(this,R.layout.row_element_grid_view_location,datalocation);
        searchAdapter=new searchAdapter(this,R.layout.search_element_list_view,dataSearch);
        grVRoom.setAdapter(roomAdapterGid);
        lstVRoom.setAdapter(roomAdapterList);
        grVRoom.setClickable(false);
        grVLocation.setAdapter(locationAdapter);
        lstVRoom.setClickable(false);
        lstVSearch.setAdapter(searchAdapter);
        lstVSuggest.setAdapter(suggestAdapterList);

        btnChooseSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lstVSearch.setVisibility(View.VISIBLE);
            }
        });
        lstVSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lstVSearch.setVisibility(View.INVISIBLE);
                switch (position)
                {
                    case 0:{
                        btnChooseSearch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_svg_location_search_24px,0,0,0);
                        break;
                    }
                    case 1:{
                        btnChooseSearch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_svg_coin_24px,0,0,0);
                        break;
                    }
                    case 2:{
                        btnChooseSearch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_svg_group_24px,0,0,0);
                        break;
                    }
                    case 3:{
                        btnChooseSearch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_svg_location_search_24px,0,0,0);
                        break;
                    }
                    case 4:{
                        btnChooseSearch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_svg_map_24px,0,0,0);
                        break;
                    }
                }
            }
        });
        edTSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(edTSearch.getText().length()==0)
                {
                    lstVSuggest.setVisibility(View.INVISIBLE);
                }
                else
                {
                    lstVSuggest.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

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
        lstVSuggest=(ListView) findViewById(R.id.lstV_suggest);
        edTSearch=(EditText) findViewById(R.id.edT_search);
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
