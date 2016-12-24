package com.ellfors.gankreader.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import static com.bumptech.glide.Glide.with;

/**
 * Glide图像加载工具类
 */
public class GlideLoadUtils {
    public static void loadImage(Context context, String url, ImageView imageView) {
        with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .placeholder(R.mipmap.ic_launcher)
//                .error(R.mipmap.ic_launcher)
                .centerCrop()
                .thumbnail(0.1f)
                .into(imageView);
    }

    /**
     * Glide获取Bitmap
     * <p>
     * 需要自己设置宽高
     */
    public static final Bitmap getGlideBitmap(Context context, String url, int width, int height) {
        Bitmap bitmap = null;
        try {
            bitmap = with(context)
                    .load(url)
                    .asBitmap() //必须
                    .centerCrop()
                    .into(width, height)//设置Bitmap大小
                    .get();
        } catch (Exception e) {

        }
        return bitmap;
    }

    /**
     * Glide获取Bitmap
     * <p>
     * 默认宽高都为300
     */
    public static final Bitmap getGlideBitmap(Context context, String url) {
        Bitmap bitmap = null;
        try {
            bitmap = with(context)
                    .load(url)
                    .asBitmap() //必须
                    .centerCrop()
                    .into(300, 300)//设置Bitmap大小
                    .get();
        } catch (Exception e) {

        }
        return bitmap;
    }


}
