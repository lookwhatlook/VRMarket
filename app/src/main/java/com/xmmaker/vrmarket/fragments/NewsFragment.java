package com.xmmaker.vrmarket.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;
import com.lzy.okhttputils.callback.AbsCallback;
import com.lzy.okhttputils.request.BaseRequest;
import com.xmmaker.vrmarket.Api;
import com.xmmaker.vrmarket.NewsDetailActivity;
import com.xmmaker.vrmarket.R;
import com.xmmaker.vrmarket.adapter.NewsListAdapter;
import com.xmmaker.vrmarket.beans.NewsListBean;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public class NewsFragment extends Fragment {

    @BindView(R.id.recyclerview)
    XRecyclerView mRecyclerView;
    List<NewsListBean.T1348649580692Bean> dataList;
    @BindView(R.id.loading_progressbar)
    ContentLoadingProgressBar loadingProgressbar;

    private NewsListAdapter mAdapter;
    private int START_PAGE = 0,PAGE_SIZE = 20;

    private View rootView;//缓存Fragment view


    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        View rootView = inflater.inflate(R.layout.fragment_news, container, false);

        if(rootView==null){
            rootView=inflater.inflate(R.layout.fragment_news, container, false);
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }

        ButterKnife.bind(this, rootView);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        //连网初始化数据，写在这里才不会重复执行。
        initNewsList();

        return rootView;
    }

    //原生httpurlconn连网请求
//    private void httpConn() {
//        new Thread() {
//
//            @Override
//            public void run() {
//
//                HttpURLConnection urlConnection = null;
//                try {
//                    Log.e("OK", "http>>111");
//                    URL url = new URL("http://c.m.163.com/nc/article/headline/T1348649580692/0-10.html");
//                    urlConnection = (HttpURLConnection) url.openConnection();
//                    urlConnection.setDoInput(true); //允许输入流，即允许下载
//                    urlConnection.setDoOutput(true); //允许输出流，即允许上传
//                    urlConnection.setUseCaches(false); //不使用缓冲
//                    urlConnection.setRequestMethod("GET"); //使用get请求
//                    InputStream is = urlConnection.getInputStream();
//                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
//                    String response = "";
//                    String readLine = null;
//                    while ((readLine = br.readLine()) != null) {
//                        //response = br.readLine();
//                        response = response + readLine;
//                    }
//                    is.close();
//                    br.close();
//                    urlConnection.disconnect();
//                    Log.e("OK", response);
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    urlConnection.disconnect();
//                }
//
//            }
//
//        }.start();
//
//    }

    //原版OKHttpClient方式
//    private void ByOkHttpClient(){
//        //创建okHttpClient对象
//        OkHttpClient mOkHttpClient = new OkHttpClient();
//        //创建一个Request
//        final Request request = new Request.Builder()
//                .url(Api.NEWS_URL)
//                .build();
//        //new call
//        Call call = mOkHttpClient.newCall(request);
//        //请求加入调度
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//                Log.e("ok","fail");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                final String res = response.body().string();
//                Log.e("ok",res);
//                System.out.print(res);
//            }
//
//        });
//
//    }

    //加载新闻
    private void initNewsList() {

        OkHttpUtils.get(Api.NEWS_URL) // 请求方式和请求url, get请求不需要拼接参数，支持get，post，put，delete，head，options请求
                .tag(this)               // 请求的 tag, 主要用于取消对应的请求
                .connTimeOut(10000)      // 设置当前请求的连接超时时间
                .readTimeOut(10000)      // 设置当前请求的读取超时时间
                .writeTimeOut(10000)     // 设置当前请求的写入超时时间
                .cacheKey("cache_news")    // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST) // 缓存模式，详细请看第四部分，缓存介绍
                //这里给出的泛型为 RequestInfo，同时传递一个泛型的 class对象，即可自动将数据结果转成对象返回
                .execute(new AbsCallback<NewsListBean>() {

                    @Override
                    public void onBefore(BaseRequest request) {
                        // UI线程 请求网络之前调用
                        // 可以显示对话框，添加/修改/移除 请求参数
                        loadingProgressbar.show();
                    }

                    @Override
                    public NewsListBean parseNetworkResponse(Response response) {
                        NewsListBean newsList = null;
                        if (response == null) {
                            return null;
                        }
                        try {
                            String responseString = response.body().string();
                            newsList = JSON.parseObject(responseString, NewsListBean.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return newsList;
                    }

                    @Override
                    public void onResponse(boolean isFromCache, NewsListBean requestInfo, Request request, @Nullable Response response) {
                        // UI 线程，请求成功后回调
                        // isFromCache 表示当前回调是否来自于缓存
                        // requestInfo 返回泛型约定的实体类型参数
                        // request     本次网络的请求信息，如果需要查看请求头或请求参数可以从此对象获取
                        // response    本次网络访问的结果对象，包含了响应头，响应码等，如果数据来自于缓存，该对象为null
                        //                Log.e("OK", "sssss");
                        if (requestInfo != null) {
                            dataList = requestInfo.getT1348649580692();
                            mAdapter = new NewsListAdapter(dataList, getActivity());
                            mRecyclerView.setAdapter(mAdapter);
                            if (!isFromCache) {
                                START_PAGE += PAGE_SIZE;
                            }

                            setClick();

                        } else {
                            if(!isFromCache) {
                                makeSnackBar("刷新失败");
                            }
                        }

                    }

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        // UI 线程，请求失败后回调
                        // isFromCache 表示当前回调是否来自于缓存
                        // call        本次网络的请求对象，可以根据该对象拿到 request
                        // response    本次网络访问的结果对象，包含了响应头，响应码等，如果网络异常 或者数据来自于缓存，该对象为null
                        // e           本次网络访问的异常信息，如果服务器内部发生了错误，响应码为 400~599之间，该异常为 null
                        if (!isFromCache) {
                            makeSnackBar("连网失败，请检查手机网络");
                        }

                    }

                    @Override
                    public void onAfter(boolean isFromCache, @Nullable NewsListBean newsListBean, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onAfter(isFromCache, newsListBean, call, response, e);
                        loadingProgressbar.hide();
                    }
                });
//Asycnhttp方式
//        HttpUtil.get(Api.NEWS_URL, new TextHttpResponseHandler() {
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, String responseString) {
//                Log.e("OK", "sssss");
//                NewsListBean newsList = JSON.parseObject(responseString, NewsListBean.class);
//                dataList = newsList.getT1348649580692();
//                mAdapter = new NewsListAdapter(dataList, getActivity());
//                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//                mRecyclerView.setLayoutManager(layoutManager);
//                mRecyclerView.setAdapter(mAdapter);
//                START_PAGE += 10;
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                Log.e("OK", "fail>>>>>>>.");
//
//                Toast.makeText(getActivity(), "获取新闻失败", Toast.LENGTH_SHORT).show();
//            }
//
//        });

    }


    //加载更多
    private void MoreNewsList() {

        OkHttpUtils.get(Api.NEWS_URL_PAGE + START_PAGE + Api.ENDNEWS_URL)
                .tag(this)               // 请求的 tag, 主要用于取消对应的请求
                .connTimeOut(10000)      // 设置当前请求的连接超时时间
                .readTimeOut(10000)      // 设置当前请求的读取超时时间
                .writeTimeOut(10000)     // 设置当前请求的写入超时时间
                .cacheKey("cache_news_more")    // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT) // 缓存模式，详细请看第四部分，缓存介绍
                //这里给出的泛型为 RequestInfo，同时传递一个泛型的 class对象，即可自动将数据结果转成对象返回
                .execute(new AbsCallback<NewsListBean>() {

                    @Override
                    public NewsListBean parseNetworkResponse(Response response) {
                        NewsListBean newsList = null;
                        if (response == null) {
                            return null;
                        }
                        try {
                            String responseString = response.body().string();
                            newsList = JSON.parseObject(responseString, NewsListBean.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return newsList;
                    }

                    @Override
                    public void onResponse(boolean isFromCache, NewsListBean requestInfo, Request request, @Nullable Response response) {
                        // UI 线程，请求成功后回调
                        if (requestInfo != null) {
                            List<NewsListBean.T1348649580692Bean> data_more = requestInfo.getT1348649580692();
                            dataList.addAll(data_more);
                            mAdapter.notifyDataSetChanged();
                            START_PAGE += 10;
                        } else {
                            Toast.makeText(getActivity(), "没有了", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        Toast.makeText(getActivity(), "连网失败，请检查手机网络", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAfter(boolean isFromCache, @Nullable NewsListBean newsListBean, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onAfter(isFromCache, newsListBean, call, response, e);
                        mRecyclerView.loadMoreComplete();
                    }


                });


    }

    //recyclerview点击事件
    private void setClick() {
        mAdapter.setOnItemClickListener(new NewsListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (dataList.size() != 0) {
                    String news_url = dataList.get(position).getUrl();
                    String news_title=dataList.get(position).getTitle();
                    if (TextUtils.isEmpty(news_url)) {
                        Toast.makeText(getActivity(), "无法查看该新闻", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                        intent.putExtra("NEWS_URL", news_url);
                        intent.putExtra("NEWS_TITLE", news_title);
                        startActivity(intent);
                    }
                }
            }
        });

    }

    //提示框
    private void makeSnackBar(String text) {
        if(isAdded()) {
            Snackbar.make(mRecyclerView, text, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//写在这里每次切换fragment会重新执行
//        initNewsList();

        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {

            @Override
            public void onRefresh() {
                START_PAGE = 0;
                dataList.clear();
                initNewsList();
                mRecyclerView.refreshComplete();

            }

            @Override
            public void onLoadMore() {
                MoreNewsList();

            }
        });


//        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
//                new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//                        if (position > 0) {
//                            position -= 1;
//                        }
//                        if (dataList.size()!=0) {
//                            String news_url = dataList.get(position).getUrl();
//                            if (TextUtils.isEmpty(news_url)) {
//                                Toast.makeText(getActivity(), "无法查看该新闻", Toast.LENGTH_SHORT).show();
//                            } else {
//                                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
//                                intent.putExtra("NEWS_URL", news_url);
//                                startActivity(intent);
//                            }
//                        }
//
//                    }
//                }));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        ButterKnife.unbind(this);
        //Activity销毁时，取消网络请求
        OkHttpUtils.getInstance().cancelTag(this);
    }

}
