package com.ellfors.gankreader.presenter.contract;


import com.ellfors.gankreader.base.BasePresenter;
import com.ellfors.gankreader.base.BaseView;
import com.ellfors.gankreader.model.IOSModel;

import java.util.List;

public class IOSContract
{
    public interface View extends BaseView
    {
        void showList(List<IOSModel> list);
        void loadingList(List<IOSModel> list);
    }

    public interface Presenter extends BasePresenter<View>
    {
        void getIOSList();
        void loadingIOSList();
    }
}
