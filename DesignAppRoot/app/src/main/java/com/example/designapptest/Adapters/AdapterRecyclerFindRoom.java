package com.example.designapptest.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.designapptest.ClassOther.classFunctionStatic;
import com.example.designapptest.Model.FindRoomModel;
import com.example.designapptest.Model.RoomModel;
import com.example.designapptest.R;
import com.example.designapptest.Views.DetailFindRoom;
import com.example.designapptest.Views.FindRoom;
import com.example.designapptest.Views.detailRoom;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterRecyclerFindRoom extends RecyclerView.Adapter<AdapterRecyclerFindRoom.ViewHolder> {
    List<FindRoomModel> findRoomModelList;
    //Là biến lưu giao diện custom của từng row
    int resource;
    Context context;

    public AdapterRecyclerFindRoom(Context context, List<FindRoomModel> findRoomModelList, int resource) {
        this.context = context;
        this.findRoomModelList = findRoomModelList;
        this.resource = resource;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtNameUser, txtAboutPrice, txtLocationSearch;
        ImageView imgAvatarUser, imgGenderUser;
        CardView cardViewFindRoomList;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            txtNameUser = (TextView) itemView.findViewById(R.id.txt_name_user);
            txtAboutPrice = (TextView) itemView.findViewById(R.id.txt_abouPrice);
            txtLocationSearch = (TextView) itemView.findViewById(R.id.txt_locationSearch) ;
            imgAvatarUser = (ImageView) itemView.findViewById(R.id.img_avatar_user);
            imgGenderUser = (ImageView) itemView.findViewById(R.id.img_gender_user);
            cardViewFindRoomList = (CardView) itemView.findViewById(R.id.cardViewFindRoomList);
        }
    }

    @NonNull
    @Override
    public AdapterRecyclerFindRoom.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(resource, viewGroup, false);

        AdapterRecyclerFindRoom.ViewHolder viewHolder = new AdapterRecyclerFindRoom.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyclerFindRoom.ViewHolder viewHolder, int i) {
        //Lấy giá trị trong list
        final FindRoomModel findRoomModel = findRoomModelList.get(i);

        //Gán các giá trị vào giao diện
        //classFunctionStatic.showProgress(context, viewHolder.imgAvatarUser);

        // Gán tên cho chủ phòng trọ.
        viewHolder.txtNameUser.setText(findRoomModel.getFindRoomOwner().getName());

        // Gán giá trị cho khoảng giá cần tìm kiếm.
        viewHolder.txtAboutPrice.setText(String.valueOf((int) findRoomModel.getMinPrice())
                                        + " - " + String.valueOf((int) findRoomModel.getMaxPrice()));

        // gán giá trị cho vị trí tìm kiếm.
        int index;
        String strLocationSearch = "";
        for (index = 0; index < findRoomModel.getLocation().size(); index++) {
            if (index != findRoomModel.getLocation().size() - 1)
                 strLocationSearch += findRoomModel.getLocation().get(index) + ", ";
            else
                strLocationSearch += findRoomModel.getLocation().get(index);
        }
        viewHolder.txtLocationSearch.setText(strLocationSearch);

        //Gán hình cho giới tính
        if (findRoomModel.getFindRoomOwner().isGender() == true) {
            viewHolder.imgGenderUser.setImageResource(R.drawable.ic_svg_male_100);
        } else {
            viewHolder.imgGenderUser.setImageResource(R.drawable.ic_svg_female_100);
        }
        //End Gán giá trị cho giới tính

        //Download ảnh dùng picaso cho đỡ lag
        Picasso.get().load(findRoomModel.getFindRoomOwner().getAvatar()).into(viewHolder.imgAvatarUser);
        //Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/findroomforrent-5bea0.appspot.com/o/Images%2Freceived-405711336891277_1555296117.jpg?alt=media&token=c27bd472-7a97-47dc-9f48-706b202929ce").into(viewHolder.imgRoom);

        viewHolder.imgAvatarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iDetailFindRoom = new Intent(context, DetailFindRoom.class);
                // Lỗi ở đây !!1
                //iDetailFindRoom.putExtra("timoghep", findRoomModel);
                context.startActivity(iDetailFindRoom);
            }
        });
    }

    @Override
    public int getItemCount() {
        return findRoomModelList.size();
    }
}
