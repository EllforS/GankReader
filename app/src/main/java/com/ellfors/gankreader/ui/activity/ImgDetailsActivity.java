package com.ellfors.gankreader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.ellfors.gankreader.R;
import com.ellfors.gankreader.base.BaseActivity;
import com.ellfors.gankreader.utils.BitmapSaveUtils;
import com.ellfors.gankreader.utils.GlideLoadUtils;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
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
    protected void onDestroy() {
        super.onDestroy();
        if(subscriptions != null)
            subscriptions.unsubscribe();
    }

    private void bindData()
    {
        Intent intent = getIntent();
        if(intent != null)
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
        Glide.with(this)
                .load(url)
                .into(photoView);
    }

    /**
     * 设置监听
     */
    private void setListener()
    {
        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                finish();
            }

            @Override
            public void onOutsidePhotoTap() {

            }
        });

        photoView.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                Vibrator vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(500);

                Snackbar snackbar = make(mRelativeLayout,"确定保存到本地么？",Snackbar.LENGTH_LONG)
                        .setAction("YES", new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                saveImage();
                            }
                        });
                snackbar.show();
                return true;
            }
        });
    }

    /**
     * 保存图片
     */
    private void saveImage()
    {
        Subscription sub = Observable
                .create(new Observable.OnSubscribe<Boolean>()
                {
                    @Override
                    public void call(Subscriber<? super Boolean> subscriber)
                    {
                        Bitmap bitmap = GlideLoadUtils.getGlideBitmap(mContext,url,480,800);
                        subscriber.onNext(BitmapSaveUtils.saveImageToGallery(mContext,bitmap,SavePath));
                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>()
                {
                    @Override
                    public void call(Boolean aBoolean)
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
                    }
                });
        subscriptions.add(sub);
    }
}
