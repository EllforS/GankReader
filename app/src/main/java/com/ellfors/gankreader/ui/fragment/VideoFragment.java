package com.ellfors.gankreader.ui.fragment;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ellfors.gankreader.R;
import com.ellfors.gankreader.base.BaseFragment;
import com.ellfors.gankreader.model.VideoModel;
import com.ellfors.gankreader.presenter.contract.VideoContract;
import com.ellfors.gankreader.presenter.impl.VideoPresenterImpl;
import com.ellfors.gankreader.ui.activity.MainActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class VideoFragment extends BaseFragment implements VideoContract.View
{
    @BindView(R.id.hear_open_drawer)
    ImageView iv_open_drawer;
    @BindView(R.id.head_title)
    TextView tv_title;
    @BindView(R.id.video_swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.video_recycler)
    RecyclerView mRecyclerView;

    @Inject
    VideoPresenterImpl videoPresenter;

    @Override
    public void initInject()
    {
        getFragmentComponent().inject(this);
        videoPresenter.attachView(this);
    }

    @Override
    public int getLayout()
    {
        return R.layout.fragment_video;
    }

    @Override
    public void initEventAndData()
    {
        /* Title */
        tv_title.setText(getResources().getString(R.string.video));
        /* SwipeRefreshLayout */
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        /* RecyclerView */
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        /* initData */
        mSwipeRefreshLayout.setRefreshing(true);
        videoPresenter.getVideoList();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if(videoPresenter != null)
            videoPresenter.detachView();
    }

    @OnClick(R.id.hear_open_drawer)
    void doOpenDrawer()
    {
        ((MainActivity) getActivity()).openDrawer();
    }

    @Override
    public void showList(List<VideoModel> list)
    {

    }

    @Override
    public void loadingList(List<VideoModel> list)
    {

    }

    @Override
    public void showError(String msg)
    {

    }
}
