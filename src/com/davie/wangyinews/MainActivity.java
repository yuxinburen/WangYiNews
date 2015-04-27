package com.davie.wangyinews;


import android.R.integer;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.common.utils.SharedPreferencesHelper;
import com.davie.fragment.FollowFragment;
import com.davie.fragment.PictureFragment;
import com.davie.fragment.ReadFragment;
import com.davie.fragment.MainFragment;
import com.davie.fragment.VideoFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MainActivity extends FragmentActivity implements OnClickListener {
	private SlidingMenu sMenu;
	private PopupWindow popupWindow;
	private View popupView;
	
	private SharedPreferencesHelper spHelper;
	private TextView home_channel_bar_portrait;
	
	//请求码
	private static final int LOGIN = 101;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initLeftMenu();//初始化左边菜单
		initRightMenu();//初始化右边菜单
		
		initPopupWindow();//初始化popupwindow
		
		MainFragment mainFragment = new MainFragment();
		getSupportFragmentManager().beginTransaction().replace(R.id.container_main, mainFragment, "MainFragment").commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * 方法D 回调
	 * @param fragment
	 */
	public void switchFragment(Fragment fragment) {
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container_main, fragment).commit();
		// 自动切换
		sMenu.toggle();
	}
	
	private void initLeftMenu() {
		View view = LayoutInflater.from(this).inflate(R.layout.menu, null);
		TextView img_news,img_reading,img_picture,img_video,img_follow;
		img_news = (TextView) view.findViewById(R.id.img_news);
		img_reading = (TextView) view.findViewById(R.id.img_reading);
		img_picture = (TextView) view.findViewById(R.id.img_picture);
		img_video = (TextView) view.findViewById(R.id.img_video);
		img_follow = (TextView) view.findViewById(R.id.img_follow);
		
		img_news.setOnClickListener(this);
		img_reading.setOnClickListener(this);
		img_picture.setOnClickListener(this);
		img_video.setOnClickListener(this);
		img_follow.setOnClickListener(this);
		
		sMenu = new SlidingMenu(this);
		sMenu.setMode(SlidingMenu.LEFT_RIGHT);
		sMenu.setMenu(view);
		sMenu.setBehindWidth(getResources().getDisplayMetrics().widthPixels *3/5);
		sMenu.setShadowDrawable(R.drawable.shadow);
		sMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
	
	}
	
	private void initRightMenu(){
		View view = LayoutInflater.from(this).inflate(R.layout.right_menu, null);
		
		sMenu.setSecondaryMenu(view);
		sMenu.setSecondaryShadowDrawable(R.drawable.shadow);
		sMenu.setBehindWidth(getResources().getDisplayMetrics().widthPixels*4/5);
		
		sMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		
		TextView night_user_reading_circle,
		plugin_icon_app,
		night_usercenter_hd_edit,
		night_pluginboard_icon_headline,
		night_pluginboard_icon_message,
		night_pluginboard_icon_mailbox,
		night_pluginboard_icon_public,
		night_icon_reward,txt_font_type,txt_font_size,txt_cache,txt_feedback,txt_exit;
		
		home_channel_bar_portrait = (TextView) view.findViewById(R.id.home_channel_bar_portrait);
		night_user_reading_circle = (TextView) view.findViewById(R.id.night_user_reading_circle);
		plugin_icon_app = (TextView) view.findViewById(R.id.plugin_icon_app);
		night_usercenter_hd_edit = (TextView) view.findViewById(R.id.night_usercenter_hd_edit);
		night_pluginboard_icon_headline = (TextView) view.findViewById(R.id.night_pluginboard_icon_headline);
		night_pluginboard_icon_message = (TextView) view.findViewById(R.id.night_pluginboard_icon_message);
		night_pluginboard_icon_mailbox = (TextView) view.findViewById(R.id.night_pluginboard_icon_mailbox);
		night_pluginboard_icon_public = (TextView) view.findViewById(R.id.night_pluginboard_icon_public);
		night_icon_reward = (TextView) view.findViewById(R.id.night_icon_reward);
		txt_exit = (TextView) view.findViewById(R.id.txt_exit);
		
		txt_font_type = (TextView) view.findViewById(R.id.txt_font_type);
		txt_font_size = (TextView) view.findViewById(R.id.txt_font_size);
		txt_cache = (TextView) view.findViewById(R.id.txt_cache);
		txt_feedback = (TextView) view.findViewById(R.id.txt_feedback);	
		
		
		home_channel_bar_portrait.setOnClickListener(this);
		night_user_reading_circle.setOnClickListener(this);
		plugin_icon_app.setOnClickListener(this);
		night_usercenter_hd_edit.setOnClickListener(this);
		night_pluginboard_icon_headline.setOnClickListener(this);
		night_pluginboard_icon_message.setOnClickListener(this);
		night_pluginboard_icon_mailbox.setOnClickListener(this);
		night_pluginboard_icon_public.setOnClickListener(this);
		night_icon_reward.setOnClickListener(this);
		
		txt_font_type.setOnClickListener(this);
		txt_font_size.setOnClickListener(this);
		txt_cache.setOnClickListener(this);
		txt_feedback.setOnClickListener(this);
		txt_exit.setOnClickListener(this);
		
	}

	
	/**
	 * Head部分的popupwindow弹窗
	 */
	private void initPopupWindow(){
		popupView = LayoutInflater.from(this).inflate(R.layout.poputwindow_fragment, null);
		popupWindow = new PopupWindow(popupView,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
	}
	
	/**
	 * 添加事件监听
	 */ 
	@Override
	public void onClick(View view) {
		Intent intent = new Intent();
		switch (view.getId()) {
		case R.id.img_sliding:
			sMenu.toggle();
			break;
		case R.id.img_usericon:
			sMenu.showSecondaryMenu();
			break;
		case R.id.img_more:
			if (popupWindow.isShowing()) {
				popupWindow.dismiss();
			} else {
				//判断当前是否已经登录
				if(isSignIn()){//已经登录过
					//填充用户的数据
					reLoadRightMenu();
				}
				popupWindow.showAtLocation(popupView, Gravity.LEFT, getResources().getDisplayMetrics().widthPixels, -100);
			}
			break;
		case R.id.img_head_more:
			Toast.makeText(this, "popupwindow", Toast.LENGTH_SHORT).show();
			break;
		case R.id.img_news://新闻
			MainFragment homeFragment = new MainFragment();
			switchFragment(homeFragment);
			break;
		case R.id.img_reading://阅读
			ReadFragment readFragment = new ReadFragment();
			switchFragment(readFragment);
			break;
		case R.id.img_picture://图片
			PictureFragment pictureFragment = new PictureFragment();
			switchFragment(pictureFragment);
			break;
		case R.id.img_video://视频
			VideoFragment videoFragment = new VideoFragment();
			switchFragment(videoFragment);
			break;
		case R.id.img_follow://跟帖
			FollowFragment followFragment = new FollowFragment();
			switchFragment(followFragment);
			break;
		case R.id.home_channel_bar_portrait:
			if(!isSignIn()){
				intent.setClass(this, LoginActivity.class);
				startActivityForResult(intent, LOGIN);
			}
			break;
		case R.id.night_user_reading_circle:
			if(isSignIn()){
				intent.setClass(this, UpdateActivity.class);
				startActivity(intent);
			}else {
				Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
				intent.setClass(this, LoginActivity.class);
				startActivity(intent);
			}
			
			break;
		case R.id.plugin_icon_app://收藏
			if(isSignIn()){
				intent.setClass(this, FavoriteActivity.class);
				startActivity(intent);
			}else {
				Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
				intent.setClass(this, LoginActivity.class);
				startActivity(intent);
			}
			break;
		case R.id.night_usercenter_hd_edit:
			if(isSignIn()){
				intent.setClass(this, MyCommentsActivity.class);
				startActivity(intent);
			}else {
				Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
				intent.setClass(this, LoginActivity.class);
				startActivity(intent);
			}
			break;
		case R.id.txt_font_type:
			if(isSignIn()){
				intent.setClass(this, FontTypeActivity.class);
				startActivity(intent);
			}else {
				Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
				intent.setClass(this, LoginActivity.class);
				startActivity(intent);
			}
			break;
		case R.id.txt_font_size:
			if(isSignIn()){
				intent.setClass(this, FontActivity.class);
				startActivity(intent);
			}else {
				Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
				intent.setClass(this, LoginActivity.class);
				startActivity(intent);
			}
			break;
		case R.id.txt_cache:
			break;
		case R.id.txt_feedback:
			if(isSignIn()){
				intent.setClass(this, FeedBackActivity.class);
				startActivity(intent);
			}else {
				Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
				intent.setClass(this, LoginActivity.class);
				startActivity(intent);
			}
			break;
		case R.id.txt_exit:
			if(isSignIn()){
				spHelper.updateState(false);//将资料状态设置为不可读
				System.exit(0);
			}
			break;
		case R.id.night_pluginboard_icon_headline:
			Toast.makeText(this, "上头条", Toast.LENGTH_SHORT).show();
			break;
		case R.id.night_pluginboard_icon_message:
			Toast.makeText(this, "暂无消息", Toast.LENGTH_SHORT).show();
			break;
		case R.id.night_pluginboard_icon_mailbox:
			Toast.makeText(this, "该版本暂不支持邮件协议,请关注后续版本更新！", Toast.LENGTH_SHORT).show();
			break;
		case R.id.night_pluginboard_icon_public:
			Toast.makeText(this, "别当键盘手,去做点实际的吧^_^", Toast.LENGTH_SHORT).show();
			break;
		case R.id.night_icon_reward:
			Toast.makeText(this, "根据国家相关规定,彩票业务暂未开放,请谅解!", Toast.LENGTH_SHORT).show();
			break;
		}
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==LOGIN&&resultCode==RESULT_OK){
			String userName = data.getStringExtra("username");
			home_channel_bar_portrait.setText(userName);
		}
	}
	
	private boolean isSignIn(){
		boolean ret = false;
		spHelper = new SharedPreferencesHelper(this);
		ret = spHelper.getState();
		return ret;
	}
	
	/**
	 * 填充用户数据到右菜单中
	 */
	private void reLoadRightMenu(){
		String imageurl = spHelper.getHeaderImg();
		String username = spHelper.getUsername();
		if(imageurl!=null){
			
		}
		home_channel_bar_portrait.setText(username);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean ret = super.onTouchEvent(event);
		if (popupWindow.isShowing()) {
			ret = true;
			popupWindow.dismiss();
		}
		return ret;
	}
}
