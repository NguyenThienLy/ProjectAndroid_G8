package com.example.designapptest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.designapptest.Adapters.AdapterRecyclerComment;
import com.example.designapptest.Adapters.AdapterRecyclerConvenient;
import com.example.designapptest.Model.RoomModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class detailRoom extends Activity {
    TextView txt_roomType, txt_roomMaxNumber, txt_quantityComment, txt_roomName,
            txt_roomPrice, txt_roomStatus, txt_roomArea, txt_roomAddress, txt_roomDescription,
            txt_roomGreatReview, txt_roomPrettyGoodReview, txt_roomMediumReview, txt_roomBadReview,
            txt_quantityComment_2;
    ImageView img_roomGender, img_room1, img_room2, img_room3, img_room4;
    List<ImageView> listImageRoom;
    //GridView grVUtilitiyRoomDetail;
    GridView grVSameCriteriaRoomDetail;

    //ListView lstVCommentRoomDetail;
    RecyclerView recycler_comment_room_detail;
    AdapterRecyclerComment adapterRecyclerComment;

    //ArrayList<utilityRoomModel> lstUtilityRoom;
    RecyclerView recycler_convenients_room_detail;
    AdapterRecyclerConvenient adapterRecyclerConvenient;

    ArrayList<roomModel> lstSameCriteriaRoom;
    //ArrayList<commentRoomModel> lstCommentRoom;

    RoomModel roomModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_detail_view);

        roomModel = getIntent().getParcelableExtra("phongtro");

        initControl();
