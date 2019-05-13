package com.example.designapptest.Views;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.designapptest.Adapters.AdapterRecyclerConvenient;
import com.example.designapptest.Adapters.AdapterRecyclerFindRoom;
import com.example.designapptest.ClassOther.classFunctionStatic;
import com.example.designapptest.Model.FindRoomModel;
import com.example.designapptest.R;
import com.squareup.picasso.Picasso;

public class FindRoomDetail extends AppCompatActivity {
    TextView txtNameUser, txtAboutPrice, txtWantGender;

    Button btnCallPhone;

    ImageView imgGenderUser, imgAvatarUser;
    GridView grVLocationSearch;

    // Các recycler.
    RecyclerView recycler_convenients_room_detail;
    AdapterRecyclerConvenient adapterRecyclerConvenient;

    ProgressBar progressBarFindRoomDetail;

    FindRoomModel findRoomModel;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_room_detail_view);

        getShareInfo();

        initControl();

       // loadProgress();
    }

    @Override
    protected void onStart() {
        super.onStart();

        initData();
        clickCallPhone();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private void loadProgress() {
        classFunctionStatic.showProgress(this, imgAvatarUser);
    }

    private void getShareInfo() {
        findRoomModel = getIntent().getParcelableExtra(AdapterRecyclerFindRoom.SHARE_FINDROOM);
    }

    // Khởi tạo các control trong room detail.
    private void initControl() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.parseColor("#00DDFF"));

        txtNameUser = (TextView) findViewById(R.id.txt_name_user_find_room_detail);
        txtAboutPrice = (TextView) findViewById(R.id.txt_abouPrice_find_room_detail);
        grVLocationSearch = (GridView) findViewById(R.id.grV_locationSearch_find_room_detail);
        txtWantGender = (TextView) findViewById(R.id.txt_wantGender_find_room_detail);

        btnCallPhone = (Button) findViewById(R.id.btn_callPhone_find_room_detail);

        imgGenderUser = (ImageView) findViewById(R.id.img_gender_user_find_room_detail);
        imgAvatarUser = (ImageView) findViewById(R.id.img_avatar_user_find_room_detail);

        //progressBarFindRoomDetail = (ProgressBar) findViewById(R.id.progress_find_room_detail);

        recycler_convenients_room_detail = (RecyclerView) findViewById(R.id.recycler_convenients_find_room_detail);
    }

    // Khởi tạo các giá trị cho các control.
    private void initData() {
        // Thiết lập toolbar
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Chi tiết tìm ở ghép");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //Gán các giá trị vào giao diện
        // Gán tên cho người dùng
        txtNameUser.setText(findRoomModel.getFindRoomOwner().getName());

        // Gán giá trị cho khoảng giá cần tìm kiếm.
        txtAboutPrice.setText(String.valueOf( findRoomModel.getMinPrice())
                + " - " + String.valueOf( findRoomModel.getMaxPrice()));

        // Chỉ xử lý khi khác null
        if (findRoomModel.getLocation() != null) {
            // gán giá trị cho vị trí tìm kiếm.
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.grid_element_find_room_detail, findRoomModel.getLocation());
            grVLocationSearch.setAdapter(adapter);
            // End gán giá trị cho vị trí tìm kiếm
        }

        //Gán hình cho giới tính cần tìm
        if (findRoomModel.isGender()) {
            txtWantGender.setText("Nam");
        } else {
            txtWantGender.setText("Nữ");
        }
        //End Gán hình cho giới tính cần tìm

        //Gán hình cho giới tính cuả người tìm ở ghép
        if (findRoomModel.getFindRoomOwner().isGender()) {
            imgGenderUser.setImageResource(R.drawable.ic_svg_male_100);
        } else {
            imgGenderUser.setImageResource(R.drawable.ic_svg_female_100);
        }
        //End Gán hình cho giới tính cuả người tìm ở ghép


        if (findRoomModel.getListConvenientRoom() != null) {
            // Load danh sách tiện nghi của phòng trọ
            RecyclerView.LayoutManager layoutManagerConvenient = new GridLayoutManager(this, 3);
            recycler_convenients_room_detail.setLayoutManager(layoutManagerConvenient);
            adapterRecyclerConvenient = new AdapterRecyclerConvenient(this, getApplicationContext(),
                    R.layout.utility_element_grid_room_detail_view, findRoomModel.getListConvenientRoom());
            recycler_convenients_room_detail.setAdapter(adapterRecyclerConvenient);
            adapterRecyclerConvenient.notifyDataSetChanged();
        }

        // Hiển thị hình ảnh người dùng.
        Picasso.get().load(findRoomModel.getFindRoomOwner().getAvatar()).into(imgAvatarUser);

        //progressBarFindRoomDetail.setVisibility(View.GONE);

    }

    // Hàm gọi điện thoại cho chủ phòng trọ.
    private void clickCallPhone() {
        btnCallPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strPhoneNumbet = findRoomModel.getFindRoomOwner().getPhoneNumber();
                Intent intentCall = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", strPhoneNumbet, null));
                startActivity(intentCall);
            }
        });
    }
}
