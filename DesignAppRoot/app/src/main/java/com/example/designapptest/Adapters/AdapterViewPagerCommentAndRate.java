package com.example.designapptest.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.designapptest.Views.commentAndRateStep1;
import com.example.designapptest.Views.commentAndRateStep2;
import com.example.designapptest.Views.commentAndRateStep3;

import java.util.HashMap;

public class AdapterViewPagerCommentAndRate extends FragmentStatePagerAdapter {

    private HashMap<Integer, Fragment> fragmentHashMap = new HashMap<>();

    public AdapterViewPagerCommentAndRate(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;

        //Check neu da put vao trong hashmap tuc la da khoi tao gia tri thi tra ve trong hashmap
        if (fragmentHashMap.get(i) != null) {
            return fragmentHashMap.get(i);
        }

        //Neu chua khoi tao thi tao moi va push vao hash map
        switch (i) {
            case 0:
                fragment = new commentAndRateStep1();
                fragmentHashMap.put(0, fragment);
                break;
            case 1:
                fragment = new commentAndRateStep2();
                fragmentHashMap.put(1, fragment);
                break;
            case 2:
                fragment = new commentAndRateStep3();
                fragmentHashMap.put(2, fragment);
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
