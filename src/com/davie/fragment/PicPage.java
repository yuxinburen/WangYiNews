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
import com.common.pulltorefresh.PullToRefreshListView;
import com.common.pulltorefresh.PullToRefreshBase.OnLastItemVisibleListener;
import com.common.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.common.url.Contants;
import com.davie.adapter.PicAdapter;
import com.davie.domain.Pic;
import com.davie.wangyinews.NewDetailActivity;
import com.davie.wangyinews.R;

public class PicPage extends Fragment {

	private int tabIndex;
	private int page;

	private ListView listView;
	private PullToRefreshListView ptfListView;

	private RequestQueue queue;
	private StringRequest stringRequest;
	
	private List<Pic> piclist;

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
	private PicAdapter picAdapter;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.page_pic, null);
		ptfListView = (PullToRefreshListView) view.findViewById(R.id.listview_pic);
		listView = ptfListView.getRefreshableView();
		
		// 给PullToRefreshListView设置监听器
				ptfListView.setOnRefreshListener(new OnRefreshListener() {
					@Override
					public void onRefresh() {
						page = 1;
						loadNetworkData();
					}
				});
				
				ptfListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
					@Override
					public void onLastItemVisible() {
						page++;
						loadNetworkData();
					}
				});
		
		loadData(tabIndex);
		
		return view;
	}
	
	
	private void loadNetworkData() {
		path = Contants.NEWS_LIST + (++tabIndex) + "&p=" + page;
		StringRequest request = new StringRequest(path + page,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						if(response==null)
							throw new IllegalArgumentException(" the object response may be is null ");
						List<Pic> piclist = JsonTools.parsePic(response);
						reloadListView(piclist);
					}
				}, 
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(PicPage.this.getActivity(), "网络异常，数据加载失败！",
								Toast.LENGTH_SHORT).show();
					}
				});
		queue.add(request);
	}

	//下拉刷新
	private void reloadListView(List<Pic> piclist) {
		if (page == 1) {
			piclist.clear();
		}
		if(piclist!=null){
			piclist.addAll(piclist);
		}
		picAdapter.notifyDataSetChanged();
		ptfListView.onRefreshComplete();
	}
	
	private void loadData(int tabIndex) {
		path = Contants.PIC_NEWS + (++tabIndex);
		stringRequest = new StringRequest(
				path,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						if(response==null)
							throw new IllegalArgumentException(" the object response may be is null ");
						piclist = JsonTools.parsePic(response);
						init(piclist);
					}
				}, 
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(PicPage.this.getActivity(), "请求数据错误", Toast.LENGTH_SHORT).show();
					}
				});
		queue.add(stringRequest);
	}
	
	private void init(final List<Pic> piclist) {
		if(piclist == null)
			throw new IllegalArgumentException(" the object piclist may be is null ");
		
		picAdapter = new PicAdapter(getActivity(), piclist);
		listView.setAdapter(picAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Pic pic = piclist.get(position);
				String idString = pic.getId();
				String type = "image";
				Bundle bundle = new Bundle();
				bundle.putString("id", idString);
				bundle.putString("type", type);
				Intent intent = new Intent();
				intent.setClass(PicPage.this.getActivity(), NewDetailActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
				Log.i("PicPage", "ListView的Item的点击");
			}
		});
	}
}

