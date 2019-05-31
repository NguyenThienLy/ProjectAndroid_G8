package com.example.designapptest.Controller;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.designapptest.Adapters.AdapterRecyclerFindRoom;
import com.example.designapptest.Controller.Interfaces.IFindRoomModel;
import com.example.designapptest.Controller.Interfaces.IPostRoomUpdateModel;
import com.example.designapptest.Controller.Interfaces.IUpdateRoomModel;
import com.example.designapptest.Model.FindRoomFilterModel;
import com.example.designapptest.Model.FindRoomModel;
import com.example.designapptest.Model.RoomModel;
import com.example.designapptest.R;
import com.example.designapptest.Views.postRoomAdapterUpdate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class PostRoomUpdateController {
    Context context;
    RoomModel roomModel;

    public PostRoomUpdateController(Context context) {
        this.context = context;
        roomModel = new RoomModel();
    }

    public void postRoomStep1Update(String roomID, String city, String district, String ward, String street, String no, double longtitude, double latitude,
                                    String oldCity, String oldDistrict, String oldWard, String oldStreet, String oldNo, IUpdateRoomModel iUpdateRoomModel) {

        //Tạo interface để truyền dữ liệu lên từ model
        IPostRoomUpdateModel iPostRoomUpdateModel = new IPostRoomUpdateModel() {
            @Override
            public void getSuccessNotifyPostRoomStep1() {
                Toast.makeText(context, "Cập nhật phòng thành công!", Toast.LENGTH_SHORT).show();

               // postRoomAdapterUpdate.setOnPostRoomStep1Update(city, district, ward, street, no, longtitude, latitude);
            }

            @Override
            public void getSuccessNotifyPostRoomStep2() {
            }

            @Override
            public void getSuccessNotifyPostRoomStep3() {
            }

            @Override
            public void getSuccessNotifyPostRoomStep4() {
            }

            @Override
            public void getRoomFollowId(RoomModel roomModel) {
                iUpdateRoomModel.getSuccessNotifyRoomMode1(roomModel);
            }
        };

        //Gọi hàm lấy dữ liệu trong model
        roomModel.postRoomStep1Update(roomID, city, district, ward, street, no, iPostRoomUpdateModel, longtitude, latitude,
                oldCity, oldDistrict, oldWard, oldStreet, oldNo);
    }

    public void postRoomStep2Update(String roomId, String typeId, boolean genderRoom, long currentNumber, long maxNumber, float width, float length,
                                    float priceRoom, float electricBill, float warterBill, float InternetBill, float parkingBill, IUpdateRoomModel iUpdateRoomModel) {

        //Tạo interface để truyền dữ liệu lên từ model
        IPostRoomUpdateModel iPostRoomUpdateModel = new IPostRoomUpdateModel() {
            @Override
            public void getSuccessNotifyPostRoomStep1() {

            }

            @Override
            public void getSuccessNotifyPostRoomStep2() {
                Toast.makeText(context, "Cập nhật phòng thành công!", Toast.LENGTH_SHORT).show();

//                postRoomAdapterUpdate.setOnPostRoomStep2Update( typeId,  genderRoom,  currentNumber,
//                 maxNumber,  width,  length,  priceRoom,
//                 electricBill,  warterBill,  InternetBill,  parkingBill);

            }

            @Override
            public void getSuccessNotifyPostRoomStep3() {
            }

            @Override
            public void getSuccessNotifyPostRoomStep4() {
            }

            @Override
            public void getRoomFollowId(RoomModel roomModel) {
                iUpdateRoomModel.getSuccessNotifyRoomMode1(roomModel);
            }

        };

        //Gọi hàm lấy dữ liệu trong model
        roomModel.postRoomStep2Update(roomId, typeId, genderRoom, maxNumber, width, length,
                priceRoom, electricBill, warterBill, InternetBill, parkingBill, iPostRoomUpdateModel);
    }

    public void postRoomStep3Update(String roomId, String owner,  List<String> listConvenient, List<String> listPathImageChoosed,
                                    boolean isChangeConvenient, boolean isChangeImageRoom, IUpdateRoomModel iUpdateRoomModel) {

        //Tạo interface để truyền dữ liệu lên từ model
        IPostRoomUpdateModel iPostRoomUpdateModel = new IPostRoomUpdateModel() {
            @Override
            public void getSuccessNotifyPostRoomStep1() {
            }

            @Override
            public void getSuccessNotifyPostRoomStep2() {
            }

            @Override
            public void getSuccessNotifyPostRoomStep3() {
                Toast.makeText(context, "Cập nhật phòng thành công!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void getSuccessNotifyPostRoomStep4() {

            }

            @Override
            public void getRoomFollowId(RoomModel roomModel) {
                iUpdateRoomModel.getSuccessNotifyRoomMode1(roomModel);
            }

        };

        //Gọi hàm lấy dữ liệu trong model
        roomModel.postRoomStep3Update(roomId, owner, listConvenient, listPathImageChoosed,
                isChangeConvenient, isChangeImageRoom, iPostRoomUpdateModel, context);
    }

    public void postRoomStep4Update(String roomId, String name, String describe, IUpdateRoomModel iUpdateRoomModel) {

        //Tạo interface để truyền dữ liệu lên từ model
        IPostRoomUpdateModel iPostRoomUpdateModel = new IPostRoomUpdateModel() {
            @Override
            public void getSuccessNotifyPostRoomStep1() {
            }

            @Override
            public void getSuccessNotifyPostRoomStep2() {
            }

            @Override
            public void getSuccessNotifyPostRoomStep3() {
            }

            @Override
            public void getSuccessNotifyPostRoomStep4() {
                Toast.makeText(context, "Cập nhật phòng thành công!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void getRoomFollowId(RoomModel roomModel) {
                iUpdateRoomModel.getSuccessNotifyRoomMode1(roomModel);
            }
        };

        //Gọi hàm lấy dữ liệu trong model
        roomModel.postRoomStep4Update(roomId, name, describe, iPostRoomUpdateModel);
    }
}
