package com.example.designapptest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.designapptest.Adapters.AdapterRecyclerRateStar;

import java.util.ArrayList;

public class commentAndRateStep1 extends Fragment {
    int scores;

    RecyclerView recycler_rate_star;
    AdapterRecyclerRateStar adapterRecyclerRateStar;

    ArrayList<Integer> lstStar;

    TextView txtScores;


    View viewCommentAndRateStep1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewCommentAndRateStep1 = inflater.inflate(R.layout.comment_and_rate_step_1_room_detail_view, container, false);

        initControl();

        initStar();

        setAdapter();

        chooseStar();

        return viewCommentAndRateStep1;
    }

    public commentAndRateStep1() {

    }

    private void initControl() {
        recycler_rate_star = (RecyclerView) viewCommentAndRateStep1.findViewById(R.id.recycler_rate_star);
        txtScores = (TextView) viewCommentAndRateStep1.findViewById(R.id.txt_scores);
    }

    private void initStar() {
        lstStar = new ArrayList<>();
        lstStar.add(R.drawable.ic_svg_star_gray_100);
        lstStar.add(R.drawable.ic_svg_star_gray_100);
        lstStar.add(R.drawable.ic_svg_star_gray_100);
        lstStar.add(R.drawable.ic_svg_star_gray_100);
        lstStar.add(R.drawable.ic_svg_star_gray_100);
        lstStar.add(R.drawable.ic_svg_star_gray_100);
        lstStar.add(R.drawable.ic_svg_star_gray_100);
        lstStar.add(R.drawable.ic_svg_star_gray_100);
        lstStar.add(R.drawable.ic_svg_star_gray_100);
        lstStar.add(R.drawable.ic_svg_star_gray_100);
    }

    private void setAdapter() {
        RecyclerView.LayoutManager layoutManagerComment = new LinearLayoutManager((commentAndRateMain)getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler_rate_star.setLayoutManager(layoutManagerComment);
        adapterRecyclerRateStar = new AdapterRecyclerRateStar((commentAndRateMain)getContext(), R.layout.rate_star_grid_view, lstStar);
        recycler_rate_star.setAdapter(adapterRecyclerRateStar);
        adapterRecyclerRateStar.notifyDataSetChanged();
    }

    private void chooseStar() {
//        recycler_rate_star.setItem(new AdapterView.OnItemClickListener() {
//               @Override
//               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                   rateStar(position);
//               }
//           }
//        );

    }

    private void rateStar(int position) {
        scores = position + 1;

        txtScores.setText("Đánh giá: " + String.valueOf(scores) + "/10");
        lstStar = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (i <= position) {
                lstStar.add(R.drawable.ic_svg_star_yellow_100);
            } else {
                lstStar.add(R.drawable.ic_svg_star_gray_100);
            }
        }

        adapterRecyclerRateStar = new AdapterRecyclerRateStar((commentAndRateMain)getContext(), R.layout.rate_star_grid_view, lstStar);
        recycler_rate_star.setAdapter(adapterRecyclerRateStar);
    }
}
