package com.ellfors.gankreader.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ellfors.gankreader.R;
import com.ellfors.gankreader.base.BaseFragment;
import com.ellfors.gankreader.ui.activity.MainActivity;
import com.ellfors.gankreader.ui.adapter.MainVpAdapter;
import com.ellfors.gankreader.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainFragment extends BaseFragment
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
    private MainVpAdapter adapter;

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
        /* Title */
        tv_title.setText(getResources().getString(R.string.read));
        /* 为TabLayout添加分割线 */
        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.layout_divider_vertical));
        linearLayout.setDividerPadding(DensityUtils.dp2px(getActivity(), 15));
        /* 装载ViewPager */
        list = new ArrayList<>();
        list.add(TAG_ANDROID);
        list.add(TAG_IOS);
        list.add(TAG_WEB);
        adapter = new MainVpAdapter(getActivity().getSupportFragmentManager(), list);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
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
