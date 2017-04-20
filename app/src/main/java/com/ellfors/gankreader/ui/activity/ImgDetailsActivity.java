package com.ellfors.gankreader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ellfors.gankreader.R;
import com.ellfors.gankreader.base.BaseActivity;
import com.ellfors.gankreader.utils.BitmapSaveUtils;
import com.ellfors.gankreader.utils.GlideLoadUtils;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

import static android.support.design.widget.Snackbar.make;

/**
 * 查看图片
 */
public class ImgDetailsActivity extends BaseActivity
{
    public static final String PHOTO_URL = "photo_url";
    private String SavePath = "/GankReader/FuLi/";

    @BindView(R.id.pd_relative)
    RelativeLayout mRelativeLayout;
    @BindView(R.id.photoview)
    PhotoView photoView;
    @BindView(R.id.photoview_bar)
    ProgressBar mBar;

    private String url;
    private CompositeSubscription subscriptions;

    @Override
    public void initInject()
    {

    }

    @Override
    public int getLayout()
    {
        return R.layout.activity_img_details;
    }

    @Override
    public void initEventAndData()
    {
        bindData();
        loadImage();
        setListener();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (subscriptions != null)
            subscriptions.unsubscribe();
    }

    private void bindData()
    {
        Intent intent = getIntent();
        if (intent != null)
        {
            url = intent.getStringExtra(PHOTO_URL);
        }
        subscriptions = new CompositeSubscription();
    }

    /**
     * 加载图片
     */
    private void loadImage()
    {
        mBar.setVisibility(View.VISIBLE);
        Glide.with(this)
                .load(url)
                .listener(new RequestListener<String, GlideDrawable>()
                {
                    @Override
                    public boolean onException(Exception e, String model,
                                               Target<GlideDrawable> target, boolean isFirstResource)
                    {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource,
                                                   String model, Target<GlideDrawable> target,
                                                   boolean isFromMemoryCache, boolean isFirstResource)
                    {
                        mBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(photoView);
    }

    /**
     * 设置监听
     */
    private void setListener()
    {
        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener()
        {
            @Override
            public void onPhotoTap(View view, float x, float y)
            {
                finish();
            }

            @Override
            public void onOutsidePhotoTap()
            {

            }
        });

        photoView.setOnLongClickListener(v -> {
            Vibrator vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(500);
            Snackbar snackbar = make(mRelativeLayout, "确定保存到本地么？", Snackbar.LENGTH_LONG)
                    .setAction("YES", v1 -> saveImage());
            snackbar.show();
            return true;
        });
    }

    /**
     * 保存图片
     */
    private void saveImage()
    {
        Subscription sub =
                Observable
                        .create(new Observable.OnSubscribe<Boolean>()
                        {
                            @Override
                            public void call(Subscriber<? super Boolean> subscriber)
                            {
                                Bitmap bitmap = GlideLoadUtils.getGlideBitmap(mContext, url, 1080, 1920);
                                subscriber.onNext(BitmapSaveUtils.saveImageToGallery(mContext, bitmap, SavePath));
                                subscriber.onCompleted();
                            }
                        })
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(aBoolean ->
                        {
                            if (aBoolean)
                            {
                                showToast("保存成功");
                                finish();
                            }
                            else
                            {
                                showToast("保存失败");
                            }
                        });
        subscriptions.add(sub);
    }
}
