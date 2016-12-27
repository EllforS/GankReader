package com.ellfors.gankreader.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ellfors.gankreader.R;
import com.ellfors.gankreader.base.BaseActivity;
import com.ellfors.gankreader.model.VideoModel;
import com.ellfors.gankreader.ui.fragment.VideoFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class VideoWebViewActivity extends BaseActivity
{
    @BindView(R.id.head_go_back)
    ImageView iv_goback;
    @BindView(R.id.head_title)
    TextView tv_title;
    @BindView(R.id.video_webview)
    WebView mWebView;
    @BindView(R.id.video_webview_progressbar)
    ProgressBar mBar;

    private WebSettings webSettings;

    @Override
    public void initInject()
    {

    }

    @Override
    public int getLayout()
    {
        return R.layout.activity_video_webview;
    }

    @Override
    public void initEventAndData()
    {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            VideoModel model = (VideoModel) bundle.getSerializable(VideoFragment.VIDEO_MODEL);

            tv_title.setText(model.getDesc());
            mWebView.loadUrl(model.getUrl());

            setMyWebViewClient();
            setMyWebViewChromeClient();
            setMyWebSetting();
        }
    }

    /**
     * 返回
     */
    @OnClick(R.id.head_go_back) void doOnGoBack()
    {
        finish();
    }

    /**
     * 设置WebViewClient
     */
    private void setMyWebViewClient()
    {
        mWebView.setWebViewClient(new WebViewClient()
        {
            /*
                可以拦截URL并处理
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                return false;
            }

            /*
                开始加载网页时处理 如：显示"加载提示" 的加载对话框
             */
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                super.onPageStarted(view, url, favicon);

                if(mBar != null)
                    mBar.setVisibility(View.VISIBLE);
            }

            /*
                网页加载完成时处理  如：让 加载对话框 消失
             */
            @Override
            public void onPageFinished(WebView view, String url)
            {
                super.onPageFinished(view, url);
            }

            /*
                加载网页失败时处理 如：提示失败，或显示新的界面
             */
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error)
            {
                super.onReceivedError(view, request, error);

                showToast("加载失败");
            }
        });
    }

    /**
     * 设置WebViewChromeClient
     */
    private void setMyWebViewChromeClient()
    {
        mWebView.setWebChromeClient(new WebChromeClient()
        {
            @Override
            public void onProgressChanged(WebView view, int newProgress)
            {
                if(mBar != null)
                {
                    if(mBar.getVisibility() == View.GONE)
                    {
                        mBar.setVisibility(View.VISIBLE);
                    }

                    mBar.setProgress(newProgress);

                    if (newProgress == 100)
                    {
                        mBar.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    /**
     * 配置WebSetting
     */
    private void setMyWebSetting()
    {
        webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);//支持JS
        webSettings.setDefaultTextEncodingName("utf-8");//设置默认编码
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true);//支持自动加载图片
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        //其中webView.canGoBack()在webView含有一个可后退的浏览记录时返回true
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            if(mWebView.canGoBack())
            {
                mWebView.goBack();
                return true;
            }
            else
            {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        if(mWebView != null)
        {
            mWebView.stopLoading();
            mWebView.destroy();
            mWebView = null;
        }
    }
}
