package com.example.designapptest.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.designapptest.Model.ConvenientModel;
import com.example.designapptest.R;

import java.util.List;

public class AdapterRecyclerConvenient extends RecyclerView.Adapter<AdapterRecyclerConvenient.ViewHolder> {

    Context context;
    Context contextMain;
    int layout;
    List<ConvenientModel> ConvenientModelList;

    public AdapterRecyclerConvenient(Context context, Context contextMain, int layout, List<ConvenientModel> ConvenientModelList) {
        this.context = context;
        this.contextMain = contextMain;
        this.layout = layout;
        this.ConvenientModelList = ConvenientModelList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_utility_room_detail;
        TextView txt_nameUtility_utility_room_detail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img_utility_room_detail = (ImageView) itemView.findViewById(R.id.img_utility_room_detail);
            txt_nameUtility_utility_room_detail = (TextView) itemView.findViewById(R.id.txt_nameUtility_utility_room_detail);
        }
    }

    @NonNull
    @Override
    public AdapterRecyclerConvenient.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterRecyclerConvenient.ViewHolder viewHolder, int i) {
        final ConvenientModel convenientModel = ConvenientModelList.get(i);

        //Gán các giá trị vào giao diện
        viewHolder.txt_nameUtility_utility_room_detail.setText(convenientModel.getName());

        int resourceId = context.getResources().getIdentifier(convenientModel.getImageName(), "drawable", contextMain.getPackageName());
        viewHolder.img_utility_room_detail.setImageResource(resourceId);
    }

    @Override
    public int getItemCount() {
        int convenients = ConvenientModelList.size();
        if (convenients > 5) {
            return 5;
        } else {
            return convenients;
        }
    }
}
