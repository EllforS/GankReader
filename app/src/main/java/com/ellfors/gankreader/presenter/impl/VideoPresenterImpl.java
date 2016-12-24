package com.ellfors.gankreader.presenter.impl;

import com.ellfors.gankreader.base.BasePresenterImpl;
import com.ellfors.gankreader.http.utils.RetrofitManager;
import com.ellfors.gankreader.http.utils.RxUtils;
import com.ellfors.gankreader.http.utils.SimpleSubscriber;
import com.ellfors.gankreader.model.VideoModel;
import com.ellfors.gankreader.presenter.contract.VideoContract;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

public class VideoPresenterImpl extends BasePresenterImpl<VideoContract.View> implements VideoContract.Presenter
{
    private RetrofitManager manager;
    private int limit = 20;
    private int page = 1;

    @Inject
    public VideoPresenterImpl(RetrofitManager manager)
    {
        this.manager = manager;
    }

    @Override
    public void getVideoList()
    {
        page = 1;
        Subscription sub = manager
                .getGsonHttpApi()
                .getVideoList(limit,page)
                .compose(RxUtils.<List<VideoModel>>handleResult())
                .compose(RxUtils.<List<VideoModel>>rxSchedulerHelper())
                .subscribe(new SimpleSubscriber<List<VideoModel>>()
                {
                    @Override
                    public void onError(Throwable e)
                    {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<VideoModel> videoModels)
                    {
                        mView.showList(videoModels);
                    }
                });
        addSubscribe(sub);
    }

    @Override
    public void doLoadingList()
    {
        page += 1;
        Subscription sub = manager
                .getGsonHttpApi()
                .getVideoList(limit,page)
                .compose(RxUtils.<List<VideoModel>>handleResult())
                .compose(RxUtils.<List<VideoModel>>rxSchedulerHelper())
                .subscribe(new SimpleSubscriber<List<VideoModel>>()
                {
                    @Override
                    public void onError(Throwable e)
                    {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<VideoModel> videoModels)
                    {
                        mView.loadingList(videoModels);
                    }
                });
        addSubscribe(sub);
    }
}
