package com.example.designapptest.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.designapptest.Model.RoomModel;
import com.example.designapptest.commentAndRateStep1;
import com.example.designapptest.commentAndRateStep2;
import com.example.designapptest.commentAndRateStep3;

public class AdapterViewPagerCommentAndRate extends FragmentStatePagerAdapter {

    public AdapterViewPagerCommentAndRate(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;

        switch (i) {
            case 0:

                fragment = new commentAndRateStep1();
                break;
            case 1:
                fragment = new commentAndRateStep2();
                break;
            case 2:
                fragment = new commentAndRateStep3();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Viết bình luận";
                break;
            case 1:
                title = "Xem tất cả";
                break;
            case 2:
                title = "Xem của bạn";
                break;
        }
        return title;
    }
}
