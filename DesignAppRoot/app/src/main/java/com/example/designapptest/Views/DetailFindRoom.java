package com.example.designapptest.Views;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.designapptest.Adapters.AdapterRecyclerComment;
import com.example.designapptest.Adapters.AdapterRecyclerConvenient;
import com.example.designapptest.Adapters.AdapterRecyclerFindRoom;
import com.example.designapptest.Adapters.AdapterViewPagerImageShow;
import com.example.designapptest.ClassOther.classFunctionStatic;
import com.example.designapptest.Controller.CommentController;
import com.example.designapptest.Model.FindRoomModel;
import com.example.designapptest.Model.RoomModel;
import com.example.designapptest.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailFindRoom extends AppCompatActivity {
    TextView txtNameUser, txtAboutPrice, txtLocationSearch, txtWantGender;

    Button btnCallPhone;

    ImageView imgGenderUser, imgAvatarUser;

    // Các recycler.
    RecyclerView recycler_convenients_room_detail;
    AdapterRecyclerConvenient adapterRecyclerConvenient;

    FindRoomModel findRoomModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_room_detail_view);

        findRoomModel = new FindRoomModel();
        findRoomModel = getIntent().getParcelableExtra(AdapterRecyclerFindRoom.SHARE_FINDROOM);

        initControl();

       // loadProgress();
    }

    @Override
    protected void onStart() {
        super.onStart();

        initData();
        clickCallPhone();
    }


    private void loadProgress() {
        classFunctionStatic.showProgress(this, imgAvatarUser);
    }

    // Khởi tạo các control trong room detail.
    private void initControl() {
        txtNameUser = (TextView) findViewById(R.id.txt_name_user);
        txtAboutPrice = (TextView) findViewById(R.id.txt_abouPrice);
        txtLocationSearch = (TextView) findViewById(R.id.txt_locationSearch);
        txtWantGender = (TextView) findViewById(R.id.txt_wantGender);

        btnCallPhone = (Button) findViewById(R.id.btn_callPhone);

        imgGenderUser = (ImageView) findViewById(R.id.img_gender_user);
        imgAvatarUser = (ImageView) findViewById(R.id.img_avatar_user);

        recycler_convenients_room_detail = (RecyclerView) findViewById(R.id.recycler_convenients);
    }

    // Khởi tạo các giá trị cho các control.
    private void initData() {
        //Gán các giá trị vào giao diện
 txtNameUser.setText(findRoomModel.getFindRoomOwner().getName());

        // Gán giá trị cho khoảng giá cần tìm kiếm.
        txtAboutPrice.setText(String.valueOf((int) findRoomModel.getMinPrice())
                + " - " + String.valueOf((int) findRoomModel.getMaxPrice()));

        // gán giá trị cho vị trí tìm kiếm.
        int index;
        String strLocationSearch = "";
        for (index = 0; index < findRoomModel.getLocation().size(); index++) {
            if (index != findRoomModel.getLocation().size() - 1)
                strLocationSearch += findRoomModel.getLocation().get(index) + ", ";
            else
                strLocationSearch += findRoomModel.getLocation().get(index);
        }
        txtLocationSearch.setText(strLocationSearch);
        // End gán giá trị cho vị trí tìm kiếm

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


        // Load danh sách tiện nghi của phòng trọ
        RecyclerView.LayoutManager layoutManagerConvenient = new GridLayoutManager(this, 3);
        recycler_convenients_room_detail.setLayoutManager(layoutManagerConvenient);
        adapterRecyclerConvenient = new AdapterRecyclerConvenient(this, getApplicationContext(),
                R.layout.utility_element_grid_rom_detail_view, findRoomModel.getListConvenientRoom());
        recycler_convenients_room_detail.setAdapter(adapterRecyclerConvenient);
        adapterRecyclerConvenient.notifyDataSetChanged();
        Picasso.get().load(findRoomModel.getFindRoomOwner().getAvatar()).into(imgAvatarUser);
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
