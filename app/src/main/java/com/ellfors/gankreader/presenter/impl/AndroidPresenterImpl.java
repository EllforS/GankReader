package com.ellfors.gankreader.presenter.impl;

import com.ellfors.gankreader.base.BasePresenterImpl;
import com.ellfors.gankreader.http.utils.RetrofitManager;
import com.ellfors.gankreader.http.utils.RxUtils;
import com.ellfors.gankreader.http.utils.SimpleSubscriber;
import com.ellfors.gankreader.model.AndroidModel;
import com.ellfors.gankreader.presenter.contract.AndroidContract;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

public class AndroidPresenterImpl extends BasePresenterImpl<AndroidContract.View> implements AndroidContract.Presenter
{
    private RetrofitManager manager;
    private int limit = 20;
    private int page = 1;

    @Inject
    public AndroidPresenterImpl(RetrofitManager manager)
    {
        this.manager = manager;
    }

    @Override
    public void getAndroidList()
    {
        page = 1;
        Subscription sub = manager
                .getGsonHttpApi()
                .getAndroidList(limit,page)
                .compose(RxUtils.<List<AndroidModel>>handleResult())
                .compose(RxUtils.<List<AndroidModel>>rxSchedulerHelper())
                .subscribe(new SimpleSubscriber<List<AndroidModel>>()
                {
                    @Override
                    public void onError(Throwable e)
                    {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<AndroidModel> androidModels)
                    {
                        mView.showList(androidModels);
                    }
                });
        addSubscribe(sub);
    }

    @Override
    public void loadingAndroidList()
    {
        page += 1;
        Subscription sub = manager
                .getGsonHttpApi()
                .getAndroidList(limit,page)
                .compose(RxUtils.<List<AndroidModel>>handleResult())
                .compose(RxUtils.<List<AndroidModel>>rxSchedulerHelper())
                .subscribe(new SimpleSubscriber<List<AndroidModel>>()
                {
                    @Override
                    public void onError(Throwable e)
                    {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<AndroidModel> androidModels)
                    {
                        mView.loadingList(androidModels);
                    }
                });
        addSubscribe(sub);
    }
}
