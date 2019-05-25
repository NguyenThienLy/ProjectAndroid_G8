package com.example.designapptest.Views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.designapptest.Controller.FindRoomController;
import com.example.designapptest.Model.FindRoomFilterModel;
import com.example.designapptest.R;

public class FindRoom extends Fragment {
    // ID object truyen qua find room
    public final static String SHARE_FINDROOM = "FINDROOMMAIN";
    private static final int REQUEST_CODE_FILTER = 0x9345;

    ImageButton btnFindRoomAdd, btnFindRoomFilter;

    RecyclerView recyclerFindRoom;

    FindRoomController findRoomController;

    FindRoomFilterModel findRoomFilterModel;

    ProgressBar progressBarFindRoom;

    // Số lượng kết quả trả về.
    TextView txtResultReturn;

    //
    LinearLayout lLayHaveResultReturn;

    Toolbar toolbar;
    MenuItem menuItemFilter;

    // Biến báo load lại find room.
    boolean flagFindRoom = true;

    //Layout
    View layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.find_room_view);
        //Hỗ trợ việc có menu cho fragment
        setHasOptionsMenu(true);

        findRoomController = new FindRoomController(getContext());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.find_room_view, container, false);

        initControl();
        clickFindRoomAdd();

        return layout;
    }

    //Load dữ liệu vào List danh sách trong lần đầu chạy
    @Override
    public void onStart() {
        super.onStart();

        initData();

        if (flagFindRoom == true) {
            findRoomController.ListFindRoom(recyclerFindRoom, progressBarFindRoom, txtResultReturn, lLayHaveResultReturn);
        }
    }
    //End load dữ liệu vào danh sách trong lần đầu chạy

//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.find_room_menu, menu);

        menuItemFilter = menu.findItem(R.id.menu_item_filter);

        // Set trọ yêu thích ?
        menuItemFilter.setIcon(R.drawable.ic_svg_filter_white_100);

        menuItemFilter.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                clickFindRoomFilter();

                return false;
            }
        });

    }

    private void initControl() {
        toolbar = layout.findViewById(R.id.toolbar);

        progressBarFindRoom = (ProgressBar) layout.findViewById(R.id.progress_find_room);
        progressBarFindRoom.getIndeterminateDrawable().setColorFilter(Color.parseColor("#00DDFF"),
                android.graphics.PorterDuff.Mode.MULTIPLY);

        recyclerFindRoom = (RecyclerView) layout.findViewById(R.id.recycler_find_Room);
        btnFindRoomAdd = (ImageButton) layout.findViewById(R.id.btn_findRooomAdd);
        txtResultReturn = (TextView) layout.findViewById(R.id.txt_resultReturn_find_room);
        lLayHaveResultReturn = (LinearLayout) layout.findViewById(R.id.lLay_haveResultReturn_find_room);
    }

    private void initData() {
        // Thiết lập toolbar
        if (toolbar != null) {
            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Tìm ở ghép");
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    // Hiển thị màn hình thêm mới tìm ở ghép
    private void clickFindRoomAdd() {
        btnFindRoomAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Phải load lại find room.
                flagFindRoom = true;

                Intent iFindRoomAdd = new Intent(getContext(), FindRoomAdd.class);
                startActivity(iFindRoomAdd);
            }
        });
    }

    // Hiển thị màn hình bộ lọc tìm phòng ở ghép
    private void clickFindRoomFilter() {
        //Bắt đầu activity find room filter
        Intent iFindRoomFilter = new Intent(getContext(), FindRoomFilter.class);
        startActivityForResult(iFindRoomFilter, REQUEST_CODE_FILTER);
    }

    // Khi kết quả được trả về từ Activity find room filter, hàm onActivityResult sẽ được gọi.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Kiểm tra requestCode có trùng với REQUEST_CODE vừa dùng
        if (requestCode == REQUEST_CODE_FILTER) {

            // resultCode được set bởi DetailActivity
            // RESULT_OK chỉ ra rằng kết quả này đã thành công
            if (resultCode == AppCompatActivity.RESULT_OK) {
                flagFindRoom = false;
                // Nhận dữ liệu từ Intent bên find room filer
                findRoomFilterModel = data.getParcelableExtra(FindRoom.SHARE_FINDROOM);

                // findRoomController = new FindRoomController(this);
                findRoomController.ListFindRoomFilter(recyclerFindRoom, findRoomFilterModel, progressBarFindRoom, txtResultReturn, lLayHaveResultReturn);

            } else {
                // DetailActivity không thành công, không có data trả về.
            }
        }

    }
}
