package com.davie.adapter;

import java.util.List;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.common.image.BitmapCache;
import com.davie.domain.DownNew;
import com.davie.wangyinews.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter{

	private Context context;
	private List<DownNew> list;
	private RequestQueue queue;
	private ImageLoader imageLoader;
	
	public ListViewAdapter(Context context,List<DownNew> list) {
		super();
		this.context = context;
		this.list = list;
		queue = Volley.newRequestQueue(context);
		imageLoader = new ImageLoader(queue,BitmapCache.getInstance());
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
		if(convertView==null){
			viewHolder = new ViewHolder();
			
			convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_home, null);
			viewHolder.title = (TextView) convertView.findViewById(R.id.txt_title_item);
			viewHolder.content = (TextView) convertView.findViewById(R.id.txt_content_item);
			viewHolder.comment = (TextView) convertView.findViewById(R.id.txt_comment_item);
			viewHolder.imageView = (NetworkImageView) convertView.findViewById(R.id.image_item);
			
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
			
		
		DownNew downNew = list.get(position);
		
		viewHolder.title.setText(downNew.getTitle());
		viewHolder.content.setText(downNew.getContent());
		viewHolder.comment.setText(downNew.getComment_total()+"跟帖");
		String pic = downNew.getCover_pic();
		if(pic.length()>0&&!pic.equals("null")){
			viewHolder.imageView.setImageUrl(pic, imageLoader);
			viewHolder.imageView.setScaleType(ScaleType.CENTER_CROP);
		}
		return convertView;
	}
	
	class ViewHolder {
		TextView title;
		TextView content;
		TextView comment;
		NetworkImageView imageView;
	}
}
