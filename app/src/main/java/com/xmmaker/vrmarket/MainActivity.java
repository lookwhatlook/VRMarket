package com.xmmaker.vrmarket;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.xmmaker.vrmarket.fragments.LocalAppFragment;
import com.xmmaker.vrmarket.fragments.NewsFragment2;
import com.xmmaker.vrmarket.fragments.OnlineAppFragment2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LocalAppFragment.OnFragmentInteractionListener {

    private final static String WEB_URL = "http://www.xmmaker.com/";
    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.image)
    ImageView imageBanner;
    private long exitTime;
    private BottomBar mBottomBar;
    private Fragment currentFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        imporDatabase();

        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.ic_settings_cell_white_24dp);
        appBarLayout.setExpanded(false);
        collapsingToolbar.setTitle("VR软件推荐");
//        collapsingToolbar.setExpandedTitleColor(Color.BLACK);//设置还没收缩时状态下字体颜色

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linkToWebsite();
            }
        });

        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            currentFragment = new LocalAppFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, currentFragment, "tag1")
                    .commit();
        }

        mBottomBar = BottomBar.attach(this, savedInstanceState);
//        mBottomBar = BottomBar.attachShy((CoordinatorLayout) findViewById(R.id.coordinator_layout),
//                findViewById(R.id.fragment_container), savedInstanceState);

        initBottomTab();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        mBottomBar.onSaveInstanceState(outState);
    }


    //从raw文件夹导入数据库
    public void imporDatabase() {
        // 存放数据库的目录
        String packgeName = getPackageName();
        String dirPath = "/data/data/" + packgeName + "/databases";
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        // 数据库文件
        File file = new File(dir, "vr_database.db");
        try {
            if (!file.exists()) {
                file.createNewFile();
            } else {
                return;
            }
            // 加载需要导入的数据库
            InputStream is = this.getApplicationContext().getResources()
                    .openRawResource(R.raw.vr_database);
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buffere = new byte[is.available()];
            is.read(buffere);
            fos.write(buffere);
            is.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //跳转到厦门创客网
    private void linkToWebsite() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(WEB_URL));
        intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
        startActivity(intent);

    }

    private void initBottomTab() {
        mBottomBar.setActiveTabColor("#CD5B55");
        mBottomBar.setItemsFromMenu(R.menu.bottombar_menu, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {

                appBarLayout.setExpanded(false);

                if (menuItemId == R.id.bottomBarItemOne) {
                    collapsingToolbar.setTitle("已安装VR软件");
//                    collapsingToolbar.setContentScrimColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
                    toolbar.setLogo(R.drawable.ic_settings_cell_white_24dp);

//                    Fragment localAppFragment = new LocalAppFragment();
//                    fragmentManager.beginTransaction()
//                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                    .replace(R.id.fragment_container, localAppFragment,"tag1")
//                            .commit();
//                    currentFragment = localAppFragment;

                    LocalAppFragment fragment = (LocalAppFragment) fragmentManager.findFragmentByTag("tag1");
                    if (fragment == null) {
                        Fragment localAppFragment = new LocalAppFragment();
                        //将新Fragment add并将当前显示的Fragment hide
                        fragmentManager.beginTransaction()
                                .hide(currentFragment).add(R.id.fragment_container, localAppFragment, "tag1").commit();
                        currentFragment = localAppFragment;
                    } else if (fragment.isHidden()) {
                        fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .hide(currentFragment).show(fragment).commit();
                        fragment.refreshData();
                        currentFragment = fragment;

                    }


                } else if (menuItemId == R.id.bottomBarItem2) {
                    collapsingToolbar.setTitle("VR软件推荐");
//                    collapsingToolbar.setContentScrimColor(ContextCompat.getColor(MainActivity.this, R.color.material_blue_grey_80));
                    toolbar.setLogo(R.drawable.ic_info_outline_white_24dp);

                    Fragment fragment = fragmentManager.findFragmentByTag("tag2");
                    if (fragment == null) {
                        Fragment onlineAppFragment = new OnlineAppFragment2();
                        //将新Fragment add并将当前显示的Fragment hide
                        fragmentManager.beginTransaction()
                                .hide(currentFragment).add(R.id.fragment_container, onlineAppFragment, "tag2").commit();
                        currentFragment = onlineAppFragment;
                    } else if (fragment.isHidden()) {
                        fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .hide(currentFragment).show(fragment).commit();
                        currentFragment = fragment;

                    }

                } else if (menuItemId == R.id.bottomBarItem3) {
                    collapsingToolbar.setTitle("新闻");
//                    collapsingToolbar.setContentScrimColor(ContextCompat.getColor(MainActivity.this, R.color.red_btn_bg_color));
                    toolbar.setLogo(R.drawable.ic_remove_red_eye_white_24dp);
                    Fragment fragment = fragmentManager.findFragmentByTag("tag3");
                    if (fragment == null) {
                        Fragment newsFragment = new NewsFragment2();
                        //将新Fragment add并将当前显示的Fragment hide
                        fragmentManager.beginTransaction().hide(currentFragment).add(R.id.fragment_container, newsFragment, "tag3").commit();
                        currentFragment = newsFragment;
                    } else if (fragment.isHidden()) {
                        fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .hide(currentFragment).show(fragment).commit();
                        currentFragment = fragment;

                    }

                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                appBarLayout.setExpanded(false);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000)  //System.currentTimeMillis()无论何时调用，肯定大于2000
        {
            // showSnackBar(null);
            exitTime = System.currentTimeMillis();

            View v = (CoordinatorLayout) findViewById(R.id.coordinator_layout);

            Snackbar.make(v, "再按一次退出程序", Snackbar.LENGTH_SHORT)
                    .setAction("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    }).show();

        } else {
            finish();
            System.exit(0);
        }
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
            View view = findViewById(R.id.coordinator_layout);
            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeScaleUpAnimation(view, //The View that the new activity is animating from
                            (int) view.getWidth() / 2, (int) view.getHeight() / 2, //拉伸开始的坐标
                            0, 0);//拉伸开始的区域大小，这里用（0，0）表示从无到全屏
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            ActivityCompat.startActivity(this, intent, options.toBundle());

//            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
//            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    //显示snackbar提示
    public void showSnackBar(String text) {
        Snackbar.make(mBottomBar, text, Snackbar.LENGTH_SHORT).show();
    }

}
