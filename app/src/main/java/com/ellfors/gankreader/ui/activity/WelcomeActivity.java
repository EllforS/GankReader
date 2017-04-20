package com.ellfors.gankreader.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.widget.TextView;

import com.ellfors.gankreader.R;
import com.ellfors.gankreader.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class WelcomeActivity extends BaseActivity
{
    private static final int TIME = 5100;

    @BindView(R.id.tv_welcome_dismiss) TextView tv_dismiss;

    private Handler handler = new Handler();

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
        handler.postDelayed(runnable,TIME);
    }

    @OnClick(R.id.tv_welcome_dismiss)
    void onDismiss()
    {
        startActivity(new Intent(mContext,MainActivity.class));
        handler.removeCallbacks(runnable);
        finish();
    }

    private Runnable runnable = () ->
    {
        startActivity(new Intent(mContext,MainActivity.class));
        finish();
    };
}
