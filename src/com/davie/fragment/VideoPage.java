package com.davie.fragment;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.common.json.JsonTools;
import com.common.url.Contants;
import com.davie.adapter.VideoAdapter;
import com.davie.domain.Video;
import com.davie.wangyinews.NewDetailActivity;
import com.davie.wangyinews.R;
import com.davie.wangyinews.VideoPlayActivity;

public class VideoPage  extends Fragment {

	private int tabIndex;

	private ListView listView;

	private RequestQueue queue;
	private StringRequest stringRequest;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		queue = Volley.newRequestQueue(getActivity());
		Bundle bundle = getArguments();
		tabIndex = bundle.getInt("tabIndex");
		
	}

	private String path;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.page_video, null);
		listView = (ListView) view.findViewById(R.id.listview_video);
		loadData(tabIndex);
		
		return view;
	}
	
	private void loadData(int tabIndex) {
		path = Contants.VIDEO + (++tabIndex);
		stringRequest = new StringRequest(
				path,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						if(response==null)
							throw new IllegalArgumentException(" the object response may be is null ");
						List<Video> videos = JsonTools.parseVideo(response);
						init(videos);
					}
				}, 
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(VideoPage.this.getActivity(), "请求数据错误", Toast.LENGTH_SHORT).show();
					}
				});
		queue.add(stringRequest);
	}
	
	private void init(final List<Video> videos) {
		if(videos == null)
			throw new IllegalArgumentException(" the object piclist may be is null ");
		
		VideoAdapter picAdapter = new VideoAdapter(getActivity(), videos);
		listView.setAdapter(picAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Video video = videos.get(position);
				String url = video.getVideo_url();
				String type = "video";
				Bundle bundle = new Bundle();
				bundle.putString("url", url);
				bundle.putString("title", video.getTitle());
				bundle.putString("type", type);
				Intent intent = new Intent();
				intent.setClass(VideoPage.this.getActivity(), VideoPlayActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
				Log.i("VideoPage", "ListView的Item的点击");
			}
		});
	}
}