//
//        initDataUtilitiy();
//
//        initDataSameCriteria();
//
//        initDataComment();
//
//        adapter();
    }

    @Override
    protected void onStart() {
        super.onStart();

        initData();
    }

    private void initControl() {
        txt_roomType = (TextView) findViewById(R.id.txt_roomType);
        txt_roomMaxNumber = (TextView) findViewById(R.id.txt_roomMaxNumber);
        txt_quantityComment = (TextView) findViewById(R.id.txt_quantityComment);
        txt_roomName = (TextView) findViewById(R.id.txt_roomName);
        txt_roomPrice = (TextView) findViewById(R.id.txt_roomPrice);
        txt_roomStatus = (TextView) findViewById(R.id.txt_roomStatus);
        txt_roomArea = (TextView) findViewById(R.id.txt_roomArea);
        txt_roomAddress = (TextView) findViewById(R.id.txt_roomAddress);
        txt_roomDescription = (TextView) findViewById(R.id.txt_roomDescription);
        txt_roomGreatReview = (TextView) findViewById(R.id.txt_roomGreatReview);
        txt_roomPrettyGoodReview = (TextView) findViewById(R.id.txt_roomPrettyGoodReview);
        txt_roomMediumReview = (TextView) findViewById(R.id.txt_roomMediumReview);
        txt_roomBadReview = (TextView) findViewById(R.id.txt_roomBadReview);
        txt_quantityComment_2 = (TextView) findViewById(R.id.txt_quantityComment_2);

        img_roomGender = (ImageView) findViewById(R.id.img_roomGender);
        img_room1 = (ImageView) findViewById(R.id.img_room1);
        img_room2 = (ImageView) findViewById(R.id.img_room2);
        img_room3 = (ImageView) findViewById(R.id.img_room3);
        img_room4 = (ImageView) findViewById(R.id.img_room4);
        listImageRoom = new ArrayList<ImageView>();
        listImageRoom.add(img_room1);
        listImageRoom.add(img_room2);
        listImageRoom.add(img_room3);
        listImageRoom.add(img_room4);

        //grVUtilitiyRoomDetail = (GridView) findViewById(R.id.grV_utiliti_rom_detail);
        grVSameCriteriaRoomDetail = (GridView) findViewById(R.id.grV_sameCriteria_room_detail);
        //lstVCommentRoomDetail = (ListView) findViewById(R.id.lst_comment_room_detail);
        recycler_comment_room_detail = (RecyclerView) findViewById(R.id.recycler_comment_room_detail);
        recycler_convenients_room_detail = (RecyclerView) findViewById(R.id.recycler_convenients_room_detail);
    }

    private void initData() {
        //Gán các giá trị vào giao diện
        txt_roomType.setText(roomModel.getRoomType());
        txt_roomMaxNumber.setText(String.valueOf((int) roomModel.getMaxNumber()));
        txt_quantityComment.setText("0");
        txt_quantityComment_2.setText("0");
        txt_roomName.setText(roomModel.getName());
        txt_roomPrice.setText(String.valueOf(roomModel.getRentalCosts()) + "tr/ phòng");

        if (((int) roomModel.getCurrentNumber()) < ((int) roomModel.getMaxNumber())) {
            txt_roomStatus.setText("Còn");
        } else {
            txt_roomStatus.setText("Hết");
        }

        txt_roomArea.setText(roomModel.getLength() + "m" + " x " + roomModel.getWidth() + "m");
        txt_roomAddress.setText(roomModel.getAddress());
        txt_roomDescription.setText(roomModel.getDescribe());
        txt_roomGreatReview.setText(roomModel.getGreat() + "");
        txt_roomPrettyGoodReview.setText(roomModel.getPrettyGood() + "");
        txt_roomMediumReview.setText(roomModel.getMedium() + "");
        txt_roomBadReview.setText(roomModel.getBad() + "");

        //Gán giá trị cho số lượt bình luận
        if (roomModel.getListCommentRoom().size() > 0) {
            txt_quantityComment.setText(roomModel.getListCommentRoom().size() + "");
            txt_quantityComment_2.setText(roomModel.getListCommentRoom().size() + "");
        }
        //End gán giá trị cho số lượng bình luận

        //Gán hình cho giới tính
        if (roomModel.isGender()) {
            img_roomGender.setImageResource(R.drawable.ic_svg_male_100);
        } else {
            img_roomGender.setImageResource(R.drawable.ic_svg_female_100);
        }
        //End Gán giá trị cho giới tính

        //Download hình ảnh cho room
        for (int i = 0; i < roomModel.getListImageRoom().size(); i++) {
            StorageReference storageReference = FirebaseStorage
                    .getInstance().getReference()
                    .child("Images")
                    .child(roomModel.getListImageRoom().get(i));

            final long ONE_MEGABYTE = 1024 * 1024;
            final int position = i;
            storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    //Tạo ảnh bitmap từ byte
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    listImageRoom.get(position).setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
        // End download hình ảnh cho room

        // Load danh sách bình luận của phòng trọ
        RecyclerView.LayoutManager layoutManagerComment = new LinearLayoutManager(this);
        recycler_comment_room_detail.setLayoutManager(layoutManagerComment);
        adapterRecyclerComment = new AdapterRecyclerComment(this, R.layout.comment_element_grid_room_detail_view, roomModel.getListCommentRoom());
        recycler_comment_room_detail.setAdapter(adapterRecyclerComment);
        adapterRecyclerComment.notifyDataSetChanged();

        // Load danh sách tiện nghi của phòng trọ
        RecyclerView.LayoutManager layoutManagerConvenient = new GridLayoutManager(this, 3);
        recycler_convenients_room_detail.setLayoutManager(layoutManagerConvenient);
        adapterRecyclerConvenient = new AdapterRecyclerConvenient(this, getApplicationContext(), R.layout.utility_element_grid_rom_detail_view, roomModel.getListConvenientRoom());
        recycler_convenients_room_detail.setAdapter(adapterRecyclerConvenient);
        adapterRecyclerConvenient.notifyDataSetChanged();
    }

    private void initDataUtilitiy() {
//        lstUtilityRoom = new ArrayList<>();
//
//        lstUtilityRoom.add(new utilityRoomModel(R.drawable.ic_svg_aircondition_100, "Máy lạnh"));
//        lstUtilityRoom.add(new utilityRoomModel(R.drawable.ic_svg_wc_100, "WC riêng"));
//        lstUtilityRoom.add(new utilityRoomModel(R.drawable.ic_svg_motobike_100, "Chỗ để xe"));
//        lstUtilityRoom.add(new utilityRoomModel(R.drawable.ic_svg_wifi_100, "Wifi"));
//        lstUtilityRoom.add(new utilityRoomModel(R.drawable.ic_svg_clock_100, "Tự do"));
//        lstUtilityRoom.add(new utilityRoomModel(R.drawable.ic_svg_keyhome_100, "Chủ riêng"));
//        lstUtilityRoom.add(new utilityRoomModel(R.drawable.ic_svg_fridge_100, "Tủ lạnh"));
//        lstUtilityRoom.add(new utilityRoomModel(R.drawable.ic_svg_washmachine_100, "Máy giặt"));
//        lstUtilityRoom.add(new utilityRoomModel(R.drawable.ic_svg_security_100, "An ninh"));
//        lstUtilityRoom.add(new utilityRoomModel(R.drawable.ic_svg_bed_100, "Giường"));
//        lstUtilityRoom.add(new utilityRoomModel(R.drawable.ic_svg_cupboard_100, "Tủ để đồ"));
//        lstUtilityRoom.add(new utilityRoomModel(R.drawable.ic_svg_window_100, "Cửa số"));
//        lstUtilityRoom.add(new utilityRoomModel(R.drawable.ic_svg_waterheater_100, "Máy nước nóng"));
    }

    private void initDataSameCriteria() {
        lstSameCriteriaRoom = new ArrayList<>();

        lstSameCriteriaRoom.add(new roomModel(R.drawable.avt_jpg_room, "Cho thuê phòng trọ giá rẻ", "2.5 triệu/phòng", "54 Âu Cơ, Bình Thạnh, TP Hồ Chí Minh", 8, 256, "PHÒNG TRỌ"));
        lstSameCriteriaRoom.add(new roomModel(R.drawable.avt_jpg_room, "Cho thuê phòng trọ giá rẻ", "3.5 triệu/phòng", "54 Âu Cơ, Quận 11, TP Hồ Chí Minh", 6, 18, "PHÒNG TRỌ"));
        lstSameCriteriaRoom.add(new roomModel(R.drawable.avt_jpg_room, "Cho thuê phòng trọ giá rẻ", "2.5 triệu/phòng", "54 Âu Cơ, Bình Thạnh, TP Hồ Chí Minh", 5, 365, "CHUNG CƯ"));
        lstSameCriteriaRoom.add(new roomModel(R.drawable.avt_jpg_room, "Cho thuê phòng trọ giá rẻ", "3.5 triệu/phòng", "54 Âu Cơ, Quận 11, TP Hồ Chí Minh", 4, 256, "PHÒNG TRỌ"));
        lstSameCriteriaRoom.add(new roomModel(R.drawable.avt_jpg_room, "Cho thuê phòng trọ giá rẻ", "2.5 triệu/phòng", "54 Âu Cơ, Bình Thạnh, TP Hồ Chí Minh", 6, 28, "KÍ TÚC XÁ"));
        lstSameCriteriaRoom.add(new roomModel(R.drawable.avt_jpg_room, "Cho thuê phòng trọ giá rẻ", "3.5 triệu/phòng", "54 Âu Cơ, Quận 11, TP Hồ Chí Minh", 7, 147, "PHÒNG TRỌ"));
    }

    private void initDataComment() {
//        lstCommentRoom = new ArrayList<>();
//
//        lstCommentRoom.add(new commentRoomModel(R.drawable.ic_svg_avt_01_100,"Nguyễn Thiên Lý",5,7, "Phòng rất tốt", "Mình tới tìm phòng này thì thấy phòng đăng khá đúng với thông tin trên app, khá hài lòng"));
//        lstCommentRoom.add(new commentRoomModel(R.drawable.ic_svg_avt_02_100,"Trần Khánh Linh",7,7, "Cảm thấy rất tuyệt vời", "Rất hài lòng...."));
//        lstCommentRoom.add(new commentRoomModel(R.drawable.ic_svg_avt_03_100,"Trần Nhất Sinh",12,7, "Chỗ ở không đúng mô tả", "Chỗ này mình tới rồi mọi người không đúng như mô tả đâu mn ơi"));
//        lstCommentRoom.add(new commentRoomModel(R.drawable.ic_svg_avt_04_100,"Lê Tường Qui",3,7, "Chỗ ở tệ", "Chỗ ở quá chật thiếu nhiều các tiện ít mà giá còn đắt hơn chỗ khác nữa chứ!!"));
    }

    private void adapter() {
        //utilityRoomAdapter adapterUtilityRoom = new utilityRoomAdapter(this, R.layout.utility_element_grid_rom_detail_view, lstUtilityRoom);
        roomAdapter adapterRoom = new roomAdapter(this, R.layout.rom_element_grid_view, lstSameCriteriaRoom);
        //commentRoomAdapter adapterComment = new commentRoomAdapter(this, R.layout.comment_element_grid_room_detail_view, lstCommentRoom);

        //grVUtilitiyRoomDetail.setAdapter(adapterUtilityRoom);
        grVSameCriteriaRoomDetail.setAdapter(adapterRoom);
        //lstVCommentRoomDetail.setAdapter(adapterComment);

        //grVUtilitiyRoomDetail.setClickable(false);
        grVSameCriteriaRoomDetail.setClickable(false);
        //lstVCommentRoomDetail.setClickable(false);
    }
}
