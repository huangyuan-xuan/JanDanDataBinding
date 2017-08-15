package com.huangyuanlove.jandan.utils;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by HuangYuan on 2017/8/15.
 */

public class ImageViewAdapter {
    @BindingAdapter({"android:src"})
    public static void setSrc(ImageView view, Bitmap bitmap) {
        view.setImageBitmap(bitmap);
    }

    @BindingAdapter({"android:src"})
    public static void setSrc(ImageView view, int resId) {
        view.setImageResource(resId);
    }

    @BindingAdapter(value = {"imageUrl", "placeHolder", "errorHolder"}, requireAll = true)
    public static void loadImage(ImageView imageView, String url, Drawable placeHolder, Drawable errorHolder) {
        if (!TextUtils.isEmpty(url) && url.endsWith("gif")) {

            Glide.with(imageView.getContext())
                    .load(url)
                    .asGif()
                    .placeholder(placeHolder)
                    .thumbnail(0.1f)
                    .error(errorHolder)
                    .into(imageView);
        } else {
            Glide.with(imageView.getContext())
                    .load(url)
                    .placeholder(placeHolder)
                    .thumbnail(0.1f)
                    .error(errorHolder)
                    .into(imageView);
        }
    }

}
