package com.ellfors.gankreader.presenter.impl;


import com.ellfors.gankreader.base.BasePresenterImpl;
import com.ellfors.gankreader.http.utils.RetrofitManager;
import com.ellfors.gankreader.http.utils.RxUtils;
import com.ellfors.gankreader.http.utils.SimpleSubscriber;
import com.ellfors.gankreader.model.IOSModel;
import com.ellfors.gankreader.presenter.contract.IOSContract;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

public class IOSPresenterImpl extends BasePresenterImpl<IOSContract.View> implements IOSContract.Presenter
{
    private RetrofitManager manager;
    private int limit = 20;
    private int page = 1;

    @Inject
    public IOSPresenterImpl(RetrofitManager manager)
    {
        this.manager = manager;
    }

    @Override
    public void getIOSList()
    {
        page = 1;
        Subscription sub = manager
                .getGsonHttpApi()
                .getIOSList(limit,page)
                .compose(RxUtils.<List<IOSModel>>handleResult())
                .compose(RxUtils.<List<IOSModel>>rxSchedulerHelper())
                .subscribe(new SimpleSubscriber<List<IOSModel>>()
                {
                    @Override
                    public void onError(Throwable e)
                    {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<IOSModel> iosModels)
                    {
                        mView.showList(iosModels);
                    }
                });
        addSubscribe(sub);
    }

    @Override
    public void loadingIOSList()
    {
        page += 1;
        Subscription sub = manager
                .getGsonHttpApi()
                .getIOSList(limit,page)
                .compose(RxUtils.<List<IOSModel>>handleResult())
                .compose(RxUtils.<List<IOSModel>>rxSchedulerHelper())
                .subscribe(new SimpleSubscriber<List<IOSModel>>()
                {
                    @Override
                    public void onError(Throwable e)
                    {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<IOSModel> iosModels)
                    {
                        mView.loadingList(iosModels);
                    }
                });
        addSubscribe(sub);
    }
}
