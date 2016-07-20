package com.xmmaker.vrmarket.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.lzy.okhttputils.OkHttpUtils;
import com.xmmaker.vrmarket.Api;
import com.xmmaker.vrmarket.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewsOnlineFragment extends Fragment {

    @BindView(R.id.news_webview)
    WebView mWebView;
    @BindView(R.id.news_progress)
    ProgressBar progressBar;
    @BindView(R.id.iv_web_back)
    ImageView ivWebBack;
    @BindView(R.id.iv_web_go)
    ImageView ivWebGo;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeLayout;
    private View rootView;//缓存Fragment view


    public NewsOnlineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        View rootView = inflater.inflate(R.layout.fragment_news, container, false);

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_news_online, container, false);
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }

        ButterKnife.bind(this, rootView);

        WebSettings setting = mWebView.getSettings();
        setting.setJavaScriptEnabled(true);

        //复写WebViewClient的shouldOverrideUrlLoading()的方法

        //如果需要事件处理返回false,否则返回true.这样就可以解决问题了

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                    swipeLayout.setRefreshing(false);
                } else {
                    if (progressBar.getVisibility() == View.GONE)
                        progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                }
            }
        });

        mWebView.loadUrl(Api.VR_NEWS_913);


        //设置刷新时动画的颜色，可以设置4个
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                mWebView.reload();
            }
        });

        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//写在这里每次切换fragment会重新执行
//        initNewsList();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        ButterKnife.unbind(this);
        //Activity销毁时，取消网络请求
        OkHttpUtils.getInstance().cancelTag(this);
    }

    @OnClick({R.id.iv_web_back, R.id.iv_web_go})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_web_back:
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                }

                break;
            case R.id.iv_web_go:
                if (mWebView.canGoForward()) {
                    mWebView.goForward();
                }
                break;
        }
    }
}
