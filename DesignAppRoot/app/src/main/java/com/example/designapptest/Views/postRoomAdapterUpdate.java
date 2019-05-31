package com.example.designapptest.Views;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.designapptest.Controller.Interfaces.ICallBackPostRoom;
import com.example.designapptest.Model.RoomModel;
import com.example.designapptest.R;

import java.util.HashMap;

public class postRoomAdapterUpdate extends AppCompatActivity implements ICallBackPostRoom, View.OnClickListener {// chưa test

    public static final String PREFS_DATA_NAME = "DataRoomPrefs";
    ImageButton btnImgLocationPushRoom, btnImgInformationPushRoom, btnImgUtilityPushRoom, btnImgConfirmPushRoom;
    TextView txtLocationPushRoom, txtInfomationPushRoom, txtUtilityPushRoom, txtComfirmPushRoom;

    //Lấy ra background blue
    Drawable blueDraw;
    Drawable grayDraw;
    //Lấy ra màu text
    int blue_color;
    int gray_color;
    Toolbar toolbar;

    //Biến kiểm tra quá trình điền thông tin đã xong hay chưa
    private boolean isCompleteLocation, isCompleteInfomation, isCompleteUtility, isCompleteConfirm;
    //End Biến kiểm tra quá trình điền thông tin đã xong hay chưa

    private ViewPager pager;

    RoomModel roomModel;

//    postRoomStep1Update postRoomStep1Update;
//
//    postRoomStep2Update postRoomStep2Update;
//
//    postRoomStep3Update postRoomStep3Update;
//
//    postRoomStep4Update postRoomStep4Update;

    // private  TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        roomModel = getIntent().getParcelableExtra("phongtro");

