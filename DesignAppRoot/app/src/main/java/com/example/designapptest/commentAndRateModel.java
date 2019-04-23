package com.example.designapptest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class commentAndRateModel extends Activity {

    Integer scores=0;
    GridView grVStar;
    ArrayList<Integer> lstStar;
    rateStarAdapter adapter;
    TextView txtScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_and_rate_view);

        initControl();

        initStar();

        adapter();

        chooseStar();

    }
    private void initControl() {
        grVStar=(GridView) findViewById(R.id.grV_star);
        txtScores=(TextView) findViewById(R.id.txt_scores);
    }

    private void initStar() {
        lstStar=new ArrayList<>();
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

    private void adapter()
    {
        adapter=new rateStarAdapter(this,R.layout.rate_star_grid_view,lstStar);
        grVStar.setAdapter(adapter);
    }

    private  void chooseStar()
    {
        grVStar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                rateStar(position);
                }
        }
        );
    }

    private void rateStar( int position)
    {
        scores=position+1;
        txtScores.setText("Đánh giá: "+scores.toString()+"/10");
        lstStar=new ArrayList<>();
        for (int i=0;i<10;i++)
        {
            if (i<=position)
            {
                lstStar.add(R.drawable.ic_svg_star_yellow_100);
            }
            else
            {
                lstStar.add(R.drawable.ic_svg_star_gray_100);
            }
        }
        adapter=new rateStarAdapter(this,R.layout.rate_star_grid_view,lstStar);
        grVStar.setAdapter(adapter);
    }
}
