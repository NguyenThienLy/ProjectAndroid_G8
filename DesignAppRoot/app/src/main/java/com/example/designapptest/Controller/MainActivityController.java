package com.example.designapptest.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.designapptest.Adapters.AdapterRecyclerMainRoom;
import com.example.designapptest.Controller.Interfaces.ILocationModel;
import com.example.designapptest.Controller.Interfaces.IMainRoomModel;
import com.example.designapptest.Model.LocationModel;
import com.example.designapptest.Model.RoomModel;
import com.example.designapptest.R;
import com.example.designapptest.Views.locationAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivityController {
    Context context;
    RoomModel roomModel;
    LocationModel locationModel;
    SharedPreferences sharedPreferences;
    static List<RoomModel> favoriteRoomsList = new ArrayList<>();
    static AdapterRecyclerMainRoom myAdapterRecyclerFavoriteRoom = null;

    public MainActivityController(Context context, SharedPreferences sharedPreferences) {
        this.context = context;
        this.roomModel = new RoomModel();
        this.locationModel = new LocationModel();
        this.sharedPreferences = sharedPreferences;
    }

    public void ListMainRoom(RecyclerView recyclerMainRoom, RecyclerView recyclerViewGridMainRoom, final ProgressBar progressBarMain) {
        final List<RoomModel> roomModelList = new ArrayList<>();

        //Tạo layout cho danh sách trọ tìm kiếm nhiều nhất
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerMainRoom.setLayoutManager(layoutManager);

        //Tạo adapter cho recycle view
        final AdapterRecyclerMainRoom adapterRecyclerMainRoom = new AdapterRecyclerMainRoom(context, roomModelList, R.layout.room_element_list_view, sharedPreferences);
        //Cài adapter cho recycle
        recyclerMainRoom.setAdapter(adapterRecyclerMainRoom);
        //End tạo layout cho danh sách trọ tìm kiếm nhiều nhất

        //Tạo layout cho danh sách trọ được hiển thị theo dạng grid phía dưới
        RecyclerView.LayoutManager layoutManagerGrid = new GridLayoutManager(context, 2);
        recyclerViewGridMainRoom.setLayoutManager(layoutManagerGrid);

        //Tạo adapter cho recycle view
        final AdapterRecyclerMainRoom adapterRecyclerGridMainRoom = new AdapterRecyclerMainRoom(context, roomModelList, R.layout.room_element_grid_view, sharedPreferences);
        //Cài adapter cho recycle
        recyclerViewGridMainRoom.setAdapter(adapterRecyclerGridMainRoom);
        //End Tạo layout cho danh sách trọ được hiển thị theo dạng grid phía dưới

        //Tạo interface để truyền dữ liệu lên từ model
        IMainRoomModel iMainRoomModel = new IMainRoomModel() {
            @Override
            public void getListMainRoom(RoomModel valueRoom) {
                //Thêm vào trong danh sách trọ
                roomModelList.add(valueRoom);

                for (String favoriteRoomId : RoomModel.myFavoriteRooms) {
                    if (favoriteRoomId.equals(valueRoom.getRoomID())) {
                        favoriteRoomsList.add(valueRoom);
                        break;
                    }
                }

                //Thông báo là đã có thêm dữ liệu
                adapterRecyclerMainRoom.notifyDataSetChanged();
                adapterRecyclerGridMainRoom.notifyDataSetChanged();
                progressBarMain.setVisibility(View.GONE);

                if (myAdapterRecyclerFavoriteRoom != null) {
                    myAdapterRecyclerFavoriteRoom.notifyDataSetChanged();
                }
            }

            @Override
            public void refreshListFavoriteRoom() {
                favoriteRoomsList.clear();
            }
        };

        //Gọi hàm lấy dữ liệu trong model
        roomModel.ListRoom(iMainRoomModel);
    }

    public void ListRoomUser(RecyclerView recyclerMainRoom,String UID){
        final List<RoomModel> roomModelList = new ArrayList<>();

        //Tạo layout cho danh sách trọ tìm kiếm nhiều nhất
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerMainRoom.setLayoutManager(layoutManager);

        //Tạo adapter cho recycle view
        final AdapterRecyclerMainRoom adapterRecyclerMainRoom = new AdapterRecyclerMainRoom(context, roomModelList, R.layout.room_element_list_view);
        //Cài adapter cho recycle
        recyclerMainRoom.setAdapter(adapterRecyclerMainRoom);
        //End tạo layout cho danh sách trọ tìm kiếm nhiều nhất

        IMainRoomModel iMainRoomModel = new IMainRoomModel() {
            @Override
            public void getListMainRoom(RoomModel valueRoom) {
                //Thêm vào trong danh sách trọ
                roomModelList.add(valueRoom);

                //Thông báo là đã có thêm dữ liệu
                adapterRecyclerMainRoom.notifyDataSetChanged();
            }
        };

        //Gọi hàm lấy dữ liệu trong model
        roomModel.ListRoomUser(iMainRoomModel,UID);
    }

    
    public void loadTopLocation(GridView grVLocation) {


        //Tạo mới mảng cho gridview
        List<LocationModel> datalocation = new ArrayList<LocationModel>();

        //Tạo adapter cho gridview
        locationAdapter adapter = new locationAdapter(context, R.layout.row_element_grid_view_location, datalocation);
        grVLocation.setAdapter(adapter);

        //Tạo mới interface đề truyền dữ liệu lên từ model
        ILocationModel iLocationModel = new ILocationModel() {
            @Override
            public void getListTopRoom(List<LocationModel> topLocation) {
                datalocation.addAll(topLocation);
                adapter.notifyDataSetChanged();
            }
        };

        //Gọi hàm lấy dữ liệu và truyền vào interface
        locationModel.topLocation(iLocationModel);
    }

    public void getListFavoriteRooms(RecyclerView recyclerFavoriteRoom) {
//        final List<RoomModel> roomModelList = new ArrayList<>();

        //Tạo layout cho danh sách trọ yêu thích
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerFavoriteRoom.setLayoutManager(layoutManager);

        //Tạo adapter cho recycle view
        final AdapterRecyclerMainRoom adapterRecyclerFavoriteRoom = new AdapterRecyclerMainRoom(context, favoriteRoomsList, R.layout.room_element_list_view, sharedPreferences);
        //Cài adapter cho recycle
        recyclerFavoriteRoom.setAdapter(adapterRecyclerFavoriteRoom);
        //End tạo layout cho danh sách trọ yêu thích

        myAdapterRecyclerFavoriteRoom = adapterRecyclerFavoriteRoom;

        //
        ColorDrawable swipeBackground = new ColorDrawable(Color.parseColor("#C03A2B"));
        Drawable deleteIcon = ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_garbage, null);

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                adapterRecyclerFavoriteRoom.removeItem(viewHolder, recyclerFavoriteRoom, adapterRecyclerFavoriteRoom);
                //adapterRecyclerFavoriteRoom.notifyDataSetChanged();
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                View itemView = viewHolder.itemView;
                int iconMarginTopBottom = (itemView.getHeight() - deleteIcon.getIntrinsicHeight()) / 2;

                if (dX > 0) {
                    swipeBackground.setBounds(itemView.getLeft(), itemView.getTop(), ((int) dX), itemView.getBottom());
                    deleteIcon.setBounds(
                            itemView.getLeft() + deleteIcon.getIntrinsicWidth(),
                            itemView.getTop() + iconMarginTopBottom,
                            itemView.getLeft() + 2 * deleteIcon.getIntrinsicWidth(),
                            itemView.getBottom() - iconMarginTopBottom);
                } else {
                    swipeBackground.setBounds(itemView.getRight() + ((int) dX), itemView.getTop(), itemView.getRight(),
                            itemView.getBottom());
                    deleteIcon.setBounds(
                            itemView.getRight() - 2 * deleteIcon.getIntrinsicWidth(),
                            itemView.getTop() + iconMarginTopBottom,
                            itemView.getRight() - deleteIcon.getIntrinsicWidth(),
                            itemView.getBottom() - iconMarginTopBottom);
                }

                swipeBackground.draw(c);

                c.save();

                if (dX > 0) {
                    c.clipRect(itemView.getLeft(), itemView.getTop(), ((int) dX), itemView.getBottom());
                } else {
                    c.clipRect(itemView.getRight() + ((int) dX), itemView.getTop(), itemView.getRight(), itemView.getBottom());
                }
                deleteIcon.draw(c);

                c.restore();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerFavoriteRoom);
        //

        //Tạo interface để truyền dữ liệu lên từ model
