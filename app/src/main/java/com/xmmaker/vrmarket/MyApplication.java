package com.xmmaker.vrmarket;

import android.app.Application;

import com.lzy.okhttputils.OkHttpUtils;

/**
 * Created by Administrator on 2016/4/12.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //必须调用初始化
        OkHttpUtils.init(this);
        //以下都不是必须的，根据需要自行选择
        OkHttpUtils.getInstance()//
               // .debug("OkHttpUtils")                                              //是否打开调试
                .setConnectTimeout(OkHttpUtils.DEFAULT_MILLISECONDS)               //全局的连接超时时间
                .setReadTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS)                  //全局的读取超时时间
                .setWriteTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS);               //全局的写入超时时间

    }
}
