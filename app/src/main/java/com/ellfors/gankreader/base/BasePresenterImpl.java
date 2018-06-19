package com.ellfors.gankreader.base;

/**
 * 基于Rxjava的PresenterImpl基类
 */
public class BasePresenterImpl<T extends BaseView> implements BasePresenter<T>
{
    public T mView;

    @Override
    public void attachView(T view)
    {
        this.mView = view;
    }

    @Override
    public void detachView()
    {
        this.mView = null;
    }
}
