package com.ellfors.gankreader.ui.fragment;


import android.widget.ImageView;
import android.widget.TextView;

import com.ellfors.gankreader.R;
import com.ellfors.gankreader.base.BaseFragment;
import com.ellfors.gankreader.ui.activity.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ReadFragment extends BaseFragment
{
    @BindView(R.id.hear_open_drawer)
    ImageView iv_open_drawer;
    @BindView(R.id.head_title)
    TextView tv_title;

    @Override
    public void initInject()
    {

    }

    @Override
    public int getLayout()
    {
        return R.layout.fragment_read;
    }

    @Override
    public void initEventAndData()
    {
        tv_title.setText(getResources().getString(R.string.read));
    }

    @OnClick(R.id.hear_open_drawer)
    void doOpenDrawer()
    {
        ((MainActivity) getActivity()).openDrawer();
    }

}
