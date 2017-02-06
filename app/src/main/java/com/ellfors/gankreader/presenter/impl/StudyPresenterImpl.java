package com.ellfors.gankreader.presenter.impl;

import com.ellfors.gankreader.base.BasePresenterImpl;
import com.ellfors.gankreader.http.utils.RetrofitManager;
import com.ellfors.gankreader.http.utils.RxUtils;
import com.ellfors.gankreader.http.utils.SimpleSubscriber;
import com.ellfors.gankreader.model.StudyModel;
import com.ellfors.gankreader.presenter.contract.StudyContract;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

public class StudyPresenterImpl extends BasePresenterImpl<StudyContract.View> implements StudyContract.Presenter
{
    private RetrofitManager manager;
    private int limit = 20;
    private int page = 1;

    @Inject
    public StudyPresenterImpl(RetrofitManager manager)
    {
        this.manager = manager;
    }

    @Override
    public void getStudyList(String tag)
    {
        page = 1;
        Subscription sub = manager
                .getGsonHttpApi()
                .getStudyList(tag,limit,page)
                .compose(RxUtils.<List<StudyModel>>handleResult())
                .compose(RxUtils.<List<StudyModel>>rxSchedulerHelper())
                .subscribe(new SimpleSubscriber<List<StudyModel>>()
                {
                    @Override
                    public void onError(Throwable e)
                    {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<StudyModel> androidModels)
                    {
                        mView.showList(androidModels);
                    }
                });
        addSubscribe(sub);
    }

    @Override
    public void loadingStudyList(String tag)
    {
        page += 1;
        Subscription sub = manager
                .getGsonHttpApi()
                .getStudyList(tag,limit,page)
                .compose(RxUtils.<List<StudyModel>>handleResult())
                .compose(RxUtils.<List<StudyModel>>rxSchedulerHelper())
                .subscribe(new SimpleSubscriber<List<StudyModel>>()
                {
                    @Override
                    public void onError(Throwable e)
                    {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<StudyModel> androidModels)
                    {
                        mView.loadingList(androidModels);
                    }
                });
        addSubscribe(sub);
    }
}
