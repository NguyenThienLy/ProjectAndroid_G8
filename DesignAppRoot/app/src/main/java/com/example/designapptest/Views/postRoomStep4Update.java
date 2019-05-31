package com.example.designapptest.Views;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.designapptest.Controller.Interfaces.IUpdateRoomModel;
import com.example.designapptest.Controller.PostRoomStep4Controller;
import com.example.designapptest.Controller.PostRoomUpdateController;
import com.example.designapptest.Model.RoomModel;
import com.example.designapptest.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class postRoomStep4Update extends Fragment implements View.OnClickListener {

    //Biến filnal lưu tên fragment
    public static final String FRAG_NAME = "POST_ROOM_STEP_4";
    //End biến filnal lưu tên fragment

    //Biến lưu thông tin từ frament này
    String name,describe;
    //End biến lưu thông tin từ fragment này

    //Biến lưu user id
    String UID;

    //Control
    Button btnNextStep4PostRoom;
    EditText edtNamePushRoom,edtDescribePushRoom;
    //End Control

    postRoomAdapterUpdate postRoom;

    // Biến lưu thông tin cũ
    RoomModel roomModel;

    // Controller cập nhật thông tin phòng
    PostRoomUpdateController postRoomUpdateController;

    ProgressDialog progressDialog;

    public postRoomStep4Update(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.post_room_step_4_view, container, false);
        initControl(layout);

        //Lấy context của fragment
        postRoom = (postRoomAdapterUpdate) getContext();

        roomModel = postRoom.returnRoomModel();

        initData();

        return layout;
    }

    private void initControl(View view){
        edtNamePushRoom = view.findViewById(R.id.edt_name_push_room);
        edtDescribePushRoom = view.findViewById(R.id.edt_describe_push_room);

        btnNextStep4PostRoom =view.findViewById(R.id.btn_nextStep4_post_room);
        btnNextStep4PostRoom.setOnClickListener(this);

        progressDialog = new ProgressDialog(getContext());
    }

    private void initData() {
        edtNamePushRoom.setText(roomModel.getName());
        edtDescribePushRoom.setText(roomModel.getDescribe());
    }

    //Hàm lấy giá trị người dùng nhập từ control
    private void getDataFromControl(){
        name = edtNamePushRoom.getText().toString();
        describe = edtDescribePushRoom.getText().toString();
    }

    // Hàm kiểm tra xem người dùng có thay đổi thông tin không
    private boolean compareInfo() {
        // Có trùng tên không
        if (name.equals(roomModel.getName()) == true
        && describe.equals(roomModel.getDescribe()) == true)
            return  false;

        return true;
    }

    //Hàm kiểm tra dữ liệu đúng từ control
    private boolean checkTrueDataFromControl(){
        if(name.matches("")||describe.matches("")){
            return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        int id= v.getId();
        switch (id){
            case R.id.btn_nextStep4_post_room:
                //Lấy dữ liệu từ các fragment
               // getDataFromPreference();
                //Lấy dữ liệu từ control
                getDataFromControl();
                //Kiểm tra dữ liệu nếu đúng thì thực hiện
                if(checkTrueDataFromControl()==true){
                    if (compareInfo() == true) {
                        postRoomUpdateController = new PostRoomUpdateController(getContext());

                        IUpdateRoomModel iUpdateRoomModel = new IUpdateRoomModel() {
                            @Override
                            public void getSuccessNotifyRoomMode1(RoomModel roomModel1) {
                                roomModel = roomModel1;

                                initData();
                            }
                        };

                        postRoomUpdateController.postRoomStep4Update(roomModel.getRoomID(), name, describe, iUpdateRoomModel);
                    }
                    else {
                        Toast.makeText(getContext(),"Bạn chưa thay đổi thông tin nào cả",Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    //Thông báo lỗi
                    Toast.makeText(getContext(),"Vui lòng điền đầy đủ thông tin",Toast.LENGTH_LONG).show();
                    //Dổi màu thành màu xám ở activity
                    changeColorInActivity(false);
                }
                break;
        }
    }

    //Truyền tham số và gọi hàm thêm phòng ở controller
    @RequiresApi(api = Build.VERSION_CODES.O)

    //Hàm chuyển màu ở activity
    private void changeColorInActivity(boolean isComplete) {
        //Gọi hàm trong posrRoom Thông qua Activity
        postRoom.onMsgFromFragToPostRoom(FRAG_NAME, isComplete);
    }
}
