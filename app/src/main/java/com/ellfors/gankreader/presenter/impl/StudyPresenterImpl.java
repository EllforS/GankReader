package com.ellfors.gankreader.presenter.impl;

import com.ellfors.gankreader.base.BasePresenterImpl;
import com.ellfors.gankreader.base.BaseSubscriber;
import com.ellfors.gankreader.http.utils.RetrofitManager;
import com.ellfors.gankreader.http.utils.RxUtils;
import com.ellfors.gankreader.model.StudyModel;
import com.ellfors.gankreader.presenter.contract.StudyContract;

import java.util.List;

import javax.inject.Inject;

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
        manager
                .getGsonHttpApi()
                .getStudyList(tag, limit, page)
                .compose(RxUtils.handleResult())
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(new BaseSubscriber<List<StudyModel>>()
                {
                    @Override
                    public void onSuccess(List<StudyModel> studyModels)
                    {
                        if (mView != null)
                            mView.showList(studyModels);
                    }

                    @Override
                    public void onFailed(Exception e)
                    {
                        if (mView != null)
                            mView.showError(e.getMessage());
                    }
                });
    }

    @Override
    public void loadingStudyList(String tag)
    {
        page += 1;
        manager
                .getGsonHttpApi()
                .getStudyList(tag, limit, page)
                .compose(RxUtils.handleResult())
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(new BaseSubscriber<List<StudyModel>>()
                {
                    @Override
                    public void onSuccess(List<StudyModel> studyModels)
                    {
                        if (mView != null)
                            mView.loadingList(studyModels);
                    }

                    @Override
                    public void onFailed(Exception e)
                    {
                        if (mView != null)
                            mView.showError(e.getMessage());
                    }
                });
    }
}
