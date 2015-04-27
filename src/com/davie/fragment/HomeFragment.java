package com.davie.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.common.image.BitmapCache;
import com.common.json.JsonTools;
import com.common.pulltorefresh.PullToRefreshBase.OnLastItemVisibleListener;
import com.common.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.common.pulltorefresh.PullToRefreshListView;
import com.common.url.Contants;
import com.davie.adapter.ImagePagerAdapter;
import com.davie.adapter.ListViewAdapter;
import com.davie.domain.DownNew;
import com.davie.domain.New;
import com.davie.domain.TopNew;
import com.davie.wangyinews.NewDetailActivity;
import com.davie.wangyinews.R;

public class HomeFragment extends Fragment {

	private int tabIndex;
	private int page;

	private ListView listView;
	private PullToRefreshListView ptfListView;

	private ViewPager viewPager;

	private RequestQueue queue;
	private StringRequest stringRequest;
	private ImageLoader imageLoader;
	private NetworkImageView networkImageView;
	
	private TextView txt_title_home;
	private LinearLayout linear_bottom_home;
	private List<View> viewList;

	private List<TopNew> topNews;
	private List<DownNew> downNews;
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		queue = Volley.newRequestQueue(getActivity());
		imageLoader = new ImageLoader(queue,BitmapCache.getInstance());
		Bundle bundle = getArguments();
		tabIndex = bundle.getInt("tabIndex");
		
	}

	private String path;
	private ListViewAdapter listViewAdapter;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_home, null);
		viewPager = (ViewPager) view.findViewById(R.id.viewPager_home);
		ptfListView = (PullToRefreshListView) view.findViewById(R.id.listview_frag);
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
	
	private void loadData(int tabIndex) {
		path = Contants.NEWS_LIST + (++tabIndex) + "&p=1";
		stringRequest = new StringRequest(
				path,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						if(response==null)
							throw new IllegalArgumentException(" the object response may be is null ");
						New new1 = JsonTools.pareJson(response);
						init(new1);
					}
				}, 
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(HomeFragment.this.getActivity(), "请求数据错误", Toast.LENGTH_SHORT).show();
					}
				});
		queue.add(stringRequest);
	}
	
	
	private void loadNetworkData() {
		path = Contants.NEWS_LIST + (++tabIndex) + "&p=" + page;
		StringRequest request = new StringRequest(path + page,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						if(response==null)throw new IllegalArgumentException(" the object response may be is null ");
						New new1 = JsonTools.pareJson(response);
						reloadListView(new1);
					}
				}, 
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(HomeFragment.this.getActivity(), "网络异常，数据加载失败！",
								Toast.LENGTH_SHORT).show();
					}
				});
		queue.add(request);
	}

	//下拉刷新
	private void reloadListView(New new1) {
		if (page == 1) {
			downNews.clear();
		}
		if (new1!=null) {
			
			downNews.addAll(new1.getDownNews());
		}
		listViewAdapter.notifyDataSetChanged();
		ptfListView.onRefreshComplete();
	}
	
	private void init(New new1) {
		if(new1 == null)
			throw new IllegalArgumentException(" the object new may be is null ");
		
		initViewPager(new1);
		
		//downNews
		downNews = new1.getDownNews();
		Log.i("HomeFragment", downNews+",,");
		listViewAdapter = new ListViewAdapter(getActivity(), downNews);
		listView.setAdapter(listViewAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				DownNew downNew = downNews.get(position);
				Bundle bundle = new Bundle();
				String idString = downNew.getId();
				String type = "news";
				bundle.putString("id", idString);
				bundle.putString("type", type);
				Intent intent = new Intent();
				intent.setClass(HomeFragment.this.getActivity(), NewDetailActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
				Log.i("HomeFragment", "ListView的Item的点击");
			}
		});
		
	}
	
	public void initViewPager(New new1){
		viewList = new ArrayList<View>();
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.viewpager, null);
		txt_title_home = (TextView) view.findViewById(R.id.txt_title_home);
		linear_bottom_home = (LinearLayout) view
				.findViewById(R.id.linear_bottom_home);
		topNews = new ArrayList<TopNew>();
		topNews = new1.getTopNews();
		for (TopNew topNew:topNews) {
			txt_title_home.setText(topNew.getTitle());
			ImageView imageView = new ImageView(getActivity());
			imageView.setImageResource(R.drawable.page);
			linear_bottom_home.addView(imageView);
			String picStr = topNew.getCover_pic();
			if(picStr.length()>0&&!picStr.equals("null")){
				networkImageView = new NetworkImageView(getActivity());
				networkImageView.setScaleType(ScaleType.CENTER_CROP);
				networkImageView.setImageUrl(picStr, imageLoader);
			}
			Log.i("HomeFragment", txt_title_home.getText().toString());
			viewList.add(networkImageView);
		}
		
		Log.i("HomeFragemnt", viewList.size()+",");
		ImagePagerAdapter pagerAdapter = new ImagePagerAdapter(viewList);
		viewPager.setAdapter(pagerAdapter);
		
		//初始化图片点
	
		//设置viewPager变化监听
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				//viewPager 变化  点图片和标题都跟着变
				txt_title_home.setText(topNews.get(position).getTitle());
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
	}
}
