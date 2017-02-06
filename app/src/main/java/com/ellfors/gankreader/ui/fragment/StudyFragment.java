package com.ellfors.gankreader.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ellfors.gankreader.R;
import com.ellfors.gankreader.base.BaseFragment;
import com.ellfors.gankreader.base.BaseRcvAdapter;
import com.ellfors.gankreader.model.StudyModel;
import com.ellfors.gankreader.presenter.contract.StudyContract;
import com.ellfors.gankreader.presenter.impl.StudyPresenterImpl;
import com.ellfors.gankreader.ui.activity.WebViewActivity;
import com.ellfors.gankreader.ui.adapter.StudyAdapter;
import com.ellfors.gankreader.utils.L;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class StudyFragment extends BaseFragment implements StudyContract.View
{
    public static final String STUDY_MODEL = "study_model";
    private String tag;

    @Inject
    StudyPresenterImpl studyPresenter;

    @BindView(R.id.fragment_study_swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.fragment_study_recycler)
    RecyclerView mRecyclerView;

    private StudyAdapter studyAdapter;
    private List<StudyModel> study_list;
    private boolean isLoading = false;
    private int lastVisibleItem;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        tag = getArguments() != null ? getArguments().getString("tag") : null;
    }

    public static StudyFragment newInstance(String tag)
    {
        final StudyFragment f = new StudyFragment();
        final Bundle bundle = new Bundle();
        bundle.putString("tag", tag);
        f.setArguments(bundle);

        return f;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if(studyPresenter != null)
            studyPresenter.detachView();
    }

    @Override
    public void initInject()
    {
        getFragmentComponent().inject(this);
        studyPresenter.attachView(this);
    }

    @Override
    public int getLayout()
    {
        return R.layout.fragment_study;
    }

    @Override
    public void initEventAndData()
    {
        /* RecyclerView */
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        /* initData */
        mSwipeRefreshLayout.setRefreshing(true);
        studyPresenter.getStudyList(tag);
        /* SwipeRefresh */
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                studyPresenter.getStudyList(tag);
            }
        });
        /* Loading List */
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);

                if (!isLoading && !mSwipeRefreshLayout.isRefreshing() && newState ==
                        RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem == studyAdapter.getItemCount() - 1)
                {
                    isLoading = true;
                    studyPresenter.loadingStudyList(tag);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager gm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                lastVisibleItem = gm.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    public void showList(List<StudyModel> list)
    {
        mSwipeRefreshLayout.setRefreshing(false);
        study_list = list;
        studyAdapter = new StudyAdapter(tag,getActivity(),study_list);
        mRecyclerView.setAdapter(studyAdapter);
        setRcvItemClick();
    }

    @Override
    public void loadingList(List<StudyModel> list)
    {
        isLoading = false;
        study_list.addAll(list);
        studyAdapter.notifyDataSetChanged();
        setRcvItemClick();
    }

    @Override
    public void showError(String msg)
    {
        L.e("获取" + tag + "列表Error = " + msg);
    }

    /**
     * 设置RecyclerView Item监听
     */
    private void setRcvItemClick()
    {
        studyAdapter.setOnItemClickListener(new BaseRcvAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(STUDY_MODEL,study_list.get(position));
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });
    }
}
