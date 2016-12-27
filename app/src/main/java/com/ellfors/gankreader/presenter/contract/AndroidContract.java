package com.ellfors.gankreader.presenter.contract;


import com.ellfors.gankreader.base.BasePresenter;
import com.ellfors.gankreader.base.BaseView;
import com.ellfors.gankreader.model.AndroidModel;

import java.util.List;

public class AndroidContract
{
    public interface View extends BaseView
    {
        void showList(List<AndroidModel> list);
        void loadingList(List<AndroidModel> list);
    }

    public interface Presenter extends BasePresenter<View>
    {
        void getAndroidList();
        void loadingAndroidList();
    }
}
