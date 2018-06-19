package com.ellfors.gankreader.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ellfors.gankreader.R;
import com.ellfors.gankreader.app.AppConfig;
import com.ellfors.gankreader.base.BaseActivity;
import com.ellfors.gankreader.utils.BitmapSaveUtils;
import com.ellfors.gankreader.utils.ImageLoader;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

import static android.support.design.widget.Snackbar.make;

/**
 * 查看图片
 */
public class ImgDetailsActivity extends BaseActivity
{
    public static final String PHOTO_URL = "photo_url";

    @BindView(R.id.pd_relative)
    RelativeLayout mRelativeLayout;
    @BindView(R.id.photoview)
    PhotoView photoView;
    @BindView(R.id.photoview_bar)
    ProgressBar mBar;

    private String url;

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
    }

    private void bindData()
    {
        Intent intent = getIntent();
        if (intent != null)
            url = intent.getStringExtra(PHOTO_URL);
    }

    /**
     * 加载图片
     */
    private void loadImage()
    {
        mBar.setVisibility(View.VISIBLE);
        ImageLoader.load(mContext, url, photoView, new RequestListener<Drawable>()
        {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource)
            {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource)
            {
                mBar.setVisibility(View.GONE);
                return false;
            }
        });
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
            vibrator.vibrate(100);
            Snackbar snackbar = make(mRelativeLayout, "确定保存到本地么？", Snackbar.LENGTH_LONG)
                    .setAction("YES", v1 -> checkPermission());
            snackbar.show();
            return true;
        });
    }

    /**
     * 保存图片
     */
    private void saveImage()
    {
        Observable
                .create((ObservableOnSubscribe<Bitmap>) subscriber ->
                {
                    try
                    {
                        subscriber.onNext(Glide.with(mContext)
                                .asBitmap()
                                .load(url)
                                .submit(1080, 1920)
                                .get());
                    }
                    catch (Exception e)
                    {
                        subscriber.onNext(null);
                    }
                    subscriber.onComplete();
                })
                .map(bitmap -> bitmap != null && BitmapSaveUtils.saveImageToGallery(mContext, bitmap))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {

                    }

                    @Override
                    public void onNext(Boolean o)
                    {
                        showToast(o ? "保存成功" : "保存失败");
                        if (o)
                            finish();
                    }

                    @Override
                    public void onError(Throwable e)
                    {

                    }

                    @Override
                    public void onComplete()
                    {

                    }
                });
    }

    /**
     * 申请权限
     */
    private void checkPermission()
    {
        PermissionGen
                .with(this)
                .addRequestCode(AppConfig.PERMISSION_CODE)
                .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = AppConfig.PERMISSION_CODE)
    public void doSuccessPermission()
    {
        /* 申请权限成功 */
        saveImage();
    }

    @PermissionFail(requestCode = AppConfig.PERMISSION_CODE)
    public void doFailPermission()
    {
        /* 申请权限失败 */
    }
}
