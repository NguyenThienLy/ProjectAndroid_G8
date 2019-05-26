package com.example.designapptest.Controller;

import com.example.designapptest.Controller.Interfaces.IMapRoomModel;
import com.example.designapptest.Model.MapRoomModel;
import com.example.designapptest.Model.RoomModel;
import com.example.designapptest.R;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapMarker;

import java.io.IOException;

public class searchMapViewController {

    MapRoomModel mapRoomModel;

    public searchMapViewController(){
        mapRoomModel = new MapRoomModel();
    }

    public void listLocationRoom(Map map){
        IMapRoomModel iMapRoomModel = new IMapRoomModel() {
            @Override
            public void getListLocationRoom(RoomModel valueRoom) {

                //Custom marker with image
                com.here.android.mpa.common.Image myImage = new com.here.android.mpa.common.Image();

                try {
                    myImage.setImageResource(R.drawable.icon_png_show_map_40);
                }catch (IOException ex){

                }

                MapMarker defaultMarker = new MapMarker();
                defaultMarker.setIcon(myImage);
                defaultMarker.setDescription("quiffffffffffffffffffffffffffffffffffffffffffffffff");
                defaultMarker.setCoordinate(new GeoCoordinate(valueRoom.getLatitude(),valueRoom.getLongtitude(), 0.0));
                map.addMapObject(defaultMarker);
            }
        };

        mapRoomModel.listLocationRoom(iMapRoomModel);
    }
}
