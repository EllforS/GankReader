package com.ellfors.gankreader.ui.fragment;


import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ellfors.gankreader.BuildConfig;
import com.ellfors.gankreader.R;
import com.ellfors.gankreader.base.BaseFragment;
import com.ellfors.gankreader.ui.activity.MainActivity;
import com.ellfors.gankreader.utils.DataCleanManager;
import com.ellfors.gankreader.utils.ImageLoader;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingFragment extends BaseFragment
{
    @BindView(R.id.hear_open_drawer)
    ImageView iv_open_drawer;
    @BindView(R.id.head_title)
    TextView tv_title;

    @BindView(R.id.setting_cachesize)
    TextView tv_cache_size;
    @BindView(R.id.setting_clean_cache)
    LinearLayout ll_clean_cache;
    @BindView(R.id.setting_now_version)
    TextView tv_version;

    @Override
    public void initInject()
    {

    }

    @Override
    public int getLayout()
    {
        return R.layout.fragment_setting;
    }

    @Override
    public void initEventAndData()
    {
        /* Title */
        tv_title.setText(getResources().getString(R.string.setting));
        /* Cache Size */
        try
        {
            tv_cache_size.setText(DataCleanManager.getTotalCacheSize(getActivity()));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        /* Version */
        tv_version.setText(BuildConfig.VERSION_NAME);
    }

    @OnClick(R.id.hear_open_drawer)
    void doOpenDrawer()
    {
        ((MainActivity) getActivity()).openDrawer();
    }

    @OnClick(R.id.setting_clean_cache)
    void doCleanCache()
    {
        DataCleanManager.clearAllCache(getActivity());
        ImageLoader.cleanAll(getActivity());
        try
        {
            tv_cache_size.setText(DataCleanManager.getTotalCacheSize(getActivity()));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
