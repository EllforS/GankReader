package com.ellfors.gankreader.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ellfors.gankreader.ui.fragment.MainFragment;
import com.ellfors.gankreader.ui.fragment.StudyFragment;

import java.util.List;

public class MainVpAdapter extends FragmentStatePagerAdapter
{
    private List<String> list;

    public MainVpAdapter(FragmentManager fm, List<String> list)
    {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position)
    {
        String tag = list.get(position);
        return StudyFragment.newInstance(tag);
    }

    @Override
    public int getCount()
    {
        return list == null ? 0 : list.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        switch (position)
        {
            case 0:
                return MainFragment.TAG_ANDROID;
            case 1:
                return MainFragment.TAG_IOS;
            case 2:
                return MainFragment.TAG_WEB;
            default:
                return null;
        }
    }
}