        setContentView(R.layout.post_room_main_view);
        initControl();
        getColor();
        addControl();
    }

    private void getColor() {
        //Lấy ra background blue
        blueDraw = getResources().getDrawable(R.drawable.custom_rim_blue);
        grayDraw = getResources().getDrawable(R.drawable.custom_rim);
        //Lấy ra màu text
        blue_color = Color.parseColor("#3498db");
        gray_color = Color.parseColor("#666666");
    }

    private void changeColor(boolean isComplete, String FragName) {
        switch (FragName) {
            case postRoomStep1.FRAG_NAME:
                if (isComplete) {
                    btnImgLocationPushRoom.setBackground(blueDraw);
                    txtLocationPushRoom.setTextColor(blue_color);
                } else {
                    btnImgLocationPushRoom.setBackground(grayDraw);
                    txtLocationPushRoom.setTextColor(gray_color);
                }
                break;
            case postRoomStep2.FRAG_NAME:
                if (isComplete) {
                    btnImgInformationPushRoom.setBackground(blueDraw);
                    txtInfomationPushRoom.setTextColor(blue_color);
                } else {
                    btnImgInformationPushRoom.setBackground(grayDraw);
                    txtInfomationPushRoom.setTextColor(gray_color);
                }
                break;

            case postRoomStep3.FRAG_NAME:
                if (isComplete) {
                    btnImgUtilityPushRoom.setBackground(blueDraw);
                    txtUtilityPushRoom.setTextColor(blue_color);
                } else {
                    btnImgUtilityPushRoom.setBackground(grayDraw);
                    txtUtilityPushRoom.setTextColor(gray_color);
                }
                break;

            case postRoomStep4.FRAG_NAME:
                if (isComplete) {
                    btnImgConfirmPushRoom.setBackground(blueDraw);
                    txtComfirmPushRoom.setTextColor(blue_color);
                } else {
                    btnImgConfirmPushRoom.setBackground(grayDraw);
                    txtComfirmPushRoom.setTextColor(gray_color);
                }
                break;
        }

    }

    private void initControl() {
        txtLocationPushRoom = findViewById(R.id.txt_location_push_room);
        txtComfirmPushRoom = findViewById(R.id.txt_comfirm_push_room);
        txtInfomationPushRoom = findViewById(R.id.txt_infomation_push_room);
        txtUtilityPushRoom = findViewById(R.id.txt_utility_push_room);

        btnImgLocationPushRoom = findViewById(R.id.btnImg_location_push_room);
        btnImgConfirmPushRoom = findViewById(R.id.btnImg_confirm_push_room);
        btnImgUtilityPushRoom = findViewById(R.id.btnImg_utility_push_room);
        btnImgInformationPushRoom = findViewById(R.id.btnImg_information_push_room);

        btnImgLocationPushRoom.setOnClickListener(this);
        btnImgConfirmPushRoom.setOnClickListener(this);
        btnImgUtilityPushRoom.setOnClickListener(this);
        btnImgInformationPushRoom.setOnClickListener(this);

        toolbar = findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Chỉnh sửa đăng phòng của bạn");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnImg_location_push_room:
                setCurrentPage(0);
                break;
            case R.id.btnImg_information_push_room:
                setCurrentPage(1);
                break;
            case R.id.btnImg_utility_push_room:
                setCurrentPage(2);
                break;
            case R.id.btnImg_confirm_push_room:
                setCurrentPage(3);
                break;
        }
    }

    private void addControl() {
        pager = (ViewPager) findViewById(R.id.viewpager_post_room);
        //  tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        FragmentManager manager = getSupportFragmentManager();
        PagerAdapter adapter = new PagerAdapter(manager);
        pager.setAdapter(adapter);
    }

    // Hàm trả về room model.
    public RoomModel returnRoomModel() {
        return  roomModel;
    }

    public void setOnPostRoomStep1Update(String City, String District, String Ward, String Street,
                                           String No, double longtitude, double latitude) {
        roomModel.setCity(City);
        roomModel.setCounty(District);
        roomModel.setWard(Ward);
        roomModel.setStreet(Street);
        roomModel.setApartmentNumber(No);
        roomModel.setLongtitude(longtitude);
        roomModel.setLatitude(latitude);
    }

    public void setOnPostRoomStep2Update(String typeID, boolean genderRoom, long currentNumber,
                                         long maxNumber, float width, float length, float priceRoom,
                                         float electricBill, float warterBill, float InternetBill, float parkingBill) {
        roomModel.setTypeID(typeID);
        roomModel.setGender(genderRoom);
        roomModel.setCurrentNumber(currentNumber);
        roomModel.setMaxNumber(maxNumber);
        roomModel.setWidth(width);
        roomModel.setLength(length);

        roomModel.setRentalCosts(priceRoom);
        roomModel.getListRoomPrice().get(0).setPrice(electricBill);
        roomModel.getListRoomPrice().get(1).setPrice(warterBill);
        roomModel.getListRoomPrice().get(2).setPrice(InternetBill);
        roomModel.getListRoomPrice().get(3).setPrice(parkingBill);
    }

    //Hàm truyền dữ liệu từ Fragment qua activity
    @Override
    public void onMsgFromFragToPostRoom(String sender, boolean isComplete) {
        if (sender == postRoomStep1Update.FRAG_NAME) {
            isCompleteLocation = isComplete;
        } else if (sender == postRoomStep2Update.FRAG_NAME) {
            isCompleteInfomation = isComplete;

        } else if (sender == postRoomStep3Update.FRAG_NAME) {
            isCompleteUtility = isComplete;

        } else if (sender == postRoomStep4Update.FRAG_NAME) {
            isCompleteConfirm = isComplete;
        }
        //Thay doi mau cho nut
        changeColor(isComplete, sender);

    }

    @Override
    public boolean isStepOneComplete() {
        return isCompleteLocation;
    }

    @Override
    public boolean isStepTwoComplete() {
        return isCompleteInfomation;
    }

    @Override
    public boolean isStepTreeComplete() {
        return isCompleteUtility;
    }

    //Hàm thay đổi page
    @Override
    public void setCurrentPage(int position) {
        pager.setCurrentItem(position);
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {
        private HashMap<Integer, Fragment> fragmentHashMap = new HashMap<>();

        PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

            //Check neu da put vao trong hashmap tuc la da khoi tao gia tri thi tra ve trong hashmap
            if (fragmentHashMap.get(position) != null) {
                return fragmentHashMap.get(position);
            }
            switch (position) {
                case 0:
                    fragment = new postRoomStep1Update();
                    fragmentHashMap.put(0, fragment);
                    break;
                case 1:
                    fragment = new postRoomStep2Update();
                    fragmentHashMap.put(1, fragment);
                    break;
                case 2:
                    fragment = new postRoomStep3Update();
                    fragmentHashMap.put(2, fragment);
                    break;
                case 3:
                    fragment = new postRoomStep4Update();
                    fragmentHashMap.put(3, fragment);
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}