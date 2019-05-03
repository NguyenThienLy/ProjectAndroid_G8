package com.example.designapptest.Views;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;

import com.example.designapptest.R;

import java.util.HashMap;

public class searchView extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    //Lưu lại trạng thái của 4 fragment thay vì tạo mới
    private HashMap<Integer, Fragment> fragmentHashMap = new HashMap<>();

    CheckBox chBoxPrice,chBoxConvenient,chBoxType,chBoxNumber;
    RecyclerView recyclerFilter;
    FrameLayout fragmentContainer;

    Drawable blueUp;
    Drawable grayDown;

    int blueColor;
    int grayColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);

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

    private void initControl(){
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
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
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

}
