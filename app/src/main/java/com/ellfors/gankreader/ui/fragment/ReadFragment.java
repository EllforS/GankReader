package com.ellfors.gankreader.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ellfors.gankreader.R;
import com.ellfors.gankreader.base.BaseFragment;
import com.ellfors.gankreader.ui.activity.MainActivity;
import com.ellfors.gankreader.ui.adapter.ViewPagerAdapter;
import com.ellfors.gankreader.utils.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ReadFragment extends BaseFragment
{
    public static final String TAG_ANDROID = "Android";
    public static final String TAG_IOS = "iOS";
    public static final String TAG_WEB = "前端";

    @BindView(R.id.hear_open_drawer)
    ImageView iv_open_drawer;
    @BindView(R.id.head_title)
    TextView tv_title;

    @BindView(R.id.read_tablayout)
    TabLayout tabLayout;
    @BindView(R.id.read_viewpager)
    ViewPager viewPager;

    private List<String> list;
    private ViewPagerAdapter adapter;

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
        L.i("触发initEventAndData()方法");

        /* Title */
        tv_title.setText(getResources().getString(R.string.read));
        /* TabLayout */
        list = new ArrayList<>();
        list.add(TAG_ANDROID);
        list.add(TAG_IOS);
        list.add(TAG_WEB);
        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(),list);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).select();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @OnClick(R.id.hear_open_drawer)
    void doOpenDrawer()
    {
        ((MainActivity) getActivity()).openDrawer();
    }

}
