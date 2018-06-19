package com.ellfors.gankreader.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.ellfors.gankreader.R;
import com.ellfors.gankreader.base.BaseActivity;
import com.ellfors.gankreader.model.LiteModel;
import com.ellfors.gankreader.model.RandomModel;
import com.ellfors.gankreader.model.StudyModel;
import com.ellfors.gankreader.presenter.contract.RandomContract;
import com.ellfors.gankreader.presenter.impl.RandomPresenterImpl;
import com.ellfors.gankreader.ui.fragment.StudyFragment;
import com.ellfors.gankreader.utils.ImageLoader;
import com.ellfors.gankreader.utils.L;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.litepal.crud.DataSupport;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class WebViewActivity extends BaseActivity implements RandomContract.View
{
    @BindView(R.id.webview_coll)
    CollapsingToolbarLayout coll;
    @BindView(R.id.webview_toolbar)
    Toolbar toolbar;
    @BindView(R.id.webview_title_bg)
    ImageView iv_title;
    @BindView(R.id.video_webview)
    WebView mWebView;
    @BindView(R.id.video_webview_progressbar)
    ProgressBar mBar;
    @BindView(R.id.webview_like_button)
    FloatingActionButton btn_like;

    @Inject
    RandomPresenterImpl randomPresenter;

    private WebSettings webSettings;
    private boolean isLiked = false;//标记为未收藏

    @Override
    public void initInject()
    {
        getActivityComponent().inject(this);
        randomPresenter.attachView(this);
    }

    @Override
    public int getLayout()
    {
        return R.layout.activity_webview;
    }

    @Override
    public void initEventAndData()
    {
        /* toolbar */
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.icon_go_back);
        toolbar.setNavigationOnClickListener(v -> finish());
        /* 填充随机图片 */
        randomPresenter.getRandomImg();
        /* 加载页面 */
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            StudyModel model = (StudyModel) bundle.getSerializable(StudyFragment.STUDY_MODEL);
            if (model != null)
            {
                if (!TextUtils.isEmpty(model.getDesc()))
                {
                    /* 设置 CollapsingToolbarLayout 标题属性 */
                    if (model.getDesc().length() > 10)
                        coll.setTitle(model.getDesc().substring(0, 10) + "...");
                    else
                        coll.setTitle(model.getDesc());
                    coll.setCollapsedTitleTextColor(getResources().getColor(R.color.black));
                    coll.setExpandedTitleColor(getResources().getColor(R.color.transparent));
                }
                if (!TextUtils.isEmpty(model.getUrl()))
                    mWebView.loadUrl(model.getUrl());
            }
            /*  设置WebView */
            setMyWebViewClient();
            setMyWebViewChromeClient();
            setMyWebSetting();
            /* 设置是否收藏 */
            checkIsLiked(model);
            setBtnClick(model);
        }
    }

    /**
     * 检查是否已收藏该条目
     */
    private void checkIsLiked(StudyModel model)
    {
        List<LiteModel> list = DataSupport.where("server_id = ?", model.get_id()).find(LiteModel.class);
        if (list == null || list.size() == 0)
        {
            isLiked = false;
            btn_like.setImageResource(R.drawable.icon_like_no);
        }
        else
        {
            isLiked = true;
            btn_like.setImageResource(R.drawable.icon_like_yes);
        }
    }

    /**
     * 设置FloatingActionButton点击事件
     */
    private void setBtnClick(final StudyModel model)
    {
        btn_like.setOnClickListener(v ->
        {
            if (isLiked)
            {
                /* 已收藏，点击取消收藏 */
                DataSupport.deleteAll(LiteModel.class, "server_id = ?", model.get_id());
                isLiked = false;
                btn_like.setImageResource(R.drawable.icon_like_no);
            }
            else
            {
                /* 为收藏，点击收藏 */
                LiteModel liteModel = new LiteModel();
                liteModel.setServer_id(model.get_id());
                liteModel.setTitle(model.getDesc());
                liteModel.setTime(model.getPublishedAt());
                liteModel.setAuthor(model.getWho());
                liteModel.setUrl(model.getUrl());
                liteModel.save();

                isLiked = true;
                btn_like.setImageResource(R.drawable.icon_like_yes);
            }
        });
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

                if (mBar != null)
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
            public void onReceivedError(WebView webView, com.tencent.smtt.export.external.interfaces.WebResourceRequest webResourceRequest, com.tencent.smtt.export.external.interfaces.WebResourceError webResourceError)
            {
                super.onReceivedError(webView, webResourceRequest, webResourceError);
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
            public void onProgressChanged(WebView webView, int newProgress)
            {
                super.onProgressChanged(webView, newProgress);

                if (mBar != null)
                {
                    if (mBar.getVisibility() == View.GONE)
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
            if (mWebView.canGoBack())
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

        if (mWebView != null)
        {
            mWebView.stopLoading();
            mWebView.destroy();
            mWebView = null;
        }
        if (randomPresenter != null)
            randomPresenter.detachView();
    }

    @Override
    public void setImage(RandomModel model)
    {
        ImageLoader.load(mContext, model.getUrl(), iv_title);
    }

    @Override
    public void showError(String msg)
    {
        L.e("获取随机图片Error = " + msg);
        iv_title.setImageResource(R.drawable.icon_about_bg);
    }
}
