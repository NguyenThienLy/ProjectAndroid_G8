package com.example.designapptest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class postRoomStep4 extends Fragment implements View.OnClickListener {

    //Biến filnal lưu tên fragment
    public static final String FRAG_NAME = "POST_ROOM_STEP_4";
    //End biến filnal lưu tên fragment

    //Biến sharedPreferences để truyền dữ liệu giữa các fragment trong viewpager
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    public static String ADDRESS = "ADDRESS";
    //End Biến sharedPreferences để truyền dữ liệu giữa các fragment trong viewpager

    //Biến lưu thông tin từ fragment 1
    String District,City,Ward,Street,No;
    Float longtitude,latitude;
    //End Biến lưu thông tin fragment 1

    //Biến truyền thông tin từ fragment 2
    boolean genderRoom;
    String typeID;
    long currentNumber,maxNumber;
    float width,length;
    float priceRomm,electricBill,warterBill,InternetBill,parkingBill;
    //End biến truyền thông tin từ fragment 2

    //Biến truyền thông tin từ fragmet 3
    List<String> listConvenient;
    List<String> listPathImageChoosed;
    //End biến truyền thông tin từ frament 3

    //Biến lưu thông tin từ frament này
    String name,describe;
    //End biến lưu thông tin từ fragment này

    //Control
    Button btnNextStep4PostRoom;
    EditText edtNamePushRoom,edtDescribePushRoom;
    //End Control

    postRoomAdapter postRoom;

    public postRoomStep4(){

    }


    //Lấy dữ liệu từ sharePreference
    private void getDataFromPreference(){
        sharedpreferences = this.getActivity().getSharedPreferences(postRoomAdapter.PREFS_DATA_NAME, Context.MODE_PRIVATE);

        //Lấy dữ liệu từ fragment 1
        Street = sharedpreferences.getString(postRoomStep1.SHARE_STREET,"");
        City = sharedpreferences.getString(postRoomStep1.SHARE_CITY,"");
        District = sharedpreferences.getString(postRoomStep1.SHARE_DISTRICT,"");
        Ward = sharedpreferences.getString(postRoomStep1.SHARE_WARD,"");
        No = sharedpreferences.getString(postRoomStep1.SHARE_NO,"");

        longtitude=sharedpreferences.getFloat(postRoomStep1.SHARE_LONGTITUDE,0);
        latitude=sharedpreferences.getFloat(postRoomStep1.SHARE_LATITUDE,0);
        //End lấy dữ liệu từ fragment 1

        //Lấy dữ liệu từ fragment2
        genderRoom = sharedpreferences.getBoolean(postRoomStep2.SHARE_GENDER_ROOM,true);
        typeID = sharedpreferences.getString(postRoomStep2.SHARE_TYPEID,"");

        currentNumber = sharedpreferences.getLong(postRoomStep2.SHARE_CURRENTNUMBER,0);
        maxNumber = sharedpreferences.getLong(postRoomStep2.SHARE_MAXNUMBER,0);

        width = sharedpreferences.getFloat(postRoomStep2.SHARE_WIDTH,0);
        length = sharedpreferences.getFloat(postRoomStep2.SHARE_LENGTH,0);

        priceRomm = sharedpreferences.getFloat(postRoomStep2.SHARE_RPICEROOM,0);
        electricBill = sharedpreferences.getFloat(postRoomStep2.SHARE_ELECTRICB,0);
        warterBill = sharedpreferences.getFloat(postRoomStep2.SHARE_WARTERB,0);
        InternetBill = sharedpreferences.getFloat(postRoomStep2.SHARE_INTERNETB,0);
        parkingBill = sharedpreferences.getFloat(postRoomStep2.SHARE_PARKINGB,0);
        //End lấy dữ liệu từ fragment2

        //Lấy dữ liệu từ frament 3
        Set<String> setTemp = new HashSet<String>();
        Set<String> setConvenient = sharedpreferences.getStringSet(postRoomStep3.SHARE_LIST_CONVENIENT,setTemp);
        listConvenient = new ArrayList<String>();
        listConvenient.addAll(setConvenient);

        Set<String> setPathImage = sharedpreferences.getStringSet(postRoomStep3.SHARE_LISTPATHIMAGE,setTemp);
        listPathImageChoosed = new ArrayList<String>();
        listPathImageChoosed.addAll(setPathImage);
        //End lấy dữ liệu từ fragment 3

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.post_room_step_4_view, container, false);
        initControl(layout);

        //Lấy context của fragment
        postRoom = (postRoomAdapter) getContext();

        return layout;
    }

    private void initControl(View view){
        edtNamePushRoom = view.findViewById(R.id.edt_name_push_room);
        edtDescribePushRoom = view.findViewById(R.id.edt_describe_push_room);

        btnNextStep4PostRoom =view.findViewById(R.id.btn_nextStep4_post_room);
        btnNextStep4PostRoom.setOnClickListener(this);
    }

    //Hàm lấy giá trị người dùng nhập từ control
    private void getDataFromControl(){
        name = edtNamePushRoom.getText().toString();
        describe = edtDescribePushRoom.getText().toString();
    }

    //Hàm kiểm tra dữ liệu đúng từ control
    private boolean checkTrueDataFromControl(){
        if(name.matches("")||describe.matches("")){
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        int id= v.getId();
        switch (id){
            case R.id.btn_nextStep4_post_room:
                //Lấy dữ liệu từ các fragment
                getDataFromPreference();
                //Lấy dữ liệu từ control
                getDataFromControl();
                //Kiểm tra dữ liệu nếu đúng thì thực hiện
                if(checkTrueDataFromControl()==true){

                    changeColorInActivity(true);
                    //Kiểm tra xem những step trước đã hoàn thành chưa
                    if(postRoom.isStepOneComplete()==true&postRoom.isStepTwoComplete()&&postRoom.isStepTreeComplete()){
                        Toast.makeText(getContext(),name + describe,Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    //Hàm chuyển màu ở activity
    private void changeColorInActivity(boolean isComplete) {
        //Gọi hàm trong posrRoom Thông qua Activity
        postRoom.onMsgFromFragToPostRoom(FRAG_NAME, isComplete);
    }
}
