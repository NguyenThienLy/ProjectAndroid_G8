package com.example.designapptest.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

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
        Picasso.get().load(lstStringLinkImage.get(positionDownload)).fit().into(imageDownload);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView((ImageView) object);
    }
}
