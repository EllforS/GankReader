package com.ellfors.gankreader.presenter.impl;


import com.ellfors.gankreader.base.BasePresenterImpl;
import com.ellfors.gankreader.http.utils.RetrofitManager;
import com.ellfors.gankreader.http.utils.RxUtils;
import com.ellfors.gankreader.http.utils.SimpleSubscriber;
import com.ellfors.gankreader.model.RandomModel;
import com.ellfors.gankreader.presenter.contract.RandomContract;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

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
        Subscription sub = manager
                .getGsonHttpApi()
                .getRandomFuli(1)
                .compose(RxUtils.<List<RandomModel>>handleResult())
                .compose(RxUtils.<List<RandomModel>>rxSchedulerHelper())
                .subscribe(new SimpleSubscriber<List<RandomModel>>()
                {
                    @Override
                    public void onError(Throwable e)
                    {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<RandomModel> randomModel)
                    {
                        mView.setImage(randomModel.get(0));
                    }
                });
        addSubscribe(sub);
    }
}
