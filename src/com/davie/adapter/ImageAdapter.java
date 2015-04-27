package com.davie.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.common.image.BitmapCache;
import com.davie.domain.ImageDetail;
import com.davie.wangyinews.R;

public class ImageAdapter extends BaseAdapter {

	private Context context;
	private List<ImageDetail> list;
	private RequestQueue queue;
	private ImageLoader imageLoader;

	public ImageAdapter(Context context, List<ImageDetail> list) {
		super();
		this.context = context;
		this.list = list;
		queue = Volley.newRequestQueue(context);
		imageLoader = new ImageLoader(queue, BitmapCache.getInstance());
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();

			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_pic_type2, null);
			viewHolder.title = (TextView) convertView
					.findViewById(R.id.title_item_pic);
			viewHolder.follow = (TextView) convertView
					.findViewById(R.id.follow_item_pic);
			viewHolder.imageLeft = (NetworkImageView) convertView
					.findViewById(R.id.left_item_pic);
			viewHolder.imageTop = (NetworkImageView) convertView
					.findViewById(R.id.top_item_pic);
			viewHolder.imageDown = (NetworkImageView) convertView
					.findViewById(R.id.down_item_pic);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		ImageDetail pic = list.get(position);

		viewHolder.title.setText(pic.getTitle());
		viewHolder.follow.setText(pic.getContent() + "跟帖");

		List<String> imgList = pic.getPic_list();
		for (int i = 0; i < imgList.size(); i++) {
			String imgString = imgList.get(i);
			if (imgString.length() > 0 && imgString != "null") {
				switch (i) {
				case 0:
					viewHolder.imageLeft.setImageUrl(imgString, imageLoader);
					viewHolder.imageLeft.setScaleType(ScaleType.CENTER_CROP);
					break;
				case 1:
					viewHolder.imageTop.setImageUrl(imgString, imageLoader);
					viewHolder.imageTop.setScaleType(ScaleType.CENTER_CROP);
					break;
				case 2:
					viewHolder.imageDown.setImageUrl(imgString, imageLoader);
					viewHolder.imageDown.setScaleType(ScaleType.CENTER_CROP);
					break;
				}
			}
		}
		return convertView;
	}

	class ViewHolder {
		TextView title;
		TextView follow;
		NetworkImageView imageLeft;
		NetworkImageView imageTop;
		NetworkImageView imageDown;
	}
}
