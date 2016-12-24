package com.ellfors.gankreader.presenter.contract;

import com.ellfors.gankreader.base.BasePresenter;
import com.ellfors.gankreader.base.BaseView;
import com.ellfors.gankreader.model.FuliModel;

import java.util.List;

public class FuliContract
{
    public interface View extends BaseView
    {
        void showList(List<FuliModel> list);
        void loadingList(List<FuliModel> list);
    }

    public interface Presenter extends BasePresenter<View>
    {
        void getFuliList();
        void doLoadingList();
    }
}
