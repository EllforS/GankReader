package com.ellfors.gankreader.base;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;

import com.ellfors.gankreader.R;
import com.ellfors.gankreader.app.AppConfig;
import com.ellfors.gankreader.app.MyApplication;
import com.ellfors.gankreader.di.component.ActivityComponent;
import com.ellfors.gankreader.di.component.DaggerActivityComponent;
import com.ellfors.gankreader.di.module.ActivityModule;
import com.ellfors.gankreader.utils.AppUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity
{
    private Unbinder unbinder;
    public static Context mContext;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        init();
        unbinder = ButterKnife.bind(this);
        initInject();
        initEventAndData();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if(unbinder != null)
            unbinder.unbind();
    }

    /**
     * 设置当前主题
     */
    public void setDayNightMode(String type)
    {
        switch (type)
        {
            case AppConfig.MODE_DAY:
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                AppUtils.setStringSharedPreferences(mContext,AppConfig.DAY_NIGHT_MODE, AppConfig.MODE_DAY);
                break;
            case AppConfig.MODE_NIGHT:
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                AppUtils.setStringSharedPreferences(mContext,AppConfig.DAY_NIGHT_MODE,AppConfig.MODE_NIGHT);
                break;
            default:
                break;
        }
    }

    /**
     * 获取ActivityComponent
     */
    public ActivityComponent getActivityComponent()
    {
        return DaggerActivityComponent
                .builder()
                .appComponent(MyApplication.getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    /**
     * 获取ActivityModule
     */
    public ActivityModule getActivityModule()
    {
        return new ActivityModule(this);
    }

    /* 初始化注入 */
    public abstract void initInject();
    /* 绑定布局 */
    public abstract int getLayout();
    /* 初始化事件和数据 */
    public abstract void initEventAndData();

    private void init()
    {
        mContext = this;
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getResources().getString(R.string.progressdialog_toast));
    }

    /**
     * 省去类型转换 将此方法写在基类Activity
     */
    protected <T extends View> T $(int id)
    {
        return (T) super.findViewById(id);
    }

    /**
     * 获取ProgressDialog实例
     */
    public ProgressDialog getProgressDialog()
    {
        return mProgressDialog;
    }

    /**
     * 显示自定义ProgrssDialog
     */
    public void showProgressDialog(String msg)
    {
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }

    /**
     *  显示默认ProgressDialog
     */
    public void showDefaultProgressDialog()
    {
        mProgressDialog.setMessage(getResources().getString(R.string.progressdialog_toast));
        mProgressDialog.show();
    }

    /**
     * 隐藏ProgressDialog
     */
    public void dismissProgressDialog()
    {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * 判断ProgressDialog是否为显示状态
     */
    public boolean progressDialogIsShowing()
    {
        if (mProgressDialog.isShowing()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 打印文本 长时间
     */
    public void showToast(String msg)
    {
        AppUtils.showToast(mContext,msg);
    }

    /**
     * 打印文本 短时间
     */
    public void showToast(int resId)
    {
        AppUtils.showToast(mContext,resId);
    }

    /**
     * 显示视图
     */
    public void showView(View view)
    {
        AppUtils.showView(view);
    }

    /**
     * 隐藏视图
     */
    public void hideViewGone(View view)
    {
        AppUtils.hideViewGone(view);
    }

    /**
     * 隐藏视图 保留位置
     */
    public void hideViewInvisible(View view)
    {
        AppUtils.hideViewInvisible(view);
    }

    /**
     * 判断视图是否显示
     */
    public boolean isShowView(View view)
    {
        return AppUtils.isShowView(view);
    }

    /**
     * 写入SharedPreferences数据
     */
    public void setStringSharedPreferences(String key, String value)
    {
        AppUtils.setStringSharedPreferences(mContext,key,value);
    }

    /**
     * 读取SharedPreferences数据
     */
    public String getStringSharedPreferences(String key, String defaultValue)
    {
        return AppUtils.getStringSharedPreferences(mContext,key,defaultValue);
    }

    /**
     * 判断是否为中文版
     */
    public boolean isZh()
    {
        return AppUtils.isZh(mContext);
    }

    /**
     * 检查是否存在SDCard
     */
    public boolean hasSdcard()
    {
        return AppUtils.hasSdcard();
    }

    /**
     * 验证是否存在可用网络
     */
    public boolean CheckNetworkState()
    {
        return AppUtils.CheckNetworkState(mContext);
    }

    /**
     * 验证网络状态 0 存在wifi网络， 1 存在2/3G网络，2无网络连接
     */
    public int currentNetwork()
    {
        return AppUtils.currentNetwork(mContext);
    }

    /**
     * 获取屏幕高度像素
     */
    public int getDisplayHeight()
    {
        return AppUtils.getDisplayHeight((Activity)mContext);
    }

    /**
     * 获取屏幕宽度像素
     */
    public int getDisplayWidth()
    {
        return AppUtils.getDisplayWidth((Activity)mContext);
    }
}
