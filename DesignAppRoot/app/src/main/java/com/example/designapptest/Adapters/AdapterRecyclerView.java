package com.example.designapptest.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.designapptest.Model.ViewModel;
import com.example.designapptest.R;

import java.util.List;

public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.ViewHolder> {

    Context context;
    int layout;
    List<ViewModel> viewModelList;

    public AdapterRecyclerView(Context context, int layout, List<ViewModel> viewModelList){
        this.context=context;
        this.layout=layout;
        this.viewModelList=viewModelList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgAvtUser;
        TextView txtNameUser,txtTimeView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgAvtUser = itemView.findViewById(R.id.img_avt_user);
            txtNameUser = itemView.findViewById(R.id.txt_name_user);
            txtTimeView = itemView.findViewById(R.id.txt_time_view);
        }
    }

    @NonNull
    @Override
    public AdapterRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyclerView.ViewHolder viewHolder, int i) {
        final ViewModel viewModel = viewModelList.get(i);

        viewHolder.txtTimeView.setText(viewModel.getTime());
        viewHolder.txtNameUser.setText(viewModel.getUserView().getName());

        viewModel.getCompressionImageFit().centerCrop().into(viewHolder.imgAvtUser);
        //Picasso.get().load(viewModel.getUserView().getAvatar()).fit().into(viewHolder.imgAvtUser);
    }

    @Override
    public int getItemCount() {
        return viewModelList.size();
    }

}
