package com.ellfors.gankreader.ui.fragment;

import android.widget.TextView;

import com.ellfors.gankreader.R;
import com.ellfors.gankreader.base.BaseFragment;

import butterknife.BindView;

public class LikeFragment extends BaseFragment
{
    @BindView(R.id.head_title)
    TextView tv_title;

    @Override
    public void initInject()
    {
        getFragmentComponent().inject(this);
    }

    @Override
    public int getLayout()
    {
        return R.layout.fragment_like;
    }

    @Override
    public void initEventAndData()
    {
        tv_title.setText(R.string.like);
    }
}
