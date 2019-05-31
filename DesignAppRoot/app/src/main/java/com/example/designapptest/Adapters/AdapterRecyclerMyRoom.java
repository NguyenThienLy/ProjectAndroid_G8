package com.example.designapptest.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.designapptest.Model.RoomModel;
import com.example.designapptest.R;
import com.example.designapptest.Views.detailRoom;
import com.example.designapptest.Views.postRoomAdapter;
import com.example.designapptest.Views.postRoomAdapterUpdate;

import java.util.List;

public class AdapterRecyclerMyRoom extends RecyclerView.Adapter<AdapterRecyclerMyRoom.ViewHolder> {


    List<RoomModel> RoomModelList;
    //Là biến lưu giao diện custom của từng row
    int resource;
    // Linh thêm
    Context context;

    public AdapterRecyclerMyRoom(Context context, List<RoomModel> RoomModelList, int resource) {
        this.context = context;
        this.RoomModelList = RoomModelList;
        this.resource = resource;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTimeCreated, txtName,txtAddress;
        ImageView imgRoom,imgVerified;
        Button btnUpdate,btnViews,btnDelete,btnComment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTimeCreated = (TextView) itemView.findViewById(R.id.txt_timeCreated);
            txtName = (TextView) itemView.findViewById(R.id.txt_name);

            txtAddress = (TextView) itemView.findViewById(R.id.txt_address);

            imgRoom = (ImageView) itemView.findViewById(R.id.img_room);

            imgVerified = (ImageView)itemView.findViewById(R.id.img_verified);

            btnUpdate =(Button)itemView.findViewById(R.id.btn_update);
            btnViews =(Button)itemView.findViewById(R.id.btn_views);
            btnDelete =(Button)itemView.findViewById(R.id.btn_delete);
            btnComment =(Button)itemView.findViewById(R.id.btn_comment);

        }

    }

    @NonNull
    @Override
    public AdapterRecyclerMyRoom.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(resource, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyclerMyRoom.ViewHolder viewHolder, int i) {
        final RoomModel roomModel = RoomModelList.get(i);

        viewHolder.txtName.setText(roomModel.getName());

        //Set address for room
        String longAddress = roomModel.getApartmentNumber() + " " + roomModel.getStreet() + ", "
                + roomModel.getWard() + ", " + roomModel.getCounty() + ", " + roomModel.getCity();
        viewHolder.txtAddress.setText(longAddress);
        //End Set address for room

        viewHolder.txtTimeCreated.setText(roomModel.getTimeCreated());

        //Hiển thị phòng đã được chứng thực
        if(roomModel.isAuthentication()){
            viewHolder.imgVerified.setVisibility(View.VISIBLE);
        }else {
            viewHolder.imgVerified.setVisibility(View.GONE);
        }
        //End hiển thị phòng đã được chúng thực

        //Download ảnh dùng picaso cho đỡ lag, dùng thuộc tính fit() để giảm dung lượng xuống thấp nhất có thể
        roomModel.getCompressionImageFit().centerCrop().into(viewHolder.imgRoom);

        viewHolder.btnViews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        viewHolder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ipostRoomUpdate = new Intent(context, postRoomAdapterUpdate.class);
                ipostRoomUpdate.putExtra("phongtro", roomModel);
                context.startActivity(ipostRoomUpdate);
            }
        });

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        viewHolder.btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return RoomModelList.size();
    }

    //Chuyển sang màn hình update
    private void updateRoom(RoomModel roomModel){

    }

}
