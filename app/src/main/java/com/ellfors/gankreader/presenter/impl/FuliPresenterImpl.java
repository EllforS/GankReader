package com.ellfors.gankreader.presenter.impl;

import com.ellfors.gankreader.base.BasePresenterImpl;
import com.ellfors.gankreader.base.BaseSubscriber;
import com.ellfors.gankreader.http.utils.RetrofitManager;
import com.ellfors.gankreader.http.utils.RxUtils;
import com.ellfors.gankreader.model.FuliModel;
import com.ellfors.gankreader.presenter.contract.FuliContract;

import java.util.List;

import javax.inject.Inject;

public class FuliPresenterImpl extends BasePresenterImpl<FuliContract.View> implements FuliContract.Presenter
{
    private RetrofitManager manager;
    private int limit = 20;
    private int page = 1;

    @Inject
    public FuliPresenterImpl(RetrofitManager manager)
    {
        this.manager = manager;
    }

    @Override
    public void getFuliList()
    {
        page = 1;
        manager
                .getGsonHttpApi()
                .getFuLi(limit, page)
                .compose(RxUtils.handleResult())
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(new BaseSubscriber<List<FuliModel>>()
                {
                    @Override
                    public void onSuccess(List<FuliModel> fuliModels)
                    {
                        if (mView != null)
                            mView.showList(fuliModels);
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
    public void doLoadingList()
    {
        page += 1;
        manager
                .getGsonHttpApi()
                .getFuLi(limit, page)
                .compose(RxUtils.handleResult())
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(new BaseSubscriber<List<FuliModel>>()
                {
                    @Override
                    public void onSuccess(List<FuliModel> fuliModels)
                    {
                        if (mView != null)
                            mView.loadingList(fuliModels);
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
