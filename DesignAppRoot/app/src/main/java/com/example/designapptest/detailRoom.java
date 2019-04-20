package com.example.designapptest;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.designapptest.Adapters.AdapterRecyclerComment;
import com.example.designapptest.Adapters.AdapterRecyclerConvenient;
import com.example.designapptest.Adapters.AdapterViewPagerImageShow;
import com.example.designapptest.ClassOther.classFunctionStatic;
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

    // Các recycler.
    RecyclerView recycler_comment_room_detail;
    AdapterRecyclerComment adapterRecyclerComment;

    RecyclerView recycler_convenients_room_detail;
    AdapterRecyclerConvenient adapterRecyclerConvenient;

    RoomModel roomModel;

    // Hiển thị hình ảnh phòng trọ chế độ xem.
    Dialog dialogShowImage;
    Button btnCloseShowImage;
    ViewPager viewPagerShowImage;
    TextView txtPositionImage;

    int maxImageInRoom;
    int indexImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_detail_view);

        roomModel = getIntent().getParcelableExtra("phongtro");

        initControl();

        loadProgress();
    }

    @Override
    protected void onStart() {
        super.onStart();

        initData();

        clickShowImage();
    }

    private void loadProgress() {
        for (ImageView imageView : listImageRoom)
         classFunctionStatic.showProgress(this, imageView);
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
            downloadImageForImageControl(listImageRoom.get(i), i);
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

    // Hàm tải ảnh từ firebase về theo image control và vị trí ảnh cần lấy trên firebase.
    private void downloadImageForImageControl(final ImageView imageDownload, final int positionDownload) {
        StorageReference storageReference = FirebaseStorage
                .getInstance().getReference()
                .child("Images")
                .child(roomModel.getListImageRoom().get(positionDownload));

        final long ONE_MEGABYTE = 1024 * 1024;

        storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                //Tạo ảnh bitmap từ byte
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageDownload.setImageBitmap(bitmap);
                imageDownload.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void clickShowImage() {
        img_room1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageDialogCustom();
                indexImage = 0;
            }
        });

        img_room2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageDialogCustom();
                indexImage = 1;
            }
        });

        img_room3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageDialogCustom();
                indexImage = 2;
            }
        });

        img_room4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageDialogCustom();
                indexImage = 3;
            }
        });
    }

    private void showPostionImage(int position) {
        txtPositionImage.setText(String.valueOf(position) + "/" + String.valueOf(maxImageInRoom));
    }


    private void showImageDialogCustom() {
        maxImageInRoom = roomModel.getListImageRoom().size();

        dialogShowImage = new Dialog(detailRoom.this);
        dialogShowImage.setContentView(R.layout.dialog_show_image_detail_room);

        // Các control chế độ xem ảnh phòng trọ.
        viewPagerShowImage = (ViewPager) dialogShowImage.findViewById(R.id.viewPager_showImage_detail_room);
        btnCloseShowImage = (Button) dialogShowImage.findViewById(R.id.btn_closeShowImage_detail_room);
        txtPositionImage = (TextView) dialogShowImage.findViewById(R.id.txt_positionImage_detail_room);

        // Truyền dữ liệu qua view pager show image.
        AdapterViewPagerImageShow adapterViewPagerImageShow = new AdapterViewPagerImageShow(this, roomModel.getListImageRoom());
        viewPagerShowImage.setAdapter(adapterViewPagerImageShow);

        // Hiển thị lúc ban đầu.
        showPostionImage(1);

        viewPagerShowImage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                int index = viewPagerShowImage.getCurrentItem();
                showPostionImage(index + 1);
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        // Kết thúc chế độ xem ảnh phòng trọ.
        btnCloseShowImage.setEnabled(true);
        btnCloseShowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogShowImage.cancel();
            }
        });


        // Tùy chính lại dialog gồm giao diện, match parent width, height và chuyển background trong suốt.
        dialogShowImage.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogShowImage.getWindow().setDimAmount(0.9f);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialogShowImage.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);

        dialogShowImage.show();
    }
}
