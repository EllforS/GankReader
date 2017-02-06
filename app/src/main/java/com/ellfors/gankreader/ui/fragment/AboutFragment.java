package com.ellfors.gankreader.ui.fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.ellfors.gankreader.R;
import com.ellfors.gankreader.base.BaseFragment;

public class AboutFragment extends BaseFragment
{
    @Override
    public void initInject()
    {

    }

    @Override
    public int getLayout()
    {
        return R.layout.fragment_about;
    }

    @Override
    public void initEventAndData()
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21)
        {
            View decorView = getActivity().getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
