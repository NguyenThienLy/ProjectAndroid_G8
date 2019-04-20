package com.example.designapptest.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.designapptest.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class AdapterViewPagerImageShow extends PagerAdapter {
    Context context;
    List<String> lstStringLinkImage;
    LinearLayout linearLayout;
    TextView txtPositionImage;
    //int maxSizeImage;
    //int positonImage;

    public AdapterViewPagerImageShow(Context context, List<String> stringLinkImage) {
        this.context = context;
        this.lstStringLinkImage = stringLinkImage;
       // this.txtPositionImage = txtPositionImage;
       // this.maxSizeImage =  lstStringLinkImage.size();
    }

    @Override
    public int getCount() {
        return lstStringLinkImage.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view == object;
    }

//    private void showPostionImage() {
//        txtPositionImage.setText(String.valueOf(positonImage) + "/" + String.valueOf(maxSizeImage));
//    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        downloadImageForImageControl(imageView, position);
        container.addView(imageView, 0);

    //    showPostionImage();

        return imageView;
    }

    private void downloadImageForImageControl(final ImageView imageDownload, final int positionDownload) {
        StorageReference storageReference = FirebaseStorage
                .getInstance().getReference()
                .child("Images")
                .child(lstStringLinkImage.get(positionDownload));

        final long ONE_MEGABYTE = 1024 * 1024;

        storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                //Tạo ảnh bitmap từ byte
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageDownload.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView((ImageView) object);
    }
}
