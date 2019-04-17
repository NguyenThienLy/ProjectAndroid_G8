package com.example.designapptest;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class postRoomAdapter extends AppCompatActivity {// chưa test

    private ViewPager pager;
   // private  TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_room_main_view);
        addControl();
    }
    private void addControl(){
        pager = (ViewPager) findViewById(R.id.viewpager_post_room);
      //  tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        FragmentManager manager = getSupportFragmentManager();
        PagerAdapter adapter = new PagerAdapter(manager);
        pager.setAdapter(adapter);
     //   tabLayout.setupWithViewPager(pager);
      //  pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
     //   tabLayout.setTabsFromPagerAdapter(adapter);//deprecated
     //   tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }
    public class PagerAdapter extends FragmentStatePagerAdapter{
        PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }
        @Override
        public Fragment getItem(int position) {
            Fragment frag=null;
            switch (position){
                case 0:
                    frag = new postRoomStep1();
                    break;
                case 1:
                    frag = new postRoomStep2();
                    break;
                case 2:
                    frag = new postRoomStep3();
                    break;
                case 3:
                    frag=new postRoomStep4();
                    break;
            }
            return frag;
        }

        @Override
        public int getCount() {
            return 4;
        }
//        @Override
//        public CharSequence getPageTitle(int position) {
//            String title = "";
//            switch (position){
//                case 0:
//                    title = "One";
//                    break;
//                case 1:
//                    title = "Two";
//                    break;
//                case 2:
//                    title = "Three";
//                    break;
//            }
//            return title;
//        }
    }
}
