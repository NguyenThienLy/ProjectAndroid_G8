package com.example.designapptest.Views;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.designapptest.R;

public class PopUpViews extends AppCompatActivity {

    ImageButton btnFinish;
    String roomID;

    TextView txtQuantityAllView;
    ProgressBar progressBarAllView;
    LinearLayout lnLyQuantityTopAllView;

    NestedScrollView nestedScrollAllView;
    ProgressBar progressBarLoadMoreAllView;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_views);
        roomID = getIntent().getStringExtra("phongtro");
        initControl();
        changeDisplay();
    }

    //Hàm thay đổi kích thước của màn hình
    private void changeDisplay() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int) (width * .9), (int) (height * .9));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);
    }

    private void initControl(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        txtQuantityAllView = (TextView) findViewById(R.id.txt_quantity_all_view);
        lnLyQuantityTopAllView = (LinearLayout) findViewById(R.id.lnLt_quantity_top_all_view);
        progressBarAllView = (ProgressBar) findViewById(R.id.progress_bar_all_view);
        progressBarAllView.getIndeterminateDrawable().setColorFilter(Color.parseColor("#00DDFF"),
                android.graphics.PorterDuff.Mode.MULTIPLY);

        nestedScrollAllView = (NestedScrollView) findViewById(R.id.nested_scroll_all_view);
        progressBarLoadMoreAllView = (ProgressBar) findViewById(R.id.progress_bar_load_more_all_view);
        progressBarLoadMoreAllView.getIndeterminateDrawable().setColorFilter(Color.parseColor("#00DDFF"),
                android.graphics.PorterDuff.Mode.MULTIPLY);

        btnFinish = findViewById(R.id.btn_finish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setView() {
        // Set view
        lnLyQuantityTopAllView.setVisibility(View.GONE);
        progressBarAllView.setVisibility(View.VISIBLE);
        progressBarLoadMoreAllView.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setView();
    }
}
