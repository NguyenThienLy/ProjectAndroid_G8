package com.example.designapptest.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.designapptest.Adapters.AdapterRecyclerComment;
import com.example.designapptest.Controller.Interfaces.IRoomCommentModel;
import com.example.designapptest.Model.CommentModel;
import com.example.designapptest.Model.RoomModel;
import com.example.designapptest.R;

import org.w3c.dom.Text;

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
    String UID;
    static List<CommentModel> myListRoomComments = new ArrayList<>();
    static AdapterRecyclerComment myAdapterRecyclerComment = null;
    static TextView myTxtQuantityMyComments = null;

    public CommentController(Context context,  String UID) {
        this.context = context;
        this.commentModel = new CommentModel();
        this.UID = UID;
    }

    public void ListRoomComments(RecyclerView recyclerRoomComments, RoomModel roomModel, TextView txtRoomGreatReview,
                                 TextView txtRoomPrettyGoodReview, TextView txtRoomMediumReview, TextView txtRoomBadReview,
                                 TextView txtQuantityAllComments) {
        final List<CommentModel> commentModelList = new ArrayList<CommentModel>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerRoomComments.setLayoutManager(layoutManager);

        //Tạo adapter cho recycle view
        final AdapterRecyclerComment adapterRecyclerComment = new AdapterRecyclerComment(context,
                R.layout.comment_element_grid_room_detail_view, commentModelList, roomModel.getRoomID(),
                UID, true);
        //Cài adapter cho recycle
        recyclerRoomComments.setAdapter(adapterRecyclerComment);

        //Tạo interface để truyền dữ liệu lên từ model
        IRoomCommentModel iRoomCommentsModel = new IRoomCommentModel() {
            @Override
            public void getListRoomComments(CommentModel valueComment) {
                //Thêm vào trong danh sách bình luận
                commentModelList.add(valueComment);

                if (valueComment.getUser().equals(UID)) {
                    myListRoomComments.add(valueComment);
                    sortListComments(myListRoomComments);
                }

                sortListComments(commentModelList);

                //Thông báo là đã có thêm dữ liệu
                adapterRecyclerComment.notifyDataSetChanged();
                // Set số lượng hiển thị
                txtQuantityAllComments.setText(commentModelList.size() + "");

                if (myAdapterRecyclerComment != null) {
                    myAdapterRecyclerComment.notifyDataSetChanged();

                    // Hiển thị số lượng trả về
                    myTxtQuantityMyComments.setText(String.valueOf(myListRoomComments.size()));
                }
            }

            @Override
            public void refreshListRoomComments() {
                commentModelList.clear();
                myListRoomComments.clear();
            }

            @Override
            public void makeToast(String message) {

            }

            @Override
            public void setView() {
                // Load số lượng bình luận thuộc từng loại điểm
                long great, prettyGood, medium, bad;
                great = prettyGood = medium = bad = 0;
                for(CommentModel commentModel : commentModelList) {
                    long stars = commentModel.getStars();

                    if (stars < 4) {
                        bad += 1;
                    } else if (stars < 7) {
                        medium += 1;
                    } else if (stars < 9) {
                        prettyGood += 1;
                    } else {
                        great += 1;
                    }
                }

                txtRoomBadReview.setText(bad + "");
                txtRoomMediumReview.setText(medium + "");
                txtRoomPrettyGoodReview.setText(prettyGood + "");
                txtRoomGreatReview.setText(great + "");
                // End load số lượng bình luận thuộc từng loại điểm
            }
        };

        //Gọi hàm lấy dữ liệu trong model
        commentModel.ListRoomComments(iRoomCommentsModel, roomModel);
    }

    public void ListMyRoomComments(RecyclerView recyclerRoomComments, RoomModel roomModel, TextView txtQuantityMyComments) {
//        final List<CommentModel> commentModelList = new ArrayList<CommentModel>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerRoomComments.setLayoutManager(layoutManager);

        //Tạo adapter cho recycle view
        final AdapterRecyclerComment adapterRecyclerComment = new AdapterRecyclerComment(context,
                R.layout.comment_element_grid_room_detail_view, myListRoomComments, roomModel.getRoomID(),
                UID, true);
        //Cài adapter cho recycle
        recyclerRoomComments.setAdapter(adapterRecyclerComment);

        myAdapterRecyclerComment = adapterRecyclerComment;
        myTxtQuantityMyComments = txtQuantityMyComments;
        // Hiển thị kết quả trả về
        myTxtQuantityMyComments.setText(String.valueOf(myListRoomComments.size()));

        //Tạo interface để truyền dữ liệu lên từ model
//        IRoomCommentModel iRoomCommentsModel = new IRoomCommentModel() {
//            @Override
//            public void getListRoomComments(CommentModel valueComment) {
//                //Thêm vào trong danh sách bình luận
//                commentModelList.add(valueComment);
//
//                sortListComments(commentModelList);
//
//                //Thông báo là đã có thêm dữ liệu
//                adapterRecyclerComment.notifyDataSetChanged();
//            }
//
//            @Override
//            public void refreshListRoomComments() {
//                commentModelList.clear();
//            }
//        };

        //Gọi hàm lấy dữ liệu trong model
//        commentModel.ListMyRoomComments(iRoomCommentsModel, roomModel, sharedPreferences);
    }

    public void addComment(CommentModel newCommentModel, String roomId, TextView txtTitle, TextView txtContent) {
        IRoomCommentModel iRoomCommentsModel = new IRoomCommentModel() {
            @Override
            public void getListRoomComments(CommentModel valueComment) {

            }

            @Override
            public void refreshListRoomComments() {

            }

            @Override
            public void makeToast(String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void setView() {
                txtTitle.setText("");
                txtContent.setText("");
            }
        };

        commentModel.addComment(newCommentModel, roomId, iRoomCommentsModel);
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