package com.xmmaker.vrmarket.test;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

import com.xmmaker.vrmarket.R;
import com.xmmaker.vrmarket.fragments.LocalAppFragment;
import com.xmmaker.vrmarket.fragments.NewsFragment;
import com.xmmaker.vrmarket.fragments.OnlineAppFragment;

/**
 * Created by Administrator on 2016/4/13.
 */
public class TabAc extends FragmentActivity implements LocalAppFragment.OnFragmentInteractionListener{

    private FragmentTabHost mTabHost = null;;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tab);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        // 添加tab名称和图标
        mTabHost.addTab(mTabHost.newTabSpec("local").setIndicator("local"),
                LocalAppFragment.class,null);
        mTabHost.addTab(mTabHost.newTabSpec("online").setIndicator("online"),
                OnlineAppFragment.class,null);
        mTabHost.addTab(mTabHost.newTabSpec("news").setIndicator("news"),
                NewsFragment.class,null);

    }


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        mTabHost = null;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
