package com.ellfors.gankreader.http.utils.progress;

import android.content.Context;
import android.util.Log;

import com.ellfors.gankreader.http.config.RetrofitConfig;
import com.ellfors.gankreader.utils.AppUtils;

import org.reactivestreams.Subscription;

import io.reactivex.FlowableSubscriber;


/**
 * 自定义Subscriber
 * 判断ProgressDialog cancel、判断网络、自动显示与隐藏ProgressDialog
 */
public abstract class ProgressSubscriber<T> implements FlowableSubscriber<T>, ProgressCancelListener
{
    private static final String TAG = "retrofit_log_tag";

    private Context mContext;
    private ProgressDialogHandler mProgressDialogHandler;
    private boolean isHasNet;
    private Subscription mSubscription;

    public ProgressSubscriber(Context context)
    {
        this.mContext = context;
        mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
    }

    private void showProgressDialog()
    {
        if (mProgressDialogHandler != null)
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
        mSubscription.cancel();
        Log.e(TAG, "取消网络请求");
    }

    @Override
    public void onSubscribe(Subscription s)
    {
        mSubscription = s;
        if (AppUtils.CheckNetworkState(mContext))
        {
            showProgressDialog();
            isHasNet = true;
        }
        else
        {
            AppUtils.showToast(mContext, RetrofitConfig.NOT_INTERNET_MESSAGE);
            isHasNet = false;
        }
        //这个方法为向观察者发送的事件数量，不设置则观察者接受不到数据
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onComplete()
    {
        dismissProgressDialog();
        Log.e(TAG, "Complete");
    }

    @Override
    public void onError(Throwable e)
    {
        dismissProgressDialog();
        if (!isHasNet)
        {
            AppUtils.showToast(mContext, RetrofitConfig.NOT_INTERNET_MESSAGE);
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
