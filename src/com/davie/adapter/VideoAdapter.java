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
import com.davie.domain.Video;
import com.davie.wangyinews.R;

public class VideoAdapter extends BaseAdapter {

	private Context context;
	private List<Video> list;
	private RequestQueue queue;
	private ImageLoader imageLoader;

	public VideoAdapter(Context context, List<Video> list) {
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
					R.layout.item_video, null);
			viewHolder.title = (TextView) convertView
					.findViewById(R.id.txt_title_video);
			viewHolder.follow = (TextView) convertView
					.findViewById(R.id.txt_play_video);
			viewHolder.imageTop = (NetworkImageView) convertView
					.findViewById(R.id.image_item_video);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		Video video = list.get(position);

		viewHolder.title.setText(video.getTitle());
		viewHolder.follow.setText(video.getPlay_num() + "次播放");

		String imgList = video.getPic();
		if (imgList.length() > 0 && imgList != "null") {
			viewHolder.imageTop.setImageUrl(imgList, imageLoader);
			viewHolder.imageTop.setScaleType(ScaleType.CENTER_CROP);
		}
		return convertView;
	}

	class ViewHolder {
		TextView title;
		TextView follow;
		NetworkImageView imageTop;
	}
}
