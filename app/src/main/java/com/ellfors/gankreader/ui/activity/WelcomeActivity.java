package com.ellfors.gankreader.ui.activity;

import android.content.Intent;
import android.os.Handler;

import com.ellfors.gankreader.R;
import com.ellfors.gankreader.base.BaseActivity;

public class WelcomeActivity extends BaseActivity
{
    private static final int TIME = 5100;

    @Override
    public void initInject()
    {

    }

    @Override
    public int getLayout()
    {
        return R.layout.activity_welcome;
    }

    @Override
    public void initEventAndData()
    {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                startActivity(new Intent(mContext,MainActivity.class));
                finish();
            }
        },TIME);
    }
}
