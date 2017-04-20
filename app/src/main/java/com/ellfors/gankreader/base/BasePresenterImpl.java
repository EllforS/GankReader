package com.ellfors.gankreader.base;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * 基于Rxjava的PresenterImpl基类
 */
public class BasePresenterImpl<T extends BaseView> implements BasePresenter<T>
{
    public CompositeSubscription subscriptions;
    public T mView;

    /* 绑定SubScriber */
    public void addSubscribe(Subscription sub)
    {
        if (subscriptions == null)
        {
            subscriptions = new CompositeSubscription();
        }
        subscriptions.add(sub);
    }

    /* 取消绑定 */
    public void unSubscribe()
    {
        if (subscriptions != null)
            subscriptions.unsubscribe();
    }

    @Override
    public void attachView(T view)
    {
        this.mView = view;
    }

    @Override
    public void detachView()
    {
        this.mView = null;
        unSubscribe();
    }
}
