package com.ellfors.gankreader.base;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ellfors.gankreader.R;
import com.ellfors.gankreader.app.MyApplication;
import com.ellfors.gankreader.di.component.DaggerFragmentComponent;
import com.ellfors.gankreader.di.component.FragmentComponent;
import com.ellfors.gankreader.di.module.FragmentModule;
import com.ellfors.gankreader.utils.AppUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment
{
    private Context mContext;
    private ProgressDialog mProgressDialog;
    private View mView;
    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        if(unbinder != null)
            unbinder.unbind();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(getLayout(),null);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        mView = view;
        initInject();
        initEventAndData();
    }

    /**
     * 获取FragmentComponent
     */
    public FragmentComponent getFragmentComponent()
    {
        return DaggerFragmentComponent
                .builder()
                .appComponent(MyApplication.getAppComponent())
                .fragmentModule(getFragmentModule())
                .build();
    }

    /**
     * 获取FragmentModule
     */
    public FragmentModule getFragmentModule()
    {
        return new FragmentModule(getActivity());
    }

    /* 初始化注入 */
    public abstract void initInject();
    /* 绑定布局 */
    public abstract int getLayout();
    /* 初始化事件和数据 */
    public abstract void initEventAndData();

    private void init() {
        mContext = getActivity();
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage(getResources().getString(R.string.progressdialog_toast));
    }

    /**
     * 省去类型转换 将此方法写在基类Activity
     */
    protected <T extends View> T $(int id)
    {
        return (T) mView.findViewById(id);
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
    public void showProgressDialog(String msg) {
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }

    /**
     * 显示默认ProgressDialog
     */
    public void showDefaultProgressDialog() {
        mProgressDialog.setMessage(getResources().getString(R.string.progressdialog_toast));
        mProgressDialog.show();
    }

    /**
     * 隐藏ProgressDialog
     */
    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * 判断ProgressDialog是否为显示状态
     */
    public boolean progressDialogIsShowing() {
        if (mProgressDialog.isShowing()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 打印文本 长时间
     */
    public void showToast(String msg) {
        AppUtils.showToast(mContext, msg);
    }

    /**
     * 打印文本 短时间
     */
    public void showToast(int resId) {
        AppUtils.showToast(mContext, resId);
    }

    /**
     * 显示视图
     */
    public void showView(View view) {
        AppUtils.showView(view);
    }

    /**
     * 隐藏视图
     */
    public void hideViewGone(View view) {
        AppUtils.hideViewGone(view);
    }

    /**
     * 隐藏视图 保留位置
     */
    public void hideViewInvisible(View view) {
        AppUtils.hideViewInvisible(view);
    }

    /**
     * 判断视图是否显示
     */
    public boolean isShowView(View view) {
        return AppUtils.isShowView(view);
    }

    /**
     * 写入SharedPreferences数据
     */
    public void setStringSharedPreferences(String key, String value) {
        AppUtils.setStringSharedPreferences(mContext, key, value);
    }

    /**
     * 读取SharedPreferences数据
     */
    public String getStringSharedPreferences(String key, String defaultValue) {
        return AppUtils.getStringSharedPreferences(mContext, key, defaultValue);
    }

    /**
     * 判断是否为中文版
     */
    public boolean isZh() {
        return AppUtils.isZh(mContext);
    }

    /**
     * 检查是否存在SDCard
     */
    public boolean hasSdcard() {
        return AppUtils.hasSdcard();
    }

    /**
     * 验证是否存在可用网络
     */
    public boolean CheckNetworkState() {
        return AppUtils.CheckNetworkState(mContext);
    }

    /**
     * 验证网络状态 0 存在wifi网络， 1 存在2/3G网络，2无网络连接
     */
    public int currentNetwork() {
        return AppUtils.currentNetwork(mContext);
    }

    /**
     * 获取屏幕高度像素
     */
    public int getDisplayHeight() {
        return AppUtils.getDisplayHeight((Activity) mContext);
    }

    /**
     * 获取屏幕宽度像素
     */
    public int getDisplayWidth() {
        return AppUtils.getDisplayWidth((Activity) mContext);
    }

}
