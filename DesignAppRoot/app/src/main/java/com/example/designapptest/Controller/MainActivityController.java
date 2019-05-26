package com.example.designapptest.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.designapptest.Adapters.AdapterRecyclerMainRoom;
import com.example.designapptest.Controller.Interfaces.ILocationModel;
import com.example.designapptest.Controller.Interfaces.IMainRoomModel;
import com.example.designapptest.Model.LocationModel;
import com.example.designapptest.Model.RoomModel;
import com.example.designapptest.R;
import com.example.designapptest.Views.locationAdapter;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MainActivityController {
    Context context;
    RoomModel roomModel;
    LocationModel locationModel;
    String UID;

    // khai báo các biến liên quan tới load more.
    int quantityGridMainRoomLoaded = 0;
    int quantityGridMainRoomEachTime = 4;
    int quantityVerifiedRoomLoaded = 0;
    int quantityVerifiedRoomEachTime = 4;

    public MainActivityController(Context context, String UID) {
        this.context = context;
        this.roomModel = new RoomModel();
        this.locationModel = new LocationModel();
        this.UID = UID;
    }

    public void ListMainRoom(RecyclerView recyclerMainRoom, RecyclerView recyclerViewGridMainRoom, final ProgressBar progressBarMain,
                             NestedScrollView nestedScrollMainView, Button btnLoadMoreVerifiedRooms,
                             ProgressBar progressBarLoadMoreGridMainRoom) {
        final List<RoomModel> roomModelList = new ArrayList<>();
        final List<RoomModel> roomModelListAuthentication = new ArrayList<>();

        //Tạo layout cho danh sách trọ tìm kiếm nhiều nhất
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerMainRoom.setLayoutManager(layoutManager);

        //Tạo adapter cho recycle view
        final AdapterRecyclerMainRoom adapterRecyclerMainRoom = new AdapterRecyclerMainRoom(context, roomModelListAuthentication, R.layout.room_element_list_view, UID);
        //Cài adapter cho recycle
        recyclerMainRoom.setAdapter(adapterRecyclerMainRoom);
        //End tạo layout cho danh sách trọ tìm kiếm nhiều nhất

        //Tạo layout cho danh sách trọ được hiển thị theo dạng grid phía dưới
        RecyclerView.LayoutManager layoutManagerGrid = new GridLayoutManager(context, 2);
        recyclerViewGridMainRoom.setLayoutManager(layoutManagerGrid);

        //Tạo adapter cho recycle view
        final AdapterRecyclerMainRoom adapterRecyclerGridMainRoom = new AdapterRecyclerMainRoom(context, roomModelList, R.layout.room_element_grid_view, UID);
        //Cài adapter cho recycle
        recyclerViewGridMainRoom.setAdapter(adapterRecyclerGridMainRoom);
        //End Tạo layout cho danh sách trọ được hiển thị theo dạng grid phía dưới

        //Tạo interface để truyền dữ liệu lên từ model
        IMainRoomModel iMainRoomModel = new IMainRoomModel() {
            @Override
            public void getListMainRoom(RoomModel valueRoom) {
                // Load ảnh nén
                valueRoom.setCompressionImageFit(Picasso.get().load(valueRoom.getCompressionImage()).fit());

                //Thêm vào trong danh sách trọ
                roomModelList.add(valueRoom);

                adapterRecyclerGridMainRoom.notifyDataSetChanged();
                progressBarMain.setVisibility(View.GONE);
            }

            @Override
            public void makeToast(String message) {

            }

            @Override
            public void setIconFavorite(int iconRes) {

            }

            @Override
            public void setButtonLoadMoreVerifiedRooms() {

            }

            @Override
            public void setProgressBarLoadMore() {
                // Ẩn progress bar load more
                progressBarLoadMoreGridMainRoom.setVisibility(View.GONE);
            }

            @Override
            public void setQuantityTop(int quantity) {

            }
        };

        IMainRoomModel iMainRoomModelAuthentication = new IMainRoomModel() {
            @Override
            public void getListMainRoom(RoomModel valueRoom) {
                // Load ảnh nén
                valueRoom.setCompressionImageFit(Picasso.get().load(valueRoom.getCompressionImage()).fit());

                //Thêm vào trong danh sách trọ
                roomModelListAuthentication.add(valueRoom);

                //Thông báo là đã có thêm dữ liệu
                adapterRecyclerMainRoom.notifyDataSetChanged();
            }

            @Override
            public void makeToast(String message) {

            }

            @Override
            public void setIconFavorite(int iconRes) {

            }

            @Override
            public void setButtonLoadMoreVerifiedRooms() {
                // Hiển thị nút Xem thêm phòng đã xác nhận
                btnLoadMoreVerifiedRooms.setVisibility(View.VISIBLE);
            }

            @Override
            public void setProgressBarLoadMore() {

            }

            @Override
            public void setQuantityTop(int quantity) {

            }

        };

        // Gọi hàm lấy dữ liệu khi scroll xuống đáy.
        nestedScrollMainView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int i, int i1, int i2, int i3) {
                // check xem có scroll đc ko
                View child = nestedScrollView.getChildAt(0);
                if (child != null) {
                    int childHeight = child.getHeight();
                    // Nếu scroll đc
                    if (nestedScrollView.getHeight() < childHeight + nestedScrollView.getPaddingTop() + nestedScrollView.getPaddingBottom()) {
                        View lastItemView = nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1);

                        if (lastItemView != null) {
                            if (i1 >= lastItemView.getMeasuredHeight() - nestedScrollView.getMeasuredHeight()) {
                                // Hiển thị progress bar
                                progressBarLoadMoreGridMainRoom.setVisibility(View.VISIBLE);

                                quantityGridMainRoomLoaded += quantityGridMainRoomEachTime;
                                roomModel.ListRoom(iMainRoomModel, quantityGridMainRoomLoaded,
                                        quantityGridMainRoomLoaded - quantityGridMainRoomEachTime);
                            }
                        }
                    }
                }
            }
        });

        //Gọi hàm lấy dữ liệu trong model.
        roomModel.ListRoom(iMainRoomModel, quantityGridMainRoomLoaded + quantityGridMainRoomEachTime,
                quantityGridMainRoomLoaded);
        quantityGridMainRoomLoaded += quantityGridMainRoomEachTime;

        roomModel.getListAuthenticationRoomsAtMainView(iMainRoomModelAuthentication, 5);
    }

    // Hàm kiểm tra danh sách tiện nghi
    private boolean checkTwoConvenients(List<String> lst1, List<String> lst2) {
        if (lst1 == null || lst2 == null)
            return false;

        // Biến đếm số lượng tiện nghi trùng lặp.
        int count = 0;
        int i, j;

        for (i = 0; i < lst1.size(); i++) {
            for (j = 0; j < lst2.size(); j++) {
                if (lst1.get(i).equals(lst2.get(j))) {
                    count++;
                }
            }
        }

        // Số lượng tiện nghi phải lớn hơn ba.
        if (count >= 3)
            return true;

        return false;
    }


    public void ListRoomUser(RecyclerView recyclerMainRoom, TextView txtQuantity, ProgressBar progressBarMyRooms,
                             LinearLayout lnLtQuantityTopMyRooms, NestedScrollView nestedScrollMyRoomsView,
                             ProgressBar progressBarLoadMoreMyRooms) {
        final List<RoomModel> roomModelList = new ArrayList<>();

        //Tạo layout cho danh sách trọ tìm kiếm nhiều nhất
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerMainRoom.setLayoutManager(layoutManager);

        //Tạo adapter cho recycle view
        final AdapterRecyclerMainRoom adapterRecyclerMainRoom = new AdapterRecyclerMainRoom(context, roomModelList, R.layout.room_element_list_view, UID);
        //Cài adapter cho recycle
        recyclerMainRoom.setAdapter(adapterRecyclerMainRoom);
        //End tạo layout cho danh sách trọ tìm kiếm nhiều nhất

        IMainRoomModel iMainRoomModel = new IMainRoomModel() {
            @Override
            public void getListMainRoom(RoomModel valueRoom) {
                // Load ảnh nén
                valueRoom.setCompressionImageFit(Picasso.get().load(valueRoom.getCompressionImage()).fit());

                //Thêm vào trong danh sách trọ
                roomModelList.add(valueRoom);

                //Thông báo là đã có thêm dữ liệu
                adapterRecyclerMainRoom.notifyDataSetChanged();

                progressBarMyRooms.setVisibility(View.GONE);
            }

            @Override
            public void makeToast(String message) {

            }

            @Override
            public void setIconFavorite(int iconRes) {

            }

            @Override
            public void setButtonLoadMoreVerifiedRooms() {

            }

            @Override
            public void setProgressBarLoadMore() {
                progressBarLoadMoreMyRooms.setVisibility(View.GONE);
            }

            @Override
            public void setQuantityTop(int quantity) {
                lnLtQuantityTopMyRooms.setVisibility(View.VISIBLE);
                // Hiển thị kết quả trả về
                txtQuantity.setText(quantity + "");
            }
        };

        nestedScrollMyRoomsView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int i, int i1, int i2, int i3) {
                // check xem có scroll đc ko
                View child = nestedScrollView.getChildAt(0);
                if (child != null) {
                    int childHeight = child.getHeight();
                    // Nếu scroll đc
                    if (nestedScrollView.getHeight() < childHeight + nestedScrollView.getPaddingTop() + nestedScrollView.getPaddingBottom()) {
                        View lastItemView = nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1);

                        if (lastItemView != null) {
                            if (i1 >= lastItemView.getMeasuredHeight() - nestedScrollView.getMeasuredHeight()) {
                                // Hiển thị progress bar
                                progressBarLoadMoreMyRooms.setVisibility(View.VISIBLE);

                                quantityVerifiedRoomLoaded += quantityVerifiedRoomEachTime;
                                roomModel.ListRoomUser(iMainRoomModel, UID,
                                        quantityVerifiedRoomLoaded + quantityVerifiedRoomEachTime,
                                        quantityVerifiedRoomLoaded);
                            }
                        }
                    }
                }
            }
        });

        //Gọi hàm lấy dữ liệu trong model
        roomModel.ListRoomUser(iMainRoomModel, UID,
                quantityVerifiedRoomLoaded + quantityVerifiedRoomEachTime,
                quantityVerifiedRoomLoaded);
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

    public void getListVerifiedRooms(RecyclerView recyclerVerifiedRoom, TextView txtQuantity, ProgressBar progressBarVerifiedRooms,
                                     LinearLayout lnLtQuantityTopVerifiedRooms, NestedScrollView nestedScrollVerifiedRoomsView,
                                     ProgressBar progressBarLoadMoreVerifiedRooms) {
        final List<RoomModel> verifiedRoomsList = new ArrayList<>();

        //Tạo layout cho danh sách trọ đã xác nhận
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerVerifiedRoom.setLayoutManager(layoutManager);

        //Tạo adapter cho recycle view
        final AdapterRecyclerMainRoom adapterRecyclerVerifiedRoom = new AdapterRecyclerMainRoom(context, verifiedRoomsList, R.layout.room_element_list_view, UID);
        //Cài adapter cho recycle
        recyclerVerifiedRoom.setAdapter(adapterRecyclerVerifiedRoom);
        //End tạo layout cho danh sách trọ đã xác nhận

        //Tạo interface để truyền dữ liệu lên từ model
        IMainRoomModel iMainRoomModel = new IMainRoomModel() {
            @Override
            public void getListMainRoom(RoomModel valueRoom) {
                // Load ảnh nén
                valueRoom.setCompressionImageFit(Picasso.get().load(valueRoom.getCompressionImage()).fit());

                //Thêm vào trong danh sách trọ
                verifiedRoomsList.add(valueRoom);

                //Thông báo là đã có thêm dữ liệu
                adapterRecyclerVerifiedRoom.notifyDataSetChanged();

                progressBarVerifiedRooms.setVisibility(View.GONE);
            }

            @Override
            public void makeToast(String message) {

            }

            @Override
            public void setIconFavorite(int iconRes) {

            }

            @Override
            public void setButtonLoadMoreVerifiedRooms() {

            }

            @Override
            public void setProgressBarLoadMore() {
                // Ẩn progress bar load more
                progressBarLoadMoreVerifiedRooms.setVisibility(View.GONE);
            }

            @Override
            public void setQuantityTop(int quantity) {
                lnLtQuantityTopVerifiedRooms.setVisibility(View.VISIBLE);
                // Hiển thị kết quả trả về
                txtQuantity.setText(quantity + "");
            }
        };

        nestedScrollVerifiedRoomsView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int i, int i1, int i2, int i3) {
                // check xem có scroll đc ko
                View child = nestedScrollView.getChildAt(0);
                if (child != null) {
                    int childHeight = child.getHeight();
                    // Nếu scroll đc
                    if (nestedScrollView.getHeight() < childHeight + nestedScrollView.getPaddingTop() + nestedScrollView.getPaddingBottom()) {
                        View lastItemView = nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1);

                        if (lastItemView != null) {
                            if (i1 >= lastItemView.getMeasuredHeight() - nestedScrollView.getMeasuredHeight()) {
                                // Hiển thị progress bar
                                progressBarLoadMoreVerifiedRooms.setVisibility(View.VISIBLE);

                                quantityVerifiedRoomLoaded += quantityVerifiedRoomEachTime;
                                roomModel.getListAuthenticationRoomsAtVerifiedRoomsView(iMainRoomModel,
                                        quantityVerifiedRoomLoaded + quantityVerifiedRoomEachTime, quantityVerifiedRoomLoaded);
                            }
                        }
                    }
                }
            }
        });

        //Gọi hàm lấy dữ liệu trong model
        roomModel.getListAuthenticationRoomsAtVerifiedRoomsView(iMainRoomModel,
                quantityVerifiedRoomLoaded + quantityVerifiedRoomEachTime, quantityVerifiedRoomLoaded);
    }

    public void getListFavoriteRooms(RecyclerView recyclerFavoriteRoom, TextView txtQuantity, ProgressBar progressBarFavoriteRooms,
                                     LinearLayout lnLtQuantityTopFavoriteRooms, NestedScrollView nestedScrollFavoriteRoomsView,
                                     ProgressBar progressBarLoadMoreFavoriteRooms) {
        final List<RoomModel> favoriteRoomsList = new ArrayList<>();

        //Tạo layout cho danh sách trọ yêu thích
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerFavoriteRoom.setLayoutManager(layoutManager);

        //Tạo adapter cho recycle view
        final AdapterRecyclerMainRoom adapterRecyclerFavoriteRoom = new AdapterRecyclerMainRoom(context, favoriteRoomsList,
                R.layout.room_element_list_view, UID);
        //Cài adapter cho recycle
        recyclerFavoriteRoom.setAdapter(adapterRecyclerFavoriteRoom);
        //End tạo layout cho danh sách trọ yêu thích

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
        IMainRoomModel iMainRoomModel = new IMainRoomModel() {
            @Override
            public void getListMainRoom(RoomModel valueRoom) {
                // Load ảnh nén
                valueRoom.setCompressionImageFit(Picasso.get().load(valueRoom.getCompressionImage()).fit());

                //Thêm vào trong danh sách trọ
                favoriteRoomsList.add(valueRoom);

                //Thông báo là đã có thêm dữ liệu
                adapterRecyclerFavoriteRoom.notifyDataSetChanged();

                progressBarFavoriteRooms.setVisibility(View.GONE);
            }

            @Override
            public void makeToast(String message) {

            }

            @Override
            public void setIconFavorite(int iconRes) {

            }

            @Override
            public void setButtonLoadMoreVerifiedRooms() {

            }

            @Override
            public void setProgressBarLoadMore() {
                // Ẩn progress bar load more.
                progressBarLoadMoreFavoriteRooms.setVisibility(View.GONE);
            }

            @Override
            public void setQuantityTop(int quantity) {
                lnLtQuantityTopFavoriteRooms.setVisibility(View.VISIBLE);
                // Hiển thị kết quả trả về
                txtQuantity.setText(quantity + "");
            }
        };

        nestedScrollFavoriteRoomsView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int i, int i1, int i2, int i3) {
                // check xem có scroll đc ko
                View child = nestedScrollView.getChildAt(0);
                if (child != null) {
                    int childHeight = child.getHeight();
                    // Nếu scroll đc
                    if (nestedScrollView.getHeight() < childHeight + nestedScrollView.getPaddingTop() + nestedScrollView.getPaddingBottom()) {
                        View lastItemView = nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1);
                        if (lastItemView != null) {
                            if (i1 >= lastItemView.getMeasuredHeight() - nestedScrollView.getMeasuredHeight()) {
                                // Hiển thị progress bar
                                progressBarLoadMoreFavoriteRooms.setVisibility(View.VISIBLE);

                                quantityVerifiedRoomLoaded += quantityVerifiedRoomEachTime;
                                roomModel.getListFavoriteRooms(iMainRoomModel, UID,
                                        quantityVerifiedRoomLoaded + quantityVerifiedRoomEachTime, quantityVerifiedRoomLoaded);
                            }
                        }
                    }
                }
            }
        });

        //Gọi hàm lấy dữ liệu trong model
        roomModel.getListFavoriteRooms(iMainRoomModel, UID,
                quantityVerifiedRoomLoaded + quantityVerifiedRoomEachTime, quantityVerifiedRoomLoaded);
    }

    public void addToFavoriteRooms(String roomId, Context context, MenuItem item) {
        IMainRoomModel iMainRoomModel = new IMainRoomModel() {
            @Override
            public void getListMainRoom(RoomModel valueRoom) {

            }

            @Override
            public void makeToast(String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void setIconFavorite(int iconRes) {
                item.setIcon(iconRes);
            }

            @Override
            public void setButtonLoadMoreVerifiedRooms() {

            }

            @Override
            public void setProgressBarLoadMore() {

            }

            @Override
            public void setQuantityTop(int quantity) {

            }
        };

        roomModel.addToFavoriteRooms(roomId, iMainRoomModel, UID);
    }

    public void removeFromFavoriteRooms(String roomId, Context context, MenuItem item) {
        IMainRoomModel iMainRoomModel = new IMainRoomModel() {
            @Override
            public void getListMainRoom(RoomModel valueRoom) {

            }

            @Override
            public void makeToast(String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void setIconFavorite(int iconRes) {
                item.setIcon(iconRes);
            }

            @Override
            public void setButtonLoadMoreVerifiedRooms() {

            }

            @Override
            public void setProgressBarLoadMore() {

            }

            @Override
            public void setQuantityTop(int quantity) {

            }
        };

        roomModel.removeFromFavoriteRooms(roomId, iMainRoomModel, UID);
    }

//    private void sortFavoriteRoomsList(List<RoomModel> favoriteRoomsList) {
//        List<RoomModel> myTempList = new ArrayList<>(favoriteRoomsList);
//
//        favoriteRoomsList.clear();
//
//        for (int i = RoomModel.ListFavoriteRoomsID.size() - 1; i >= 0; i--) {
//            for (RoomModel valueRoom : myTempList) {
//                if (valueRoom.getRoomID().equals(RoomModel.ListFavoriteRoomsID.get(i))) {
//                    favoriteRoomsList.add(valueRoom);
//                    break;
//                }
//            }
//        }
//    }
}
