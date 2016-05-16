package com.xmmaker.vrmarket.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xmmaker.vrmarket.R;
import com.xmmaker.vrmarket.beans.AppInfo;

import java.util.ArrayList;


public class LocalAppAdapter extends
		RecyclerView.Adapter<LocalAppAdapter.ViewHolder> {

	private ArrayList<AppInfo> datalist;
//	private Context context;

	public LocalAppAdapter(ArrayList<AppInfo> datalist) {
		super();
		this.datalist = datalist;
	}

//	public LocalAppAdapter(ArrayList<AppInfo> datalist, Context context) {
//		super();
//		this.datalist = datalist;
//		this.context = context;
//
//	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		private TextView mTextView;
		private ImageView mImageView;

		public ViewHolder(View v) {
			super(v);

			mTextView = (TextView) v.findViewById(R.id.tv_appName);
			mImageView = (ImageView) v.findViewById(R.id.img_appIcon);

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
				R.layout.item_cardview, viewGroup, false);

		ViewHolder viewHolder=new ViewHolder(view);

		return viewHolder;
	}

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int i) {

		viewHolder.mTextView.setText(datalist.get(i).appName);
		viewHolder.mImageView.setImageDrawable(datalist.get(i).appIcon);
	}

}
