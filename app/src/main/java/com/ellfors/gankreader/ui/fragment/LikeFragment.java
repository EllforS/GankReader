package com.ellfors.gankreader.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ellfors.gankreader.R;
import com.ellfors.gankreader.base.BaseFragment;
import com.ellfors.gankreader.base.BaseRcvAdapter;
import com.ellfors.gankreader.model.LiteModel;
import com.ellfors.gankreader.model.StudyModel;
import com.ellfors.gankreader.ui.activity.MainActivity;
import com.ellfors.gankreader.ui.activity.WebViewActivity;
import com.ellfors.gankreader.ui.adapter.LikeAdapter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LikeFragment extends BaseFragment
{
    @BindView(R.id.hear_open_drawer)
    ImageView iv_open_drawer;
    @BindView(R.id.head_title)
    TextView tv_title;

    @BindView(R.id.like_recycler)
    RecyclerView mRecyclerView;

    private LikeAdapter adapter;

    @Override
    public void initInject()
    {

    }

    @Override
    public int getLayout()
    {
        return R.layout.fragment_like;
    }

    @Override
    public void initEventAndData()
    {
        /* Title */
        tv_title.setText(R.string.like);
        /* RecyclerView */
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onResume()
    {
        super.onResume();
        /* 初始化 */
        List<LiteModel> list = DataSupport.findAll(LiteModel.class);
        final List<StudyModel> study_list = new ArrayList<>();
        for(int i = 0 ; i < list.size() ; i++)
        {
            StudyModel model = new StudyModel();
            model.set_id(list.get(i).getServer_id());
            model.setUrl(list.get(i).getUrl());
            model.setWho(list.get(i).getAuthor());
            model.setPublishedAt(list.get(i).getTime());
            model.setDesc(list.get(i).getTitle());
            study_list.add(model);
        }
        adapter = new LikeAdapter(getActivity(),study_list);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseRcvAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(StudyFragment.STUDY_MODEL,study_list.get(position));
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });
    }

    @OnClick(R.id.hear_open_drawer)
    void doOpenDrawer()
    {
        ((MainActivity) getActivity()).openDrawer();
    }
}
