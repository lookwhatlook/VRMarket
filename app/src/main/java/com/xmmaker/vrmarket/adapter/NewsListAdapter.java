package com.xmmaker.vrmarket.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xmmaker.vrmarket.R;
import com.xmmaker.vrmarket.beans.NewsListBean;

import java.util.ArrayList;
import java.util.List;


public class NewsListAdapter extends
        RecyclerView.Adapter<NewsListAdapter.ViewHolder> implements View.OnClickListener{

    private List<NewsListBean.T1348649580692Bean> datalist;
    private Context context;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public NewsListAdapter(List<NewsListBean.T1348649580692Bean> datalist, Context context) {
        super();
        this.datalist = datalist;
        this.context = context;

    }

    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , int position);
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(view, (Integer) view.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return datalist == null ? 0 : datalist.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.item_news_summary, viewGroup, false);

        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);

//        if (mOnItemClickListener != null) {
//            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mOnItemClickListener.onItemClick(v, viewHolder.getLayoutPosition());
//                }
//            });
//        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.textTitle.setText(datalist.get(i).getTitle());
        viewHolder.textContent.setText(datalist.get(i).getDigest());
        viewHolder.textTime.setText(datalist.get(i).getPtime());
        Glide
                .with(context)
                .load(datalist.get(i).getImgsrc())
                .centerCrop()
                .placeholder(R.drawable.loading)
                .crossFade()
                .into(viewHolder.mImageView);

        viewHolder.itemView.setTag(i);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textTitle;
        private TextView textContent;
        private TextView textTime;
        private ImageView mImageView;

        public ViewHolder(View v) {
            super(v);

            textTitle = (TextView) v.findViewById(R.id.tv_news_summary_title);
            textContent = (TextView) v.findViewById(R.id.tv_news_summary_digest);
            textTime = (TextView) v.findViewById(R.id.tv_news_summary_ptime);
            mImageView = (ImageView) v.findViewById(R.id.iv_news_summary_photo);

        }

    }

}
