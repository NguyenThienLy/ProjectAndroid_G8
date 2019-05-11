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
import com.example.designapptest.R;

import java.util.HashMap;

public class postRoomAdapter extends AppCompatActivity implements ICallBackPostRoom, View.OnClickListener {// chưa test

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

    // private  TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            getSupportActionBar().setTitle("Đăng phòng của bạn");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
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

    //Hàm truyền dữ liệu từ Fragment qua activity
    @Override
    public void onMsgFromFragToPostRoom(String sender, boolean isComplete) {
        if (sender == postRoomStep1.FRAG_NAME) {
            isCompleteLocation = isComplete;
        } else if (sender == postRoomStep2.FRAG_NAME) {
            isCompleteInfomation = isComplete;

        } else if (sender == postRoomStep3.FRAG_NAME) {
            isCompleteUtility = isComplete;

        } else if (sender == postRoomStep4.FRAG_NAME) {
            isCompleteConfirm = isComplete;
        }
        //Thay doi mau cho nut
        changeColor(isComplete,sender);

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
                    fragment = new postRoomStep1();
                    fragmentHashMap.put(0, fragment);
                    break;
                case 1:
                    fragment = new postRoomStep2();
                    fragmentHashMap.put(1, fragment);
                    break;
                case 2:
                    fragment = new postRoomStep3();
                    fragmentHashMap.put(2, fragment);
                    break;
                case 3:
                    fragment = new postRoomStep4();
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
