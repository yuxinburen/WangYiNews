package com.davie.fragment;

import java.util.ArrayList;
import java.util.List;

import com.davie.adapter.ViewPagerAdapter;
import com.davie.wangyinews.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class VideoFragment extends Fragment{
	
	/**
     * 导航部分
     */
    private LinearLayout topHost;
	private String[] tabHostArr;
	private HorizontalScrollView horizontalScrollView;
	
    /**
     * 下半部分
     */
	private ViewPager viewPager_main;
	private ViewPagerAdapter viewpagerAdapter;
	private List<Fragment> fragList;
    
	@Override
	public void onActivityCreated( Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_video, container,false);
		initView(view);
		initData();
		
		return view;
	}
	
	/**
	 * 初始化界面
	 * @param view
	 */
	public void initView(View view){
        
        /**
         * 导航部分
         */
        horizontalScrollView = (HorizontalScrollView) view.findViewById(R.id.scrollView);
		topHost = (LinearLayout) view.findViewById(R.id.topHost);
	
		/**
		 * 下半部分
		 */
		viewPager_main = (ViewPager) view.findViewById(R.id.viewPager_main);
	
		Log.i("VideoFragment", "..初始化界面");
	}
	
	/**
     * 初始化数据
     */
    private void initData(){
    	//Head部分.无数据,只需要监听事件
    	
    	//导航栏部分.
    	tabHostArr = getResources().getStringArray(R.array.tabVideo);
    	initTabHost();
    	loadTabHost();
    	
    	//下半部分
    	loadViewPager();
    	
    	Log.i("VideoFragment", "..初始化数据");
    	
    }
    
    /**
     * 初始化导航栏导航数据
     */
    private void initTabHost() {
		for (int i = 0; i < topHost.getChildCount(); i++) {
			TextView textView = (TextView) topHost.getChildAt(i);
			textView.setTextColor(Color.BLACK);
		}
	}
    
    /**
     * 加载数据TabHost数据
     */
    private void loadTabHost(){
    	for (int i = 0; i < tabHostArr.length; i++) {
			TextView txtHost = new TextView(getActivity());
			txtHost.setText(tabHostArr[i]);
			if (i == 0) {
				txtHost.setTextColor(Color.RED);
			}
			txtHost.setWidth(140);
			txtHost.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					initTabHost();
					TextView textView = (TextView) v;
					String text = textView.getText().toString().trim();
					for (int i = 0; i < tabHostArr.length; i++) {
						if (text.equals(tabHostArr[i])) {
							horizontalScrollView.scrollTo(i * 140, 0);
							viewPager_main.setCurrentItem(i);
						}
					}
					textView.setTextColor(Color.RED);
				}
			});
			Log.i("VideoFragment", tabHostArr+"");
			topHost.addView(txtHost);
			Log.i("VideoFragment", topHost.getChildCount()+"");
		}
    }
    
    /**
     * 下半部分数据初始化
     */
    private void loadViewPager() {
		fragList = new ArrayList<Fragment>();
		for (int i = 0; i < tabHostArr.length; i++) {
			VideoPage page = new VideoPage();
			Bundle bundle = new Bundle();
			bundle.putInt("tabIndex", i);
			page.setArguments(bundle);
			fragList.add(page);
		}
		viewpagerAdapter = new ViewPagerAdapter(getChildFragmentManager(),
				fragList, tabHostArr);
		viewPager_main.setAdapter(viewpagerAdapter);
	}
}
