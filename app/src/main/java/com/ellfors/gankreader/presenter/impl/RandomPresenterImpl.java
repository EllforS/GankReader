package com.ellfors.gankreader.presenter.impl;


import com.ellfors.gankreader.base.BasePresenterImpl;
import com.ellfors.gankreader.base.BaseSubscriber;
import com.ellfors.gankreader.http.utils.RetrofitManager;
import com.ellfors.gankreader.http.utils.RxUtils;
import com.ellfors.gankreader.model.RandomModel;
import com.ellfors.gankreader.presenter.contract.RandomContract;

import java.util.List;

import javax.inject.Inject;

public class RandomPresenterImpl extends BasePresenterImpl<RandomContract.View> implements RandomContract.Presenter
{
    private RetrofitManager manager;

    @Inject
    public RandomPresenterImpl(RetrofitManager manager)
    {
        this.manager = manager;
    }

    @Override
    public void getRandomImg()
    {
        manager
                .getGsonHttpApi()
                .getRandomFuli(1)
                .compose(RxUtils.handleResult())
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(new BaseSubscriber<List<RandomModel>>()
                {
                    @Override
                    public void onSuccess(List<RandomModel> randomModels)
                    {
                        if (mView != null)
                            mView.setImage(randomModels.size() > 0 ? randomModels.get(0) : new RandomModel());
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
