package com.xmmaker.vrmarket.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.xmmaker.vrmarket.R;
import com.xmmaker.vrmarket.beans.OnlineAppInfo;

import java.util.ArrayList;


public class OnlineAppAdapter extends
		RecyclerView.Adapter<OnlineAppAdapter.ViewHolder> implements View.OnClickListener{

	private ArrayList<OnlineAppInfo> datalist;
	private Context context;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

	public OnlineAppAdapter(ArrayList<OnlineAppInfo> datalist, Context context) {
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


    public static class ViewHolder extends RecyclerView.ViewHolder {

		private TextView mTextTitle;
		private ExpandableTextView mTextContent;
		private TextView mTextOther;
		private ImageView mImageView;
        private RelativeLayout bannerLayout;

		public ViewHolder(View v) {
			super(v);

			mTextTitle = (TextView) v.findViewById(R.id.tv_app_summary_title);
			mTextContent = (ExpandableTextView) v.findViewById(R.id.tv_app_summary_info);
			mTextOther = (TextView) v.findViewById(R.id.tv_app_summary_size);
			mImageView = (ImageView) v.findViewById(R.id.iv_app_summary_photo);
            bannerLayout= (RelativeLayout) v.findViewById(R.id.layout_top);

		}

	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return datalist == null ? 0 : datalist.size();
	}


	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

		View view = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.item_online_app, viewGroup, false);

		ViewHolder viewHolder=new ViewHolder(view);

        viewHolder.bannerLayout.setOnClickListener(this);

		return viewHolder;
	}

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int i) {

		viewHolder.mTextTitle.setText(datalist.get(i).getName());
		viewHolder.mTextContent.setText("简介："+datalist.get(i).getInfo());
		viewHolder.mTextOther.setText("大小："+datalist.get(i).getSize()+"M");
		Glide
				.with(context)
				.load(datalist.get(i).getIconlink())
				.centerCrop()
				.placeholder(R.drawable.loading)
				.crossFade()
				.into(viewHolder.mImageView);

        viewHolder.bannerLayout.setTag(i);

	}

}
