package com.example.designapptest.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.designapptest.R;

public class Main_Menu extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    FrameLayout fragmentContainer;

    MainActivity HomeView;
    accountView AccountView;
    postRoomAdapter PostRoomView;
    FindRoom FindRoomView;
    searchMapView SearchMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__menu);

        initControl();
        //Chạy lần đầu tiên sẽ load vào màn hình main
        HomeView = new MainActivity();
        setFragment(HomeView);

    }

    //Khởi tạo control
    private void initControl(){
        fragmentContainer = findViewById(R.id.fragment_container);
        bottomNavigation = findViewById(R.id.bottom_navigation);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                switch (id) {
                    case R.id.nav_home:
                        //Chuyển sang màn hình home
                        //HomeView = new MainActivity();
                        setFragment(HomeView);
                        return true;
                    case R.id.nav_find_room:
                        //Chuyển sang màn hình tìm kiếm ở ghép
                        FindRoomView = new FindRoom();
                        setFragment(FindRoomView);
                        return true;
                    case R.id.nav_map:
                        //Chuyển sang màn hình map
                        SearchMapView = new searchMapView();
                        setFragment(SearchMapView);
                        return true;
                    case R.id.nav_post_room:
                        //Hiển thị màn hình đăng phòng mới
                        Intent intent = new Intent(Main_Menu.this, postRoomAdapter.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_acount:
                        //Chuyển sang màn hình quản lý tài khoản
                        AccountView = new accountView();
                        setFragment(AccountView);
                        return true;

                    default:
                        return false;
                }
            }
        });
    }

    //Hàm replace fragment tương ứng khi chọn vào menu
    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
    }
}
