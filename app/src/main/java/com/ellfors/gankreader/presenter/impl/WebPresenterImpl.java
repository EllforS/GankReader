package com.ellfors.gankreader.presenter.impl;


import com.ellfors.gankreader.base.BasePresenterImpl;
import com.ellfors.gankreader.http.utils.RetrofitManager;
import com.ellfors.gankreader.http.utils.RxUtils;
import com.ellfors.gankreader.http.utils.SimpleSubscriber;
import com.ellfors.gankreader.model.WebModel;
import com.ellfors.gankreader.presenter.contract.WebContract;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

public class WebPresenterImpl extends BasePresenterImpl<WebContract.View> implements WebContract.Presenter
{
    private RetrofitManager manager;
    private int limit = 20;
    private int page = 1;

    @Inject
    public WebPresenterImpl(RetrofitManager manager)
    {
        this.manager = manager;
    }

    @Override
    public void getWebList()
    {
        page = 1;
        Subscription sub = manager
                .getGsonHttpApi()
                .getWebList(limit,page)
                .compose(RxUtils.<List<WebModel>>handleResult())
                .compose(RxUtils.<List<WebModel>>rxSchedulerHelper())
                .subscribe(new SimpleSubscriber<List<WebModel>>()
                {
                    @Override
                    public void onError(Throwable e)
                    {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<WebModel> webModels)
                    {
                        mView.showList(webModels);
                    }
                });
        addSubscribe(sub);
    }

    @Override
    public void loadingWebList()
    {
        page += 1;
        Subscription sub = manager
                .getGsonHttpApi()
                .getWebList(limit,page)
                .compose(RxUtils.<List<WebModel>>handleResult())
                .compose(RxUtils.<List<WebModel>>rxSchedulerHelper())
                .subscribe(new SimpleSubscriber<List<WebModel>>()
                {
                    @Override
                    public void onError(Throwable e)
                    {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<WebModel> webModels)
                    {
                        mView.loadingList(webModels);
                    }
                });
        addSubscribe(sub);
    }
}
