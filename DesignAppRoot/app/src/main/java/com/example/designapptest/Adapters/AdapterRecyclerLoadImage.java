package com.example.designapptest.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.example.designapptest.Model.loadImageModel;
import com.example.designapptest.R;

import java.util.List;

public class AdapterRecyclerLoadImage extends RecyclerView.Adapter<AdapterRecyclerLoadImage.ViewHolder> {

    List<loadImageModel> listPathImage;
    Context context;
    int resource;

    public AdapterRecyclerLoadImage(Context context, int resource, List<loadImageModel> listPathImage) {
        this.listPathImage = listPathImage;
        this.context = context;
        this.resource=resource;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgLoad;
        CheckBox chBoxChooseImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLoad = itemView.findViewById(R.id.img_load);
            chBoxChooseImage = itemView.findViewById(R.id.chBox_choose_image);
        }
    }

    @NonNull
    @Override
    public AdapterRecyclerLoadImage.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(resource,viewGroup,false);
        ViewHolder viewHolder  = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyclerLoadImage.ViewHolder viewHolder, final int i) {
        final loadImageModel loadImageModel = listPathImage.get(i);
        String Path = loadImageModel.getPath();
        Uri uri = Uri.parse(Path);
        viewHolder.imgLoad.setImageURI(uri);
        viewHolder.chBoxChooseImage.setChecked(loadImageModel.isChecked());

        viewHolder.chBoxChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox =(CheckBox)v;
                listPathImage.get(i).setChecked(checkBox.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPathImage.size();
    }
}
