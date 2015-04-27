package com.davie.fragment;

import java.util.ArrayList;
import java.util.List;

import com.davie.adapter.ViewPagerAdapter;
import com.davie.wangyinews.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainFragment extends Fragment{
	
	/**
	 * Head部分
	 */
	private ImageButton img_sliding;
    private TextView txt_title;
    private ImageButton img_usericon;
    private ImageButton img_more;
    
    
    /**
     * 导航部分
     */
    private LinearLayout topHost;
	private String[] tabHostArr;
	private HorizontalScrollView horizontalScrollView;
	private ImageButton img_head_more;
	
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
		View view = inflater.inflate(R.layout.fragment_main, container,false);
		initView(view);//初始化界面
		initData();//初始化数据
		return view;
	}
	
	/**
	 * 初始化界面
	 * @param view
	 */
	public void initView(View view){
		/**
		 * 顶部部分
		 */
        img_sliding = (ImageButton) view.findViewById(R.id.img_sliding);
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        img_usericon = (ImageButton) view.findViewById(R.id.img_usericon);
        img_more = (ImageButton) view.findViewById(R.id.img_more);
         
        /**
         * 导航部分
         */
        img_head_more = (ImageButton) view.findViewById(R.id.img_head_more);
        horizontalScrollView = (HorizontalScrollView) view.findViewById(R.id.scrollView);
		topHost = (LinearLayout) view.findViewById(R.id.topHost);
	
		/**
		 * 下半部分
		 */
		viewPager_main = (ViewPager) view.findViewById(R.id.viewPager_main);
		
	}
	
	/**
     * 初始化数据
     */
    private void initData(){
    	//Head部分.无数据,只需要监听事件
    	
    	//导航栏部分.
    	tabHostArr = getResources().getStringArray(R.array.tabhost);
    	initTabHost();
    	loadTabHost();
    	
    	//下半部分
    	loadViewPager();
    	
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
			txtHost.setWidth(120);
			txtHost.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					initTabHost();
					TextView textView = (TextView) v;
					String text = textView.getText().toString().trim();
					for (int i = 0; i < tabHostArr.length; i++) {
						if (text.equals(tabHostArr[i])) {
							horizontalScrollView.scrollTo(i * 120, 0);
							viewPager_main.setCurrentItem(i);
						}
					}
					textView.setTextColor(Color.RED);
				}
			});
			topHost.addView(txtHost);
		}
    }
    
    /**
     * 下半部分数据初始化
     */
    private void loadViewPager() {
		fragList = new ArrayList<Fragment>();
		for (int i = 0; i < tabHostArr.length; i++) {
			HomeFragment fragment = new HomeFragment();
			Bundle bundle = new Bundle();
			bundle.putInt("tabIndex", i);
			fragment.setArguments(bundle);
			fragList.add(fragment);
		}
		viewpagerAdapter = new ViewPagerAdapter(getChildFragmentManager(),
				fragList, tabHostArr);
		viewPager_main.setAdapter(viewpagerAdapter);
		viewPager_main.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				horizontalScrollView.scrollTo(position * 120, 0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {}
		});
	}
    
	/**
	 * 添加事件监听
	 */
	public void onClick(View view){
		switch (view.getId()) {
		case R.id.img_sliding:
			Toast.makeText(getActivity(), "弹出抽屉", Toast.LENGTH_SHORT).show();
			
			break;
		case R.id.img_usericon:
			Toast.makeText(getActivity(), "头像监听", Toast.LENGTH_SHORT).show();
			
			break;
		case R.id.img_more:
			Toast.makeText(getActivity(), "菜单监听", Toast.LENGTH_SHORT).show();
			
			break;
		case R.id.img_head_more:
			Toast.makeText(getActivity(), "popupwindow", Toast.LENGTH_SHORT).show();
			
			break;
		}
    }

}
