package com.ellfors.gankreader.http.utils;

import android.content.Context;
import android.util.Log;

import com.ellfors.gankreader.http.config.RetrofitConfig;
import com.ellfors.gankreader.utils.AppUtils;

import rx.Subscriber;


/**
 * 自定义Subscriber
 *      判断ProgressDialog cancel、判断网络、自动显示与隐藏ProgressDialog
 */
public abstract class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener
{
    private Context mContext;

    private ProgressDialogHandler mProgressDialogHandler;

    private boolean isHasNet;

    public ProgressSubscriber(Context context)
    {
        this.mContext = context;
        mProgressDialogHandler = new ProgressDialogHandler(context,this,true);
    }

    private void showProgressDialog()
    {
        if(mProgressDialogHandler != null)
        {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog()
    {
        if (mProgressDialogHandler != null)
        {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    /**
     * ProgressDialog cancel 取消网络请求
     */
    @Override
    public void onProgressCancel()
    {
        if (!this.isUnsubscribed())
        {
            this.unsubscribe();
            Log.e(RetrofitConfig.TAG,RetrofitConfig.CANCEL_MESSAGE);
        }
    }

    @Override
    public void onStart()
    {
        super.onStart();

        if(AppUtils.CheckNetworkState(mContext))
        {
            showProgressDialog();
            isHasNet = true;
        }
        else
        {
            AppUtils.showToast(mContext,RetrofitConfig.NOT_INTERNET_MESSAGE);
            isHasNet = false;
        }
    }

    @Override
    public void onCompleted()
    {
        dismissProgressDialog();
        Log.e(RetrofitConfig.TAG,RetrofitConfig.COMPLETE_MESSAGE);
    }

    @Override
    public void onError(Throwable e)
    {
        dismissProgressDialog();

        if(!isHasNet)
        {
            AppUtils.showToast(mContext,RetrofitConfig.NOT_INTERNET_MESSAGE);
            return;
        }

        onFailed(e);
    }

    @Override
    public void onNext(T t)
    {
        onSuccess(t);
    }

    public abstract void onSuccess(T t);
    public abstract void onFailed(Throwable e);
}
