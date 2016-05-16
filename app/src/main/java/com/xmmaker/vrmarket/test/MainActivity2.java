package com.xmmaker.vrmarket.test;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.xmmaker.vrmarket.AboutActivity;
import com.xmmaker.vrmarket.MainActivity;
import com.xmmaker.vrmarket.R;
import com.xmmaker.vrmarket.adapter.MyFragmentPagerAdapter;
import com.xmmaker.vrmarket.fragments.LocalAppFragment;
import com.xmmaker.vrmarket.fragments.NewsFragment;
import com.xmmaker.vrmarket.fragments.OnlineAppFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity2 extends AppCompatActivity implements LocalAppFragment.OnFragmentInteractionListener, AppBarLayout.OnOffsetChangedListener {

    private final static String WEB_URL = "http://www.xmmaker.com/";
    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;
    //    @Bind(R.id.text_shuoming)
//    ShimmerTextView textShuoming;
//    @Bind(R.id.recyclerView)
//    RecyclerView recyclerView;
//    @Bind(R.id.swipe_layout)
//    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fragment_container)
    ViewPager viewPager;
    private long exitTime;
    private BottomBar mBottomBar;


    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.ic_settings_cell_white_24dp);

        collapsingToolbar.setTitle("VR软件推荐");
//        collapsingToolbar.setExpandedTitleColor(Color.BLACK);//设置还没收缩时状态下字体颜色

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linkToWebsite();
            }
        });



        mBottomBar = BottomBar.attach(this, savedInstanceState);
        initBottomTab();

        initViewPager();


    }

    private void initViewPager() {

        LocalAppFragment localAppFragment = new LocalAppFragment();
        OnlineAppFragment onlineAppFragment = new OnlineAppFragment();
        NewsFragment newsFragment = new NewsFragment();
        fragmentList = new ArrayList<Fragment>();

        fragmentList.add(0, localAppFragment);
        fragmentList.add(1, onlineAppFragment);
        fragmentList.add(2, newsFragment);

        MyFragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(3);


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        mBottomBar.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        appBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        appBarLayout.removeOnOffsetChangedListener(this);
    }

    //跳转到厦门创客网
    private void linkToWebsite() {
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(WEB_URL));
//        intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
//        startActivity(intent);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    private void initBottomTab() {

        mBottomBar.setItemsFromMenu(R.menu.bottombar_menu, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarItemOne) {
                    collapsingToolbar.setTitle("已安装VR软件");
                    collapsingToolbar.setContentScrimColor(ContextCompat.getColor(MainActivity2.this, R.color.colorPrimary));
                    appBarLayout.setExpanded(false);
                    toolbar.setLogo(R.drawable.ic_settings_cell_white_24dp);

//                    currentFragment=new NewsFragment();
//                    fragmentManager.beginTransaction()
//                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                    .replace(R.id.fragment_container, currentFragment)
//                            .commit();

                    viewPager.setCurrentItem(0,true);
                } else if (menuItemId == R.id.bottomBarItem2) {
                    collapsingToolbar.setTitle("VR软件推荐");
                    collapsingToolbar.setContentScrimColor(ContextCompat.getColor(MainActivity2.this, R.color.material_blue_grey_80));
                    appBarLayout.setExpanded(true);
                    toolbar.setLogo(R.drawable.ic_info_outline_white_24dp);

                    viewPager.setCurrentItem(1,true);

                } else if (menuItemId == R.id.bottomBarItem3) {
                    collapsingToolbar.setTitle("新闻");
                    collapsingToolbar.setContentScrimColor(ContextCompat.getColor(MainActivity2.this, R.color.red_btn_bg_color));
                    appBarLayout.setExpanded(false);
                    toolbar.setLogo(R.drawable.ic_remove_red_eye_white_24dp);
                    viewPager.setCurrentItem(2,true);

                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarItemOne) {
                    // The user reselected item number one, scroll your content to top.
                }
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {

            //兼容包提供的activity切换动画 。让新的Activity从一个小的范围扩大到全屏
            View view = findViewById(R.id.root_layout);
            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeScaleUpAnimation(view, //The View that the new activity is animating from
                            (int) view.getWidth() / 2, (int) view.getHeight() / 2, //拉伸开始的坐标
                            0, 0);//拉伸开始的区域大小，这里用（0，0）表示从无到全屏
            Intent intent = new Intent(MainActivity2.this, AboutActivity.class);
            ActivityCompat.startActivity(this, intent, options.toBundle());

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//        LocalAppFragment f= (LocalAppFragment) getFragmentManager().findFragmentByTag("tag1");
//        if (verticalOffset == 0) {
//            f.changeSwipe(0);
//        } else {
//            f.changeSwipe(1);
//        }

    }
}
