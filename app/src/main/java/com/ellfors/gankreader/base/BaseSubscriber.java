package com.ellfors.gankreader.base;

import android.util.Log;

import com.ellfors.gankreader.app.MyApplication;
import com.ellfors.gankreader.http.config.RetrofitConfig;
import com.ellfors.gankreader.utils.AppUtils;

import org.reactivestreams.Subscription;

import java.net.SocketTimeoutException;

import io.reactivex.FlowableSubscriber;

/**
 * Rx Subscriber基类
 */
public abstract class BaseSubscriber<T> implements FlowableSubscriber<T>
{
    private T t;
    private Subscription mSubscription;

    public abstract void onSuccess(T t);

    public abstract void onFailed(Exception e);

    @Override
    public void onSubscribe(Subscription s)
    {
        mSubscription = s;
        if (!AppUtils.CheckNetworkState(MyApplication.getInstance().currentActivity()))
        {
            onError(new Exception(RetrofitConfig.NOT_INTERNET_MESSAGE));
            mSubscription.cancel();
            return;
        }
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(T t)
    {
        this.t = t;
        if (t != null)
            onSuccess(t);
    }

    @Override
    public void onError(Throwable t)
    {
        if (t instanceof SocketTimeoutException)
        {
            onFailed(new Exception(RetrofitConfig.NOT_INTERNET_MESSAGE));
        }
        else
        {
            Log.e(RetrofitConfig.HTTP_TAG, t.getMessage());
            onFailed(new Exception(""));
        }
        mSubscription.cancel();
    }

    @Override
    public void onComplete()
    {
        if (t == null)
            onSuccess(null);
        if (mSubscription != null)
            mSubscription.cancel();
    }
}