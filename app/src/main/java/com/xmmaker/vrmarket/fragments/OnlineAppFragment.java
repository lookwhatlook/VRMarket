package com.xmmaker.vrmarket.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;
import com.lzy.okhttputils.callback.AbsCallback;
import com.lzy.okhttputils.request.BaseRequest;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.xmmaker.vrmarket.Api;
import com.xmmaker.vrmarket.R;
import com.xmmaker.vrmarket.adapter.OnlineAppAdapter;
import com.xmmaker.vrmarket.beans.OnlineAppInfo;
import com.xmmaker.vrmarket.test.SpaceItemDecoration;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;


public class OnlineAppFragment extends Fragment {

    @BindView(R.id.text_shuoming)
    ShimmerTextView textShuoming;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.loading_progressbar)
    ContentLoadingProgressBar loadingProgressbar;

    private ArrayList<OnlineAppInfo> dataList;
    private OnlineAppAdapter mAdapter;


    public OnlineAppFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_online_app, container, false);
        ButterKnife.bind(this, view);
        dataList = new ArrayList<OnlineAppInfo>();

        initRecyclerView();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initSwipeRefreshLayout();
//        initRecyclerView();
    }

    //初始化软件列表
    private void getAppList() {
        OkHttpUtils.get(Api.XM_ALL_APPS)
                .tag(this)               // 请求的 tag, 主要用于取消对应的请求
                .connTimeOut(10000)      // 设置当前请求的连接超时时间
                .readTimeOut(10000)      // 设置当前请求的读取超时时间
                .writeTimeOut(10000)     // 设置当前请求的写入超时时间
                .cacheKey("cache_apps")    // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST) // 缓存模式，这个模式onResponse执行两次
                .execute(new AbsCallback<ArrayList<OnlineAppInfo>>() {

                    @Override
                    public void onBefore(BaseRequest request) {
                        // UI线程 请求网络之前调用
                        // 可以显示对话框，添加/修改/移除 请求参数
                        loadingProgressbar.show();
                    }

                    @Override
                    public ArrayList<OnlineAppInfo> parseNetworkResponse(Response response) {
                        ArrayList<OnlineAppInfo> listBeans = null;
                        if (response == null) {
                            return null;
                        }
                        try {
                            String responseString = response.body().string();
                            listBeans = new ArrayList<OnlineAppInfo>(JSONArray.parseArray(responseString, OnlineAppInfo.class));
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        return listBeans;
                    }


                    @Override
                    public void onResponse(boolean isFromCache, ArrayList<OnlineAppInfo> requestInfo, Request request, @Nullable Response response) {
                        // UI 线程，请求成功后回调
                        // isFromCache 表示当前回调是否来自于缓存
                        // requestInfo 返回泛型约定的实体类型参数
                        // request     本次网络的请求信息，如果需要查看请求头或请求参数可以从此对象获取
                        // response    本次网络访问的结果对象，包含了响应头，响应码等，如果数据来自于缓存，该对象为null
                        Log.e("OK","onResponse>>>"+"是否缓存》》"+isFromCache);

                        if (requestInfo != null) {
                            dataList.clear();
                            dataList.addAll(requestInfo);
//                            mAdapter = new OnlineAppAdapter(dataList, getActivity());
//                            mRecyclerView.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();
                            mAdapter.setOnItemClickListener(new OnlineAppAdapter.OnRecyclerViewItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    downloadDialog(position);
                                }
                            });


                            if (!isFromCache) {
//                                Toast.makeText(getActivity(), "刷新完毕", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            if(!isFromCache){
                                makeSnackBar("获取内容失败");
                            }
                        }

                    }

                    @Override
                    public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                        if (!isFromCache) {
                            makeSnackBar("连网失败，请检查手机网络");
                        }

                    }

                    @Override
                    public void onAfter(boolean isFromCache, @Nullable ArrayList<OnlineAppInfo> appInfo, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onAfter(isFromCache, appInfo, call, response, e);
                        loadingProgressbar.hide();
                    }
                });
    }

    //调用activity的提示框
    private void makeSnackBar(String text) {
//        MainActivity activity;
//        activity= (MainActivity) getActivity();
//        activity.showSnackBar(text);
        if(isAdded()) {
            Snackbar.make(mRecyclerView, text, Snackbar.LENGTH_SHORT).show();
        }
    }


    private void initRecyclerView() {
        textShuoming.setText("厦门创客网http://www.xmmaker.com/");
        Shimmer shimmer = new Shimmer();
        shimmer.start(textShuoming);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.addItemDecoration(new DividerDecoration(getActivity()));
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.setNestedScrollingEnabled(true);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.card_insets);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        //设置空的adapter
        mAdapter = new OnlineAppAdapter(dataList, getActivity());
        mRecyclerView.setAdapter(mAdapter);

        getAppList();

//        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
//                new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//                        if (dataList.size() > 0) {
//
//                            downloadDialog(position);
//
//                        }
//
//                    }
//                }));

    }

    //提示是否下载
    private void downloadDialog(final int position) {
        String apkName = dataList.get(position).getName();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("确定下载"+apkName+"吗？")
                .setTitle("提示")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String apkURL = dataList.get(position).getLink();
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(apkURL));
                        startActivity(intent);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }

                });

        builder.create().show();

    }

    //下拉刷新组件
    private void initSwipeRefreshLayout() {
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getAppList();
                swipeRefreshLayout.setRefreshing(false);

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //ButterKnife.unbind(this);
        //根据 Tag 取消请求
        OkHttpUtils.getInstance().cancelTag(this);
    }

}
