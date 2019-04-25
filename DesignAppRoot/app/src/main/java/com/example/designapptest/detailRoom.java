package com.example.designapptest;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.designapptest.Adapters.AdapterRecyclerComment;
import com.example.designapptest.Adapters.AdapterRecyclerConvenient;
import com.example.designapptest.Adapters.AdapterViewPagerImageShow;
import com.example.designapptest.ClassOther.classFunctionStatic;
import com.example.designapptest.Controller.CommentController;
import com.example.designapptest.Model.RoomModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class detailRoom extends AppCompatActivity {
    TextView txtRoomType, txtRoomMaxNumber, txtQuantityComment, txtRoomName,
            txtRoomPrice, txtRoomStatus, txtRoomArea, txtRoomAddress, txtRoomDescription,
            txtRoomGreatReview, txtRoomPrettyGoodReview, txtRoomMediumReview, txtRoomBadReview,
            txtQuantityComment_2,txtRoomPhoneNumber;

    Button btnCallPhone, btnPostComment, btnViewAll;

    ImageView imgRoomGender, imgRoom1, imgRoom2, imgRoom3, imgRoom4;

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

    SharedPreferences sharedPreferences;
    CommentController commentController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_detail_view);

        roomModel = getIntent().getParcelableExtra("phongtro");
        sharedPreferences = getSharedPreferences("currentUserId", MODE_PRIVATE);

        initControl();

        loadProgress();
    }

    @Override
    protected void onStart() {
        super.onStart();

        initData();

        clickCallPhone();

        clickPostComment();

        clickShowImage();

    }

    private void loadProgress() {
        for (ImageView imageView : listImageRoom)
         classFunctionStatic.showProgress(this, imageView);
    }

    // Khởi tạo các control trong room detail.
    private void initControl() {
        txtRoomType = (TextView) findViewById(R.id.txt_roomType);
        txtRoomMaxNumber = (TextView) findViewById(R.id.txt_roomMaxNumber);
        txtQuantityComment = (TextView) findViewById(R.id.txt_quantityComment);
        txtRoomName = (TextView) findViewById(R.id.txt_roomName);
        txtRoomPrice = (TextView) findViewById(R.id.txt_roomPrice);
        txtRoomStatus = (TextView) findViewById(R.id.txt_roomStatus);
        txtRoomArea = (TextView) findViewById(R.id.txt_roomArea);
        txtRoomAddress = (TextView) findViewById(R.id.txt_roomAddress);
        txtRoomDescription = (TextView) findViewById(R.id.txt_roomDescription);
        txtRoomGreatReview = (TextView) findViewById(R.id.txt_roomGreatReview);
        txtRoomPrettyGoodReview = (TextView) findViewById(R.id.txt_roomPrettyGoodReview);
        txtRoomMediumReview = (TextView) findViewById(R.id.txt_roomMediumReview);
        txtRoomBadReview = (TextView) findViewById(R.id.txt_roomBadReview);
        txtQuantityComment_2 = (TextView) findViewById(R.id.txt_quantityComment_2);
        txtRoomPhoneNumber = (TextView)findViewById(R.id.txt_room_phonenumber);

        btnCallPhone = (Button) findViewById(R.id.btn_callPhone) ;
        btnPostComment = (Button) findViewById(R.id.btn_postComment) ;
        btnViewAll = (Button) findViewById(R.id.btn_viewAll) ;

        imgRoomGender = (ImageView) findViewById(R.id.img_roomGender);
        imgRoom1 = (ImageView) findViewById(R.id.img_room1);
        imgRoom2 = (ImageView) findViewById(R.id.img_room2);
        imgRoom3 = (ImageView) findViewById(R.id.img_room3);
        imgRoom4 = (ImageView) findViewById(R.id.img_room4);

        listImageRoom = new ArrayList<ImageView>();

        listImageRoom.add(imgRoom1);
        listImageRoom.add(imgRoom2);
        listImageRoom.add(imgRoom3);
        listImageRoom.add(imgRoom4);

        recycler_comment_room_detail = (RecyclerView) findViewById(R.id.recycler_comment_room_detail);
        recycler_convenients_room_detail = (RecyclerView) findViewById(R.id.recycler_convenients_room_detail);
    }

    // Khởi tạo các giá trị cho các control.
    private void initData() {
        //Gán các giá trị vào giao diện
        txtRoomType.setText(roomModel.getRoomType());
        txtRoomMaxNumber.setText(String.valueOf((int) roomModel.getMaxNumber()));
        txtQuantityComment.setText("0");
        txtQuantityComment_2.setText("0");
        txtRoomName.setText(roomModel.getName());
        txtRoomPrice.setText(String.valueOf(roomModel.getRentalCosts()) + "tr/ phòng");
        txtRoomPhoneNumber.setText(roomModel.getRoomOwner().getPhoneNumber());

        if (((int) roomModel.getCurrentNumber()) < ((int) roomModel.getMaxNumber())) {
            txtRoomStatus.setText("Còn");
        } else {
            txtRoomStatus.setText("Hết");
        }

        txtRoomArea.setText(roomModel.getLength() + "m" + " x " + roomModel.getWidth() + "m");
        txtRoomDescription.setText(roomModel.getDescribe());
        txtRoomGreatReview.setText(roomModel.getGreat() + "");
        txtRoomPrettyGoodReview.setText(roomModel.getPrettyGood() + "");
        txtRoomMediumReview.setText(roomModel.getMedium() + "");
        txtRoomBadReview.setText(roomModel.getBad() + "");

        //Set address for room
        String longAddress = roomModel.getApartmentNumber() +" "+roomModel.getStreet()+", "
                +roomModel.getWard()+", "+roomModel.getCounty()+", "+roomModel.getCity();
        txtRoomAddress.setText(longAddress);
        //End set address for room

        //Gán hình cho giới tính
        if (roomModel.isGender()) {
            imgRoomGender.setImageResource(R.drawable.ic_svg_male_100);
        } else {
            imgRoomGender.setImageResource(R.drawable.ic_svg_female_100);
        }
        //End Gán giá trị cho giới tính

        //Download hình ảnh cho room
        for (int i = 0; i < roomModel.getListImageRoom().size(); i++) {
            downloadImageForImageControl(listImageRoom.get(i), i);
        }
        // End download hình ảnh cho room

        // Load danh sách bình luận của phòng trọ
//        RecyclerView.LayoutManager layoutManagerComment = new LinearLayoutManager(this);
//        recycler_comment_room_detail.setLayoutManager(layoutManagerComment);
//        adapterRecyclerComment = new AdapterRecyclerComment(this, R.layout.comment_element_grid_room_detail_view, roomModel.getListCommentRoom(), sharedPreferences);
//        recycler_comment_room_detail.setAdapter(adapterRecyclerComment);
//        adapterRecyclerComment.notifyDataSetChanged();
        commentController = new CommentController(this, sharedPreferences);
        commentController.ListRoomComments(recycler_comment_room_detail, roomModel);

        //Gán giá trị cho số lượt bình luận
        if (roomModel.getListCommentRoom().size() > 0) {
            txtQuantityComment.setText(roomModel.getListCommentRoom().size() + "");
            txtQuantityComment_2.setText(roomModel.getListCommentRoom().size() + "");
        }
        //End gán giá trị cho số lượng bình luận

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

    // Hàm gọi điện thoại cho chủ phòng trọ.
    private void clickCallPhone() {
        btnCallPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strPhoneNumbet = roomModel.getRoomOwner().getPhoneNumber();
                Intent intentCall = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", strPhoneNumbet, null));

                intentCall.putExtra("phongtro", roomModel);
                startActivity(intentCall);
            }
        });
    }

    // Hàm viết hiển thị màn hình viết bình luận.
    private  void clickPostComment() {
        btnPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCommentAndRate = new Intent(detailRoom.this, commentAndRateMain.class);
                intentCommentAndRate.putExtra("phongtro", roomModel);
                startActivity(intentCommentAndRate);
            }
        });

        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCommentAndRate = new Intent(detailRoom.this, commentAndRateMain.class);
                intentCommentAndRate.putExtra("phongtro", roomModel);
                startActivity(intentCommentAndRate);
            }
        });
    }

    // Hàm hiển thị ảnh phòng trọ chế độ xem.
    private void clickShowImage() {
        imgRoom1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageDialogCustom();
                indexImage = 0;
            }
        });

        imgRoom2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageDialogCustom();
                indexImage = 1;
            }
        });

        imgRoom3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageDialogCustom();
                indexImage = 2;
            }
        });

        imgRoom4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageDialogCustom();
                indexImage = 3;
            }
        });
    }

    // Hàm hiển thị vị trí ảnh trên tống số.
    private void showPostionImage(int position) {
        txtPositionImage.setText(String.valueOf(position) + "/" + String.valueOf(maxImageInRoom));
    }

    // Hàm tạo ra custom dialong và các tác vụ liên quan.
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
