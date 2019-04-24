package com.example.designapptest;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.example.designapptest.Adapters.AdapterViewPagerCommentAndRate;
import com.example.designapptest.Model.RoomModel;
import com.google.firebase.database.core.view.View;

public class commentAndRateMain extends AppCompatActivity {
    ViewPager viewPagerCommentAndRate;
    TabLayout tabLayoutCommentAndRate;

    RoomModel roomModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_and_rate_main_room_detail);

        roomModel = getIntent().getParcelableExtra("phongtro");

        loadControl();
    }

    public RoomModel getRoomModelInfo() {
        return roomModel;
    }

    private void loadControl() {
        viewPagerCommentAndRate = (ViewPager) findViewById(R.id.viewpager_commentAndRate_room_detail);
        tabLayoutCommentAndRate = (TabLayout) findViewById(R.id.tablayout_commentAndRate_room_detail);

        FragmentManager fragmentManager = getSupportFragmentManager();

        AdapterViewPagerCommentAndRate adapterViewPagerCommentAndRate = new AdapterViewPagerCommentAndRate(fragmentManager);

        viewPagerCommentAndRate.setAdapter(adapterViewPagerCommentAndRate);
        tabLayoutCommentAndRate.setupWithViewPager(viewPagerCommentAndRate);

        viewPagerCommentAndRate.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutCommentAndRate));
        tabLayoutCommentAndRate.setTabsFromPagerAdapter(adapterViewPagerCommentAndRate);
        tabLayoutCommentAndRate.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPagerCommentAndRate));
    }


}
