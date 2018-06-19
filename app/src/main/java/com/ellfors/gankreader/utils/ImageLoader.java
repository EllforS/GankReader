package com.ellfors.gankreader.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.ellfors.gankreader.R;

/**
 * 基于Glide的图片加载工具类
 */
public class ImageLoader
{
    private static final String TAG = "ImageLoader";
    private static final int IMG_DEF = 0;
    private static final int IMG_FAIL = R.drawable.img_error;
    private static final int DEFAULT_AVATAR = 0;

    /**
     * 根据上下文和 url获取 Glide的DrawableTypeRequest
     *
     * @param context 上下文
     * @param url     图片连接
     * @param <T>     Context类型
     * @param <K>     url类型
     * @return 返回DrawableTypeRequst<K> 类型
     */
    private static <T, K> RequestBuilder<Drawable> getDrawableTypeRequest(T context, K url)
    {
        RequestBuilder<Drawable> builder = null;
        try
        {
            if (context instanceof android.support.v4.app.Fragment)
            {
                builder = Glide.with((android.support.v4.app.Fragment) context).load(url);
            }
            else if (context instanceof android.app.Fragment)
            {
                builder = Glide.with((android.app.Fragment) context).load(url);
            }
            else if (context instanceof Activity)
            {    //包括FragmentActivity
                builder = Glide.with((Activity) context).load(url);
            }
            else if (context instanceof Context)
            {
                builder = Glide.with((Context) context).load(url);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return builder;
    }

    /**
     * 加载图片
     *
     * @param mContext 上下文
     * @param url      图片资源
     * @param iv       图片视图
     * @param <T>      上下文泛型
     * @param <K>      图片资源泛型
     */
    public static <T, K> void load(T mContext, K url, ImageView iv)
    {
        RequestBuilder<Drawable> builder = getDrawableTypeRequest(mContext, url);
        if (builder != null)
            builder.apply(RequestOptions.centerCropTransform().placeholder(IMG_DEF).error(IMG_FAIL)).into(iv);
        else
            Log.e(TAG, "--Image context error--");
    }

    /**
     * 加载图片
     *
     * @param mContext 上下文
     * @param url      图片资源
     * @param <T>      上下文泛型
     * @param <K>      图片资源泛型
     */
    public static <T, K> RequestBuilder<Drawable> load(T mContext, K url)
    {
        RequestBuilder<Drawable> builder = getDrawableTypeRequest(mContext, url);
        if (builder != null)
            return builder;
        else
            return null;
    }

    /**
     * 加载图片
     *
     * @param mContext        上下文
     * @param url             图片资源
     * @param iv              图片视图
     * @param <T>             上下文泛型
     * @param <K>             图片资源泛型
     * @param requestListener 加载图片请求监听
     */
    public static <T, K> void load(T mContext, K url, ImageView iv, RequestListener<Drawable> requestListener)
    {
        RequestBuilder<Drawable> builder = getDrawableTypeRequest(mContext, url);
        if (builder != null)
            builder.apply(RequestOptions.centerCropTransform().placeholder(IMG_DEF).error(IMG_FAIL)).listener(requestListener).into(iv);
        else
            Log.e(TAG, "--Image context error--");
    }

    /**
     * 加载图片
     *
     * @param mContext 上下文
     * @param url      图片资源
     * @param iv       图片视图
     * @param <T>      上下文泛型
     * @param <K>      图片资源泛型
     * @param errorRes 指定默认加载失败图片
     */
    public static <T, K> void load(T mContext, K url, ImageView iv, int errorRes)
    {
        RequestBuilder<Drawable> builder = getDrawableTypeRequest(mContext, url);
        if (builder != null)
            builder.apply(RequestOptions.centerCropTransform().error(errorRes)).into(iv);
        else
            Log.e(TAG, "--Image context error--");
    }

    /**
     * 加载图片
     *
     * @param mContext 上下文
     * @param url      图片资源
     * @param iv       图片视图
     * @param <T>      上下文泛型
     * @param <K>      图片资源泛型
     * @param ivW      图片宽
     * @param ivH      图片高
     */
    public static <T, K> void load(T mContext, K url, ImageView iv, int ivW, int ivH)
    {
        RequestBuilder<Drawable> builder = getDrawableTypeRequest(mContext, url);
        if (builder != null)
            builder.apply(RequestOptions.centerCropTransform().override(ivW, ivH).placeholder(IMG_DEF).error(IMG_FAIL)).into(iv);
        else
            Log.e(TAG, "--Image context error--");
    }

    /**
     * 加载图片(没有默认图片和样式)
     *
     * @param mContext 上下文
     * @param url      图片资源
     * @param iv       图片视图
     * @param <T>      上下文泛型
     * @param <K>      图片资源泛型
     */
    public static <T, K> void loadNoDef(T mContext, K url, ImageView iv)
    {
        RequestBuilder<Drawable> builder = getDrawableTypeRequest(mContext, url);
        if (builder != null)
            builder.into(iv);
        else
            Log.e(TAG, "--Image context error--");
    }

    /**
     * 加载填充图片
     *
     * @param mContext 上下文
     * @param url      图片资源
     * @param iv       图片视图
     * @param <T>      上下文泛型
     * @param <K>      图片资源泛型
     */
    public static <T, K> void loadFit(T mContext, K url, ImageView iv)
    {
        RequestBuilder<Drawable> builder = getDrawableTypeRequest(mContext, url);
        if (builder != null)
            builder.apply(RequestOptions.fitCenterTransform().placeholder(IMG_DEF).error(IMG_FAIL)).into(iv);
        else
            Log.e(TAG, "--Image context error--");
    }

    /**
     * 加载填充图片
     *
     * @param mContext 上下文
     * @param url      图片资源
     * @param iv       图片视图
     * @param <T>      上下文泛型
     * @param <K>      图片资源泛型
     */
    public static <T, K> void loadFit(T mContext, K url, ImageView iv, int errorRes, int ivW, int ivH)
    {
        RequestBuilder<Drawable> builder = getDrawableTypeRequest(mContext, url);
        if (builder != null)
            builder.apply(RequestOptions.fitCenterTransform().error(errorRes).override(ivW, ivH)).into(iv);
        else
            Log.e(TAG, "--Image context error--");
    }

    /**
     * 加载填充图片
     *
     * @param mContext        上下文
     * @param url             图片资源
     * @param iv              图片视图
     * @param <T>             上下文泛型
     * @param <K>             图片资源泛型
     * @param requestListener 加载图片请求监听
     */
    public static <T, K> void loadFit(T mContext, K url, ImageView iv, RequestListener<Drawable> requestListener)
    {
        RequestBuilder<Drawable> builder = getDrawableTypeRequest(mContext, url);
        if (builder != null)
            builder.apply(RequestOptions.fitCenterTransform().placeholder(IMG_DEF).error(IMG_FAIL)).listener(requestListener).into(iv);
        else
            Log.e(TAG, "--Image context error--");
    }

    /**
     * 加载圆形图片
     *
     * @param mContext 上下文
     * @param url      图片资源
     * @param iv       图片视图
     * @param <T>      上下文泛型
     * @param <K>      图片资源泛型
     */
    public static <T, K> void loadCircle(T mContext, K url, ImageView iv)
    {
        RequestBuilder<Drawable> builder = getDrawableTypeRequest(mContext, url);
        if (builder != null)
            builder.apply(RequestOptions.circleCropTransform().placeholder(IMG_DEF).error(IMG_FAIL)).into(iv);
        else
            Log.e(TAG, "--Image context error--");
    }

    /**
     * 加载圆形图片
     *
     * @param mContext 上下文
     * @param url      图片资源
     * @param iv       图片视图
     * @param <T>      上下文泛型
     * @param <K>      图片资源泛型
     * @param errorRes 指定默认加载失败图片
     */
    public static <T, K> void loadCircle(T mContext, K url, ImageView iv, int errorRes)
    {
        RequestBuilder<Drawable> builder = getDrawableTypeRequest(mContext, url);
        if (builder != null)
            builder.apply(RequestOptions.circleCropTransform().error(errorRes)).into(iv);
        else
            Log.e(TAG, "--Image context error--");
    }

    /**
     * 加载圆形头像
     *
     * @param mContext 上下文
     * @param url      图片资源
     * @param iv       图片视图
     * @param <T>      上下文泛型
     * @param <K>      图片资源泛型
     */
    public static <T, K> void loadCircleHead(T mContext, K url, ImageView iv)
    {
        RequestBuilder<Drawable> builder = getDrawableTypeRequest(mContext, url);
        if (builder != null)
            builder.apply(RequestOptions.circleCropTransform().error(DEFAULT_AVATAR).placeholder(DEFAULT_AVATAR)).into(iv);
        else
            Log.e(TAG, "--Image head context error--");
    }

    /**
     * 简单图片加载回调
     *
     * @param <T> 图片url 或资源id 或 文件
     * @param <K> 返回的资源,GlideDrawable或者Bitmap或者GifDrawable,ImageView.setImageRecourse设置
     */
    public interface ImageLoadListener<T, K>
    {
        /**
         * 图片加载成功回调
         *
         * @param uri      图片url 或资源id 或 文件
         * @param view     目标载体，不传则为空
         * @param resource 返回的资源,GlideDrawable或者Bitmap或者GifDrawable,ImageView.setImageRecourse设置
         */
        void onLoadingComplete(T uri, ImageView view, K resource);

        /**
         * 图片加载异常返回
         *
         * @param source 图片地址、File、资源id
         * @param e      异常信息
         */
        void onLoadingError(T source, Exception e);
    }


    /**
     * 详细加载图片加载回调
     *
     * @param <T> 图片url 或资源id 或 文件
     * @param <K> 返回的资源
     */
    public interface ImageLoadDetailListener<T, K>
    {
        /**
         * 图片加载成功回调
         *
         * @param uri      图片url 或资源id 或 文件
         * @param view     目标载体，不传则为空
         * @param resource 返回的资源,GlideDrawable或者Bitmap或者GifDrawable,ImageView.setImageRecourse设置
         */
        void onLoadingComplete(T uri, ImageView view, K resource);

        /**
         * 图片加载异常返回
         *
         * @param source        图片地址、File、资源id
         * @param errorDrawable 加载错误占位图
         * @param e             异常信息
         */
        void onLoadingError(T source, Drawable errorDrawable, Exception e);

        /**
         * 加载开始
         *
         * @param source      图片来源
         * @param placeHolder 开始加载占位图
         */
        void onLoadingStart(T source, Drawable placeHolder);
    }

    /**
     * 释放内存
     *
     * @param context 上下文
     */
    private static void clearMemory(Context context)
    {
        Glide.get(context).clearMemory();
    }

    /**
     * 取消所有正在下载或等待下载的任务。
     *
     * @param context 上下文
     */
    public static void cancelAllTasks(Context context)
    {
        Glide.with(context).pauseRequests();
    }

    /**
     * 恢复所有任务
     */
    public static void resumeAllTasks(Context context)
    {
        Glide.with(context).resumeRequests();
    }

    /**
     * 清除磁盘缓存
     *
     * @param context 上下文
     */
    private static void clearDiskCache(final Context context)
    {
        new Thread(() -> Glide.get(context).clearDiskCache()).start();
    }

    /**
     * 清除所有缓存
     *
     * @param context 上下文
     */
    public static void cleanAll(Context context)
    {
        clearDiskCache(context);
        clearMemory(context);
    }
}
