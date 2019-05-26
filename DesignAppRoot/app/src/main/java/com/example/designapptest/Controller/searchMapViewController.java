package com.example.designapptest.Controller;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.designapptest.Adapters.AdapterRecyclerMainRoom;
import com.example.designapptest.Controller.Interfaces.IMainRoomModel;
import com.example.designapptest.Controller.Interfaces.IMapRoomModel;
import com.example.designapptest.Model.MapRoomModel;
import com.example.designapptest.Model.RoomModel;
import com.example.designapptest.R;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapMarker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class searchMapViewController {

    MapRoomModel mapRoomModel;
    Context context;
    RoomModel roomModel;

    public searchMapViewController(Context context){
        mapRoomModel = new MapRoomModel();
        roomModel = new RoomModel();
        this.context=context;
    }

    public void listLocationRoom(Map map){
        IMapRoomModel iMapRoomModel = new IMapRoomModel() {
            @Override
            public void getListLocationRoom(RoomModel valueRoom) {

                //Custom marker with image
                com.here.android.mpa.common.Image myImage = new com.here.android.mpa.common.Image();

                try {
                    myImage.setImageResource(R.drawable.ic_test_1);
                }catch (IOException ex){

                }

                MapMarker defaultMarker = new MapMarker();
               defaultMarker.setIcon(myImage);
                defaultMarker.setTitle(valueRoom.getRoomID());
                defaultMarker.setCoordinate(new GeoCoordinate(valueRoom.getLatitude(),valueRoom.getLongtitude(), 0.0));
                defaultMarker.unsetVisibleMask(1,12);
                map.addMapObject(defaultMarker);

            }
        };

        mapRoomModel.listLocationRoom(iMapRoomModel);
    }

    public void listInfoRoom(RecyclerView recyclerView, List<String> listRoomID, ProgressBar progressBar){
        final List<RoomModel> roomModelList = new ArrayList<>();

        //Tạo layout cho danh sách trọ
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        //Tạo adapter cho recycle view
        final AdapterRecyclerMainRoom adapterRecyclerMainRoom = new AdapterRecyclerMainRoom(context, roomModelList, R.layout.room_element_list_view,"");
        //Cài adapter cho recycle
        recyclerView.setAdapter(adapterRecyclerMainRoom);

        IMainRoomModel iMainRoomModel = new IMainRoomModel() {
            int i = 0;
            @Override
            public void getListMainRoom(RoomModel valueRoom) {
                i++;
                roomModelList.add(valueRoom);
                adapterRecyclerMainRoom.notifyDataSetChanged();
                if(i==listRoomID.size()){
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void refreshListFavoriteRoom() {

            }

            @Override
            public void makeToast(String message) {

            }

            @Override
            public void setIconFavorite(int iconRes) {

            }
        };

        roomModel.SenData(listRoomID,iMainRoomModel);
    }

}
