package com.ellfors.gankreader.presenter.contract;


import com.ellfors.gankreader.base.BasePresenter;
import com.ellfors.gankreader.base.BaseView;
import com.ellfors.gankreader.model.VideoModel;

import java.util.List;

public class VideoContract
{
    public interface View extends BaseView
    {
        void showList(List<VideoModel> list);
        void loadingList(List<VideoModel> list);
    }

    public interface Presenter extends BasePresenter<View>
    {
        void getVideoList();
        void doLoadingList();
    }
}
