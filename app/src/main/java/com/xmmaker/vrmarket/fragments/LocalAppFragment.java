package com.xmmaker.vrmarket.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xmmaker.vrmarket.R;
import com.xmmaker.vrmarket.RecyclerItemClickListener;
import com.xmmaker.vrmarket.adapter.LocalAppAdapter;
import com.xmmaker.vrmarket.beans.AppInfo;
import com.xmmaker.vrmarket.test.GridDividerDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class LocalAppFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.loading_progressbar)
    ContentLoadingProgressBar loadingProgressbar;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    private ArrayList<AppInfo> applist;

    private OnFragmentInteractionListener mListener;

    public LocalAppFragment() {
        // Required empty public constructor
    }

    //刷新
    public void refreshData(){
        new Thread() {
            public void run() {
                applist = getAppList(getActivity());
                applist = filterApp(applist);
                handler.sendEmptyMessage(0);
            }
        }.start();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_local_app, container, false);
        ButterKnife.bind(this, view);

        recyclerView.setLayoutManager(new
                GridLayoutManager(getActivity(), 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new GridDividerDecoration(getActivity()));
        loadingProgressbar.show();
        new Thread() {
            public void run() {
                applist = getAppList(getActivity());
                applist = filterApp(applist);
                handler.sendEmptyMessage(0);
            }
        }.start();
        return view;
    }

    private ArrayList<AppInfo> getAppList(Context context) {
        ArrayList<AppInfo> mList = new ArrayList<AppInfo>();
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> PackageList = pm.getInstalledPackages(0);
        int length = PackageList.size();
        // String myPackageName = getApplicationInfo().packageName;
        PackageInfo pi;

        for (int i = 0; i < length; i++) {
            pi = PackageList.get(i);
            // 过滤系统应用,<0不是系统应用
            if ((pi.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                AppInfo mAppInfo = new AppInfo();
                mAppInfo.appName = pi.applicationInfo.loadLabel(pm).toString();
                mAppInfo.packageName = pi.packageName;
                mAppInfo.packageName = (pi.packageName);
                mAppInfo.versionName = pi.versionName;
                mAppInfo.appIcon = pi.applicationInfo
                        .loadIcon(pm);
//				if((pi.applicationInfo.flags&ApplicationInfo.FLAG_SYSTEM)==0)
//				{
//					mList.add(mAppInfo);//如果非系统应用，则添加至appList
//				}
                mList.add(mAppInfo);
            }
        }
        return mList;
    }


    //过滤是否VR软件
    private ArrayList<AppInfo> filterApp(ArrayList<AppInfo> allApps) {
        ArrayList<AppInfo> VRAPP_LIST = new ArrayList<AppInfo>();
        ArrayList<String> database_list = queryFromSQLite();
        for (AppInfo appInfo : allApps) {
            String name = appInfo.appName;
            //过滤自己
//            if(appInfo.packageName.equals("com.xmmaker.vrmarket")){
//                break;
//            }
            if (database_list.contains(name)) {
                VRAPP_LIST.add(appInfo);
            } else if (name.toLowerCase().contains("vr") || name.toLowerCase().contains("cardboard")) {
                VRAPP_LIST.add(appInfo);
            } else if (name.equals("暴风影音") || name.equals("暴风魔镜")) {
                VRAPP_LIST.add(appInfo);
            }

        }

        return VRAPP_LIST;

    }


    // 根据扫描到的编码（检测项目编号），从数据库查询到对应的检测项目名称
    private ArrayList<String> queryFromSQLite() {
        ArrayList<String> nodes = new ArrayList<String>();
        try {

            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
                    "/data/data/com.xmmaker.vrmarket/databases/vr_database.db", null);
            Cursor c = db.rawQuery("SELECT package_name FROM T_VR_APPS", null);
            while (c.moveToNext()) {

                String name = c.getString(c.getColumnIndex("package_name"));
                nodes.add(name);
            }
            c.close();
            db.close();
//            Log.e("OK","查询数据库结果》"+nodes.toString());
        } catch (Exception e) {
            e.printStackTrace();
            // 如果数据库没有直接显示null
            nodes = null;
        }
        return nodes;
    }


    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if(loadingProgressbar!=null){
                loadingProgressbar.hide();
            }

            if (applist != null && applist.size() > 0) {
                initRecyclerView();
            } else {
                tvEmpty.setVisibility(View.VISIBLE);
            }

        }
    };

    private void initRecyclerView() {

        LocalAppAdapter mAdapter = new LocalAppAdapter(applist);
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        startAPP(applist.get(position).packageName);
                    }
                }));

    }

    //根据包名启动一个app
    public void startAPP(String appPackageName) {
        try {
            if (!appPackageName.equals("com.xmmaker.vrmarket")) {
                Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage(appPackageName);
                startActivity(intent);
            } else {

            }

        } catch (Exception e) {
            Snackbar.make(recyclerView, "打开失败", Snackbar.LENGTH_SHORT).show();
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @OnClick(R.id.tv_empty)
    public void onClick() {
//        if (tvEmpty.getVisibility() == View.VISIBLE) {
//            tvEmpty.setVisibility(View.GONE);
//        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

//    public void changeSwipe(int i){
//        if (i==0) {
//            swipeRefreshLayout.setEnabled(true);
//        } else {
//            swipeRefreshLayout.setEnabled(false);
//        }
//    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //ButterKnife.unbind(this);
    }
}
