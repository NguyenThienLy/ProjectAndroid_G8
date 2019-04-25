package com.example.designapptest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.designapptest.Adapters.AdapterViewPagerCommentAndRate;
import com.example.designapptest.Model.RoomModel;

public class commentAndRateMain extends AppCompatActivity {
    ViewPager viewPagerCommentAndRate;
    TabLayout tabLayoutCommentAndRate;

    RoomModel roomModel;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_and_rate_main_room_detail);

        roomModel = getIntent().getParcelableExtra("phongtro");
        sharedPreferences = getSharedPreferences("currentUserId", MODE_PRIVATE);

        loadControl();
    }

    public RoomModel getRoomModelInfo() {
        return roomModel;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    private void loadControl() {
        viewPagerCommentAndRate = (ViewPager) findViewById(R.id.viewpager_commentAndRate_room_detail);
        tabLayoutCommentAndRate = (TabLayout) findViewById(R.id.tablayout_commentAndRate_room_detail);

        FragmentManager fragmentManager = getSupportFragmentManager();

        final AdapterViewPagerCommentAndRate adapterViewPagerCommentAndRate = new AdapterViewPagerCommentAndRate(fragmentManager);

        viewPagerCommentAndRate.setAdapter(adapterViewPagerCommentAndRate);
        tabLayoutCommentAndRate.setupWithViewPager(viewPagerCommentAndRate);

        viewPagerCommentAndRate.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutCommentAndRate));
        tabLayoutCommentAndRate.setTabsFromPagerAdapter(adapterViewPagerCommentAndRate);
        tabLayoutCommentAndRate.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPagerCommentAndRate));

        viewPagerCommentAndRate.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 1) {
                    commentAndRateStep2 fragment = (commentAndRateStep2) adapterViewPagerCommentAndRate.getItem(i);
                    fragment.setAdapter();
                } else if (i == 2) {
                    commentAndRateStep3 fragment = (commentAndRateStep3) adapterViewPagerCommentAndRate.getItem(i);
                    fragment.setAdapter();
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
}
