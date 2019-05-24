package com.example.designapptest.Views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.designapptest.R;

public class Main_Menu extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    FrameLayout fragmentContainer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__menu);

        initControl();
        //Chạy lần đầu tiên sẽ load vào màn hình main

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
                        Toast.makeText(Main_Menu.this,"fsf",Toast.LENGTH_LONG).show();
                        return true;
                    case R.id.nav_find_room:

                        return true;
                    case R.id.nav_map:

                        return true;
                    case R.id.nav_post_room:

                        return true;
                    case R.id.nav_acount:

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
