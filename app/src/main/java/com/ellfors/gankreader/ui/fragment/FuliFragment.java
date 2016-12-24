package com.ellfors.gankreader.ui.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ellfors.gankreader.R;
import com.ellfors.gankreader.base.BaseFragment;
import com.ellfors.gankreader.base.BaseRcvAdapter;
import com.ellfors.gankreader.model.FuliModel;
import com.ellfors.gankreader.presenter.contract.FuliContract;
import com.ellfors.gankreader.presenter.impl.FuliPresenterImpl;
import com.ellfors.gankreader.ui.activity.ImgDetailsActivity;
import com.ellfors.gankreader.ui.activity.MainActivity;
import com.ellfors.gankreader.ui.adapter.FuliAdapter;
import com.ellfors.gankreader.utils.L;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class FuliFragment extends BaseFragment implements FuliContract.View
{
    @BindView(R.id.hear_open_drawer)
    ImageView head_open_drawer;
    @BindView(R.id.head_title)
    TextView head_title;
    @BindView(R.id.fuli_swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.fuli_recycler)
    RecyclerView mRecyclerView;

    @Inject
    FuliPresenterImpl fuliPresenter;

    private FuliAdapter fuliAdapter;
    private List<FuliModel> fuli_list;
    private boolean isLoading = false;
    private int lastVisibleItem;

    @Override
    public void initInject()
    {
        getFragmentComponent().inject(this);
        fuliPresenter.attachView(this);
    }

    @Override
    public int getLayout()
    {
        return R.layout.fragment_fuli;
    }

    @Override
    public void initEventAndData()
    {
        /* Title */
        head_title.setText(getResources().getString(R.string.fuli));
        /* RecyclerView */
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        /* initData */
        mSwipeRefreshLayout.setRefreshing(true);
        fuliPresenter.getFuliList();
        /* SwipeRefresh */
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                fuliPresenter.getFuliList();
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
                        RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem == fuliAdapter.getItemCount() - 3)
                {
                    isLoading = true;
                    fuliPresenter.doLoadingList();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);

                GridLayoutManager gm = (GridLayoutManager) mRecyclerView.getLayoutManager();
                lastVisibleItem = gm.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if(fuliPresenter != null)
            fuliPresenter.detachView();
    }

    @OnClick(R.id.hear_open_drawer) void doOpenDrawer()
    {
        ((MainActivity) getActivity()).openDrawer();
    }

    @Override
    public void showList(List<FuliModel> list)
    {
        mSwipeRefreshLayout.setRefreshing(false);
        fuli_list = list;
        fuliAdapter = new FuliAdapter(getActivity(),fuli_list);
        mRecyclerView.setAdapter(fuliAdapter);
        setRcvItemClick();
    }

    @Override
    public void loadingList(List<FuliModel> list)
    {
        isLoading = false;
        fuli_list.addAll(list);
        fuliAdapter.notifyDataSetChanged();
        setRcvItemClick();
    }

    @Override
    public void showError(String msg)
    {
        L.e("获取福利列表 Error = " + msg);
    }

    /**
     * 设置RecyclerView Item监听
     */
    private void setRcvItemClick()
    {
        fuliAdapter.setOnItemClickListener(new BaseRcvAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                startActivity(new Intent(
                        getActivity(), ImgDetailsActivity.class).putExtra(
                        ImgDetailsActivity.PHOTO_URL,fuli_list.get(position).getUrl()));
            }
        });
    }
}
