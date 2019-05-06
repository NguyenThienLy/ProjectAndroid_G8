package com.example.designapptest.Views;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.designapptest.Adapters.AdapterRecyclerFilter;
import com.example.designapptest.ClassOther.myFilter;
import com.example.designapptest.Controller.Interfaces.ICallBackSearchView;
import com.example.designapptest.Controller.searchViewController;
import com.example.designapptest.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class searchView extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, ICallBackSearchView {

    //Lưu lại trạng thái của 4 fragment thay vì tạo mới
    private HashMap<Integer, Fragment> fragmentHashMap = new HashMap<>();

    CheckBox chBoxPrice,chBoxConvenient,chBoxType,chBoxNumber;
    RecyclerView recyclerFilter,recyclerSearchRoom;
    TextView txtNumberRoom;
    Button btnsSubmit;
    EditText edTSearch;

    FrameLayout fragmentContainer;

    Drawable blueUp;
    Drawable grayDown;

    int blueColor;
    int grayColor;

    List<myFilter> filterList;
    AdapterRecyclerFilter adapterRecyclerFilter;

    String district;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);

        initData();
        initControl();
        getColor();

    }

    //Lấy màu từ resource
    private void getColor(){
        blueUp = getResources().getDrawable(R.drawable.ic_svg_up_24);
        grayDown = getResources().getDrawable(R.drawable.ic_svg_down_2_15);
        blueColor = getResources().getColor(R.color.success);
        grayColor= getResources().getColor(R.color.unsuccess);
    }

    private void initData(){
        filterList = new ArrayList<myFilter>();
    }

    private void initControl(){
        edTSearch = findViewById(R.id.edT_search);

        txtNumberRoom =findViewById(R.id.txt_number);

        chBoxPrice=findViewById(R.id.chBox_price);
        chBoxConvenient=findViewById(R.id.chBox_convenient);
        chBoxType=findViewById(R.id.chBox_type);
        chBoxNumber=findViewById(R.id.chBox_number);

        chBoxPrice.setOnClickListener(this);
        chBoxConvenient.setOnClickListener(this);
        chBoxType.setOnClickListener(this);
        chBoxNumber.setOnClickListener(this);

        chBoxPrice.setOnCheckedChangeListener(this);
        chBoxConvenient.setOnCheckedChangeListener(this);
        chBoxType.setOnCheckedChangeListener(this);
        chBoxNumber.setOnCheckedChangeListener(this);

        fragmentContainer = findViewById(R.id.fragment_container);

        recyclerFilter = findViewById(R.id.recycler_filter);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL);
        recyclerFilter.setLayoutManager(staggeredGridLayoutManager);

        adapterRecyclerFilter = new AdapterRecyclerFilter(this,R.layout.search_filter_element_recyclerview,filterList);
        recyclerFilter.setAdapter(adapterRecyclerFilter);

        recyclerSearchRoom = findViewById(R.id.recycler_search_room);

        btnsSubmit = findViewById(R.id.btn_submit);
        btnsSubmit.setOnClickListener(this);

    }

    private void getDataFromControl(){
        district = edTSearch.getText().toString();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btn_submit) {
            getDataFromControl();
            callSearchRoomController();
        }
        else{
            boolean isChecked = ((CheckBox)v).isChecked();
            if(isChecked){
                //Hiện fragment
                fragmentContainer.setVisibility(View.VISIBLE);

                //Replace fragment
                switch (id){
                    case R.id.chBox_price:
                        GroupCheckbox(0);
                        changeFragment(0);
                        break;
                    case R.id.chBox_convenient:
                        GroupCheckbox(1);
                        changeFragment(1);
                        break;
                    case R.id.chBox_type:
                        GroupCheckbox(2);
                        changeFragment(2);
                        break;
                    case R.id.chBox_number:
                        GroupCheckbox(3);
                        changeFragment(3);
                        break;
                }
            }else {
                //Ẩn fragment
                fragmentContainer.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        switch (id){
            case R.id.chBox_price:
                setColorForCheckBox(chBoxPrice,isChecked);
                break;
            case R.id.chBox_convenient:
                setColorForCheckBox(chBoxConvenient,isChecked);
                break;
            case R.id.chBox_type:
                setColorForCheckBox(chBoxType,isChecked);
                break;
            case R.id.chBox_number:
                setColorForCheckBox(chBoxNumber,isChecked);
                break;
        }
    }

    private void setColorForCheckBox(CheckBox chBox,boolean isChecked){
        if(isChecked){
            chBox.setTextColor(blueColor);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                chBox.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,blueUp,null);
            }
        }else {
            chBox.setTextColor(grayColor);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                chBox.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,grayDown,null);
            }
        }
    }

    //Group checkbox
    private void GroupCheckbox(int i){
        switch (i){
            case 0:
                chBoxConvenient.setChecked(false);
                chBoxType.setChecked(false);
                chBoxNumber.setChecked(false);
                break;
            case 1:
                chBoxPrice.setChecked(false);
                chBoxType.setChecked(false);
                chBoxNumber.setChecked(false);
                break;
            case 2:
                chBoxPrice.setChecked(false);
                chBoxConvenient.setChecked(false);
                chBoxNumber.setChecked(false);
                break;
            case 3:
                chBoxPrice.setChecked(false);
                chBoxConvenient.setChecked(false);
                chBoxType.setChecked(false);
                break;
        }
    }

    //Hàm thay đổi fragment
    private void changeFragment(int i){
        Fragment fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //Lấy fragment từ hashmap nếu đã tồn tại ngược lại tạo mới và push vào hash map
        if(fragmentHashMap.get(i)!=null){
            fragmentTransaction.replace(R.id.fragment_container,fragmentHashMap.get(i));
        }
        else {
            switch (i){
                case 0:
                    fragment = new FragmentPrice();
                    fragmentHashMap.put(0,fragment);
                    fragmentTransaction.replace(R.id.fragment_container,fragment);
                    break;
                case 1:
                    fragment = new FragmentConvenient();
                    fragmentHashMap.put(1,fragment);
                    fragmentTransaction.replace(R.id.fragment_container,fragment);
                    break;
                case 2:
                    fragment = new FragmentTypeRoom();
                    fragmentHashMap.put(2,fragment);
                    fragmentTransaction.replace(R.id.fragment_container,fragment);
                    break;
                case 3:
                    fragment = new FragmentNumberPeople();
                    fragmentHashMap.put(3,fragment);
                    fragmentTransaction.replace(R.id.fragment_container,fragment);
                    break;
            }
        }

        fragmentTransaction.commit();
    }


    @Override
    public void addFilter(myFilter filter) {
        filterList.add(filter);
        adapterRecyclerFilter.notifyDataSetChanged();
    }

    @Override
    public void replaceFilter(myFilter filter) {

    }

    @Override
    public void removeFilter(myFilter filter) {
        filterList.remove(filter);
        adapterRecyclerFilter.notifyDataSetChanged();
    }

    //Hàm gọi hàm tìm kiếm trong controller
    private void callSearchRoomController(){
        searchViewController controller = new searchViewController(this,district,filterList);
        controller.loadSearchRoom();
    }
}
