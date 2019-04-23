package com.example.designapptest.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.designapptest.R;
import com.example.designapptest.detailRoom;


import java.util.List;

public class AdapterRecyclerRateStar extends RecyclerView.Adapter<AdapterRecyclerRateStar.ViewHolder> {
    int layout;
    List<Integer> lstStar;
    Context context;

    public AdapterRecyclerRateStar(Context context, int layout, List<Integer> lstStar) {
        this.layout = layout;
        this.lstStar = lstStar;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_star;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img_star = (ImageView) itemView.findViewById(R.id.img_star);
        }
    }

    @NonNull
    @Override
    public AdapterRecyclerRateStar.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;


    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterRecyclerRateStar.ViewHolder viewHolder, final int i) {
        //Gán các giá trị vào giao diện

        viewHolder.img_star.setImageResource(lstStar.get(i));

//        // Đăng kí sự kiện click cho cardView
//        viewHolder.img_star.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int index = 0;
//                lstStar.clear();
//                for (index = 0; index < i; index++) {
//                    lstStar.add(R.drawable.ic_svg_star_yellow_100);
//                }
//
//                for (; i < 10; index++) {
//                    lstStar.add(R.drawable.ic_svg_star_gray_100);
//                }
//
//                viewHolder.img_star.setImageResource(lstStar.get(i));
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return lstStar.size();
    }
}
