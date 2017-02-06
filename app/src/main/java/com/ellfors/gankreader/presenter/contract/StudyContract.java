package com.ellfors.gankreader.presenter.contract;


import com.ellfors.gankreader.base.BasePresenter;
import com.ellfors.gankreader.base.BaseView;
import com.ellfors.gankreader.model.StudyModel;

import java.util.List;

public class StudyContract
{
    public interface View extends BaseView
    {
        void showList(List<StudyModel> list);
        void loadingList(List<StudyModel> list);
    }

    public interface Presenter extends BasePresenter<View>
    {
        void getStudyList(String tag);
        void loadingStudyList(String tag);
    }
}
