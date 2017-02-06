package com.ellfors.gankreader.presenter.contract;

import com.ellfors.gankreader.base.BasePresenter;
import com.ellfors.gankreader.base.BaseView;
import com.ellfors.gankreader.model.RandomModel;

public class RandomContract
{
    public interface View extends BaseView
    {
        void setImage(RandomModel model);
    }

    public interface Presenter extends BasePresenter<View>
    {
        void getRandomImg();
    }
}