//        IMainRoomModel iMainRoomModel = new IMainRoomModel() {
//            @Override
//            public void getListMainRoom(RoomModel valueRoom) {
//                //Thêm vào trong danh sách trọ
//                roomModelList.add(valueRoom);
//
//                //Thông báo là đã có thêm dữ liệu
//                adapterRecyclerFavoriteRoom.notifyDataSetChanged();
//            }
//
//            @Override
//            public void refreshListFavoriteRoom() {
//                roomModelList.clear();
//            }
//        };

        //Gọi hàm lấy dữ liệu trong model
//        roomModel.getListFavoriteRooms(iMainRoomModel, sharedPreferences);
    }

//    public void addToFavoriteRooms(String roomId, Context context, SharedPreferences sharedPreferences, ImageView imageView) {
//        roomModel.addToFavoriteRooms(roomId, context, sharedPreferences, imageView);
//    }

    public void addToFavoriteRooms(String roomId, Context context, SharedPreferences sharedPreferences, MenuItem item) {
        roomModel.addToFavoriteRooms(roomId, context, sharedPreferences, item);
    }

//    public void removeFromFavoriteRooms(String roomId, Context context, SharedPreferences sharedPreferences, ImageView imageView) {
//        roomModel.removeFromFavoriteRooms(roomId, context, sharedPreferences, imageView);
//    }

    public void removeFromFavoriteRooms(String roomId, Context context, SharedPreferences sharedPreferences, MenuItem item) {
        roomModel.removeFromFavoriteRooms(roomId, context, sharedPreferences, item);
    }
}
