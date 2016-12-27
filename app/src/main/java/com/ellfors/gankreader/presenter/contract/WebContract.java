package com.ellfors.gankreader.presenter.contract;


import com.ellfors.gankreader.base.BasePresenter;
import com.ellfors.gankreader.base.BaseView;
import com.ellfors.gankreader.model.WebModel;

import java.util.List;

public class WebContract
{
    public interface View extends BaseView
    {
        void showList(List<WebModel> list);
        void loadingList(List<WebModel> list);
    }

    public interface Presenter extends BasePresenter<View>
    {
        void getWebList();
        void loadingWebList();
    }
}
