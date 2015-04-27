package com.davie.wangyinews;

import java.util.List;


import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.common.db.MySQLiteOpenHelper;
import com.common.image.BitmapCache;
import com.common.json.JsonTools;
import com.common.url.Contants;
import com.davie.domain.DownNew;
import com.davie.domain.ImageDetail;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

/**
 * 新闻详情
 * @author aaa
 *
 */
public class NewDetailActivity extends Activity implements OnClickListener {

	private RequestQueue queue;
	private StringRequest stringRequest;
	private ImageLoader imageLoader;

	/**
	 * 顶部的收藏和分享
	 */
	private ImageView img_back_picture;// 返回
	private ImageView img_collect_picture;// 收藏
	private ImageView img_share_picture;// 分享

	/**
	 * 控件
	 */
	private TextView title;
	private TextView time;
	private TextView content;
	private NetworkImageView imageView;
	private NetworkImageView pic1, pic2, pic3;
	
	//该篇详细是否已被收藏
	private boolean isCollect = false;
	
	private MySQLiteOpenHelper helper ;
	
	private ImageDetail imageDetail;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_image);

		helper = new MySQLiteOpenHelper(this);
		queue = Volley.newRequestQueue(this);
		imageLoader = new ImageLoader(queue, BitmapCache.getInstance());
		
		Bundle bundle = getIntent().getExtras();
		DownNew downNew = (DownNew) bundle.getSerializable("DownNew");
		String id = downNew.getId();
		
		initView();
		initData(id);
		
		load(id);
	}
	
	public void initData(String title_id){
		String sql = " select title_id from tb_myfavorite where title_id = ? ";
		int count = helper.selectCount(sql, new String[] { title_id });
		if (count > 0) {
			isCollect = true;
			img_collect_picture.setImageResource(R.drawable.icon_service_already);
		} else {
			img_collect_picture.setImageResource(R.drawable.icon_service_no);
			isCollect = false;
		}
	}
	
	public void initView() {
		title = (TextView) findViewById(R.id.title_detail_news);
		time = (TextView) findViewById(R.id.time_detail_news);
		content = (TextView) findViewById(R.id.content_detail_news);
		imageView = (NetworkImageView) findViewById(R.id.cover_detail_news);
		pic1 = (NetworkImageView) findViewById(R.id.pic1_detail_news);
		pic2 = (NetworkImageView) findViewById(R.id.pic2_detail_news);
		pic3 = (NetworkImageView) findViewById(R.id.pic3_detail_news);

		/**
		 * 顶部
		 */
		img_back_picture = (ImageView) findViewById(R.id.img_back_picture);
		img_collect_picture = (ImageView) findViewById(R.id.img_collect_picture);
		img_share_picture = (ImageView) findViewById(R.id.img_share_picture);

		/**
		 * 设置监听
		 */
		img_back_picture.setOnClickListener(this);
		img_collect_picture.setOnClickListener(this);
		img_share_picture.setOnClickListener(this);
	}

	public void load(String id) {
		String path = Contants.NEWS_DETAIL + id;
		stringRequest = new StringRequest(path,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						if (response == null)
							throw new IllegalArgumentException(
									" the object response may be is null ");
						imageDetail = JsonTools
								.parseNewDetail(response);
						handle(imageDetail);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(NewDetailActivity.this, "请求数据错误",
								Toast.LENGTH_SHORT).show();
					}
				});
		queue.add(stringRequest);
	}

	private void handle(ImageDetail imageDetail) {
		title.setText(imageDetail.getTitle());
		time.setText(imageDetail.getCreate_time());
		content.setText(imageDetail.getContent());
		String pic = imageDetail.getCover_pic();
		if (pic != null && !pic.equals("null")) {
			imageView.setImageUrl(pic, imageLoader);
		}
		List<String> pic_list = imageDetail.getPic_list();
		int num = pic_list.size();
		if (num > 0) {
			for (int i = 0; i < num; i++) {
				String path = pic_list.get(i);
				if (path != null && !path.equals("null")) {
					switch (num) {
					case 0:
						pic1.setImageUrl(pic, imageLoader);
						break;

					case 1:
						pic2.setImageUrl(pic, imageLoader);
						break;
					case 2:
						pic3.setImageUrl(pic, imageLoader);
						break;
					}
				}
			}
		}
	}

	/**
	 * 点击事件
	 */
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.img_back_picture:
			finish();
			//动画效果
			break;
		case R.id.img_collect_picture:
			//收藏
			String id = imageDetail.getNews_id();
			String content = imageDetail.getContent();
			String title = imageDetail.getTitle();
			String imgUrl = imageDetail.getCover_pic();
			isCollect = !isCollect;
			if(isCollect){
				String sql = " insert into tb_myfavorite(image,category,tabIndex,title_id,title) values(?,?,?,?,?) ";
				boolean flag = helper.execData(sql, new String[] { imgUrl,"", "",
						id, title });
				if (flag) {
					img_collect_picture.setImageResource(R.drawable.icon_service_already);
					Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT)
							.show();
				}
			}else {
				String sql = " delete from tb_myfavorite where title_id = ? ";
				boolean flag = helper.execData(sql, new String[] { id });
				if (flag) {
					img_collect_picture.setImageResource(R.drawable.icon_service_no);
					Toast.makeText(this, "取消收藏", Toast.LENGTH_SHORT).show();
				}
			}
			break;
		case R.id.img_share_picture:
			//分享
			Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
			showShare();
			break;
		}
	}
	
	
	/**
	 * 分享的方法
	 */
	private void showShare() {
		 ShareSDK.initSDK(this);
		 OnekeyShare oks = new OnekeyShare();
		 //关闭sso授权
		 oks.disableSSOWhenAuthorize(); 

		// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
		 //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
		 // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		 oks.setTitle(getString(R.string.share));
		 // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		 oks.setTitleUrl("http://sharesdk.cn");
		 // text是分享文本，所有平台都需要这个字段
		 oks.setText("我是分享文本");
		 // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		 oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
		 // url仅在微信（包括好友和朋友圈）中使用
		 oks.setUrl("http://sharesdk.cn");
		 // comment是我对这条分享的评论，仅在人人网和QQ空间使用
		 oks.setComment("我是测试评论文本");
		 // site是分享此内容的网站名称，仅在QQ空间使用
		 oks.setSite(getString(R.string.app_name));
		 // siteUrl是分享此内容的网站地址，仅在QQ空间使用
		 oks.setSiteUrl("http://sharesdk.cn");

		// 启动分享GUI
		 oks.show(this);
		 }
}
