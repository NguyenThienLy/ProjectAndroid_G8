package com.example.designapptest.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.designapptest.Adapters.AdapterRecyclerMainRoom;
import com.example.designapptest.ClassOther.myFilter;
import com.example.designapptest.Controller.Interfaces.ISearchRoomModel;
import com.example.designapptest.Model.RoomModel;
import com.example.designapptest.Model.SearchRoomModel;
import com.example.designapptest.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class searchViewController {

    Context context;
    SearchRoomModel searchRoomModel;
    String UID;

    // khai báo các biến liên quan tới load more.
    int quantityRoomsLoaded = 0;
    int quantityRoomsEachTime = 4;

    public searchViewController(Context context, String district, List<myFilter> filterList, String UID) {
        this.context = context;
        this.searchRoomModel = new SearchRoomModel(district,filterList);
        this.UID = UID;
    }

    public void loadSearchRoom(RecyclerView recyclerSearchRoom, TextView txtNumberRoom, ProgressBar progressBarLoad,
                               LinearLayout lnLtResultReturn, NestedScrollView nestedScrollSearchView,
                               ProgressBar progressBarLoadMore){
        final List<RoomModel> roomModelList = new ArrayList<>();

        //Tạo layout cho danh sách trọ
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerSearchRoom.setLayoutManager(layoutManager);

        //Tạo adapter cho recycle view
        final AdapterRecyclerMainRoom adapterRecyclerMainRoom = new AdapterRecyclerMainRoom(context, roomModelList, R.layout.room_element_list_view, UID);
        //Cài adapter cho recycle
        recyclerSearchRoom.setAdapter(adapterRecyclerMainRoom);
        ViewCompat.setNestedScrollingEnabled(recyclerSearchRoom, false);
        //End tạo layout cho danh sách trọ

        ISearchRoomModel searchRoomModelInterface = new ISearchRoomModel() {
            //int i =0;
            @Override
            public void sendDataRoom(RoomModel roomModel,boolean ishadFound) {

                if(ishadFound){
                    // Load ảnh nén
                    roomModel.setCompressionImageFit(Picasso.get().load(roomModel.getCompressionImage()).fit());

                    //Add thêm vào recycler view
                    roomModelList.add(roomModel);

                    //Thông báo thay đổi dữ liệu
                    adapterRecyclerMainRoom.notifyDataSetChanged();
//                    lnLtResultReturn.setVisibility(View.VISIBLE);
//                    //Tăng kết quả lên mỗi lần có room mới được tải về
//                    i++;
//                    //Set text hiển thị
//                    txtNumberRoom.setText(i + "");
                }
                else {
//                    lnLtResultReturn.setVisibility(View.VISIBLE);
//                    //Set text hiển thị
//                    txtNumberRoom.setText(i + "");
                }

            }

            @Override
            public void setProgressBarLoadMore() {
                //Ẩn Progessbar và hiện text
                progressBarLoad.setVisibility(View.GONE);
                progressBarLoadMore.setVisibility(View.GONE);
            }

            @Override
            public void setQuantityTop(int quantity) {
                lnLtResultReturn.setVisibility(View.VISIBLE);
                txtNumberRoom.setText(quantity + "");
            }
        };

        nestedScrollSearchView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
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
                                progressBarLoadMore.setVisibility(View.VISIBLE);

                                quantityRoomsLoaded += quantityRoomsEachTime;
                                searchRoomModel.searchRoom(searchRoomModelInterface, "",
                                        quantityRoomsLoaded + quantityRoomsEachTime, quantityRoomsLoaded);
                            }
                        }
                    }
                }
            }
        });

        //Thêm sự kiện listenner cho noderoot
        searchRoomModel.searchRoom(searchRoomModelInterface, "",
                quantityRoomsLoaded + quantityRoomsEachTime, quantityRoomsLoaded);
    }
}
