package com.example.designapptest.Controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.example.designapptest.Controller.Interfaces.ICallBackFromAddRoom;
import com.example.designapptest.Model.RoomModel;

import java.util.List;

public class PostRoomStep4Controller {
    RoomModel roomModel;
    Context context;

    public PostRoomStep4Controller(Context context){
        this.roomModel = new RoomModel();
        this.context=context;
    }

    public void callAddRoomFromModel(String UID, RoomModel dataRoom, List<String> listConvenient, List<String> listPathImg,
                                     float electricBill, float warterBill, float InternetBill, float parkingBill, ProgressDialog progressDialog){

        ICallBackFromAddRoom iCallBackFromAddRoom = new ICallBackFromAddRoom() {
            @Override
            public void stopProgess(boolean isSuccess) {
                if(isSuccess){
                    //Stop progess
                    progressDialog.dismiss();
                    Toast.makeText(context,"Thêm thành công",Toast.LENGTH_SHORT).show();

                }else {
                    //do nothing
                }
            }
        };

        //Gọi hàm thêm phòng ở model
        roomModel.addRoom(UID, dataRoom,listConvenient,listPathImg,electricBill,warterBill,InternetBill,parkingBill,iCallBackFromAddRoom,context);
    }
}
