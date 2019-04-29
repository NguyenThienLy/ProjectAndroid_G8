package com.example.designapptest.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.designapptest.Adapters.AdapterRecyclerComment;
import com.example.designapptest.Controller.Interfaces.IRoomCommentModel;
import com.example.designapptest.Model.CommentModel;
import com.example.designapptest.Model.RoomModel;
import com.example.designapptest.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class CommentController {
    CommentModel commentModel;
    Context context;
    SharedPreferences sharedPreferences;

    public CommentController(Context context, SharedPreferences sharedPreferences) {
        this.context = context;
        this.commentModel = new CommentModel();
        this.sharedPreferences = sharedPreferences;
    }

    public void ListRoomComments(RecyclerView recyclerRoomComments, RoomModel roomModel){
        final List<CommentModel> commentModelList = new ArrayList<CommentModel>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerRoomComments.setLayoutManager(layoutManager);

        //Tạo adapter cho recycle view
        final AdapterRecyclerComment adapterRecyclerComment = new AdapterRecyclerComment(context,
                R.layout.comment_element_grid_room_detail_view, commentModelList, roomModel.getRoomID(),
                sharedPreferences, true);
        //Cài adapter cho recycle
        recyclerRoomComments.setAdapter(adapterRecyclerComment);

        //Tạo interface để truyền dữ liệu lên từ model
        IRoomCommentModel iRoomCommentsModel = new IRoomCommentModel() {
            @Override
            public void getListRoomComments(CommentModel valueComment) {
                //Thêm vào trong danh sách bình luận
                commentModelList.add(valueComment);

                sortListComments(commentModelList);

                //Thông báo là đã có thêm dữ liệu
                adapterRecyclerComment.notifyDataSetChanged();
            }

            @Override
            public void refreshListRoomComments() {
                commentModelList.clear();
            }
        };

        //Gọi hàm lấy dữ liệu trong model
        commentModel.ListRoomComments(iRoomCommentsModel, roomModel);
    }

    public void ListMyRoomComments(RecyclerView recyclerRoomComments, RoomModel roomModel){
        final List<CommentModel> commentModelList = new ArrayList<CommentModel>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerRoomComments.setLayoutManager(layoutManager);

        //Tạo adapter cho recycle view
        final AdapterRecyclerComment adapterRecyclerComment = new AdapterRecyclerComment(context,
                R.layout.comment_element_grid_room_detail_view, commentModelList, roomModel.getRoomID(),
                sharedPreferences, true);
        //Cài adapter cho recycle
        recyclerRoomComments.setAdapter(adapterRecyclerComment);

        //Tạo interface để truyền dữ liệu lên từ model
        IRoomCommentModel iRoomCommentsModel = new IRoomCommentModel() {
            @Override
            public void getListRoomComments(CommentModel valueComment) {
                //Thêm vào trong danh sách bình luận
                commentModelList.add(valueComment);

                sortListComments(commentModelList);

                //Thông báo là đã có thêm dữ liệu
                adapterRecyclerComment.notifyDataSetChanged();
            }

            @Override
            public void refreshListRoomComments() {
                commentModelList.clear();
            }
        };

        //Gọi hàm lấy dữ liệu trong model
        commentModel.ListMyRoomComments(iRoomCommentsModel, roomModel, sharedPreferences);
    }

    public void addComment(CommentModel newCommentModel, String roomId) {
        commentModel.addComment(newCommentModel, roomId, context);
    }

    public void sortListComments(List<CommentModel> listComments) {
        Collections.sort(listComments, new Comparator<CommentModel>() {
            @Override
            public int compare(CommentModel commentModel1, CommentModel commentModel2) {
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date1 = null;
                try {
                    date1 = df.parse(commentModel1.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Date date2 = null;
                try {
                    date2 = df.parse(commentModel2.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return date2.compareTo(date1);
            }
        });
    }
}