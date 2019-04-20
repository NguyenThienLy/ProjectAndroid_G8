package com.example.designapptest.ClassOther;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.designapptest.R;

public class classFunctionStatic {
    public static void  showProgress(Context context, ImageView imageView) {
        Glide.with(context).load(R.drawable.progress_gift_loading_image).into(imageView);
    }
}
