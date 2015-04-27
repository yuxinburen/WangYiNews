package com.davie.wangyinews;

import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
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
import com.common.utils.SharedPreferencesHelper;
import com.davie.domain.ImageDetail;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


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
	private TextView txt_send;// 提交评论
	private EditText edt_comment;

	// 该篇详细是否已被收藏
	private boolean isCollect = false;

	private MySQLiteOpenHelper helper;

	private ImageDetail imageDetail;
	private String path;

	private SharedPreferencesHelper spHelper;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_image);
		spHelper = new SharedPreferencesHelper(this);
		intent = new Intent();

		helper = new MySQLiteOpenHelper(this);
		queue = Volley.newRequestQueue(this);
		imageLoader = new ImageLoader(queue, BitmapCache.getInstance());

		Bundle bundle = getIntent().getExtras();
		String id = bundle.getString("id");
		initView();

		 initData(id);//初始化数据

		load(id);
	}

	public void initData(String title_id) {
		String sql = " select title_id from tb_myfavorite where title_id = ? ";
		int count = helper.selectCount(sql, new String[] { title_id });
		if (count > 0) {
			isCollect = true;
			img_collect_picture
					.setImageResource(R.drawable.icon_service_already);
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
		txt_send = (TextView) findViewById(R.id.txt_send);
		edt_comment = (EditText) findViewById(R.id.edt_comment);
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
		txt_send.setOnClickListener(this);// 评论发送
	}

	public void load(String id) {
		path = Contants.NEWS_DETAIL + id;
		stringRequest = new StringRequest(path,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						if (response == null)
							throw new IllegalArgumentException(
									" the object response may be is null ");
						imageDetail = JsonTools.parseNewDetail(response);
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
			// 动画效果
			break;
		case R.id.img_collect_picture:
			collect();
			break;
		case R.id.img_share_picture:
			// 分享
			showShare();
			break;
		case R.id.txt_send:
			// 发送评论跟帖
			sendComment();
			break;
		}
	}

	/**
	 * 文章明细收藏
	 */
	private void collect() {
		if (!isSignIn()) {
			Toast.makeText(this, "请先登录", Toast.LENGTH_LONG).show();
			intent.setClass(this, LoginActivity.class);
			startActivity(intent);
			return;
		}

//		String id = imageDetail.getNews_id();
		isCollect = !isCollect;

	}

	/**
	 * 发送评论跟帖
	 */
	private void sendComment() {
		if (!isSignIn()) {
			Toast.makeText(NewDetailActivity.this, "请先登录", Toast.LENGTH_LONG)
					.show();
			intent.setClass(this, LoginActivity.class);
			startActivity(intent);
			return;
		}

		String commnet = edt_comment.getText().toString().trim();
		if (commnet.length() > 0) {
			String token = spHelper.getToken();
			String id = imageDetail.getNews_id();
			RequestParams params = new RequestParams();
			params.put("content", commnet);
			params.put("token", token);
			params.put("news_id", id);

			new AsyncHttpClient().get(Contants.COMMENT, params,
					new AsyncHttpResponseHandler() {
						@Override
						public void onFailure(int arg0, Header[] arg1,
								byte[] arg2, Throwable arg3) {
							Toast.makeText(NewDetailActivity.this, "评论失败",
									Toast.LENGTH_LONG).show();
						}

						@Override
						public void onSuccess(int arg0, Header[] arg1,
								byte[] response) {
							int code = 0;
							try {
								JSONObject object = new JSONObject(new String(
										response));
								code = object.optInt("code");
								if (code == 200) {
									Toast.makeText(NewDetailActivity.this,
											"评论成功", Toast.LENGTH_LONG).show();
									edt_comment.setText("");
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
		}

	}

	/**
	 * 分享的方法
	 */
	private void showShare() {
		ShareSDK.initSDK(this);
		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();

		oks.setTitle(getString(R.string.share));
		oks.setTitleUrl("http://sharesdk.cn");
		oks.setText("我是分享文本");
		oks.setImagePath("/sdcard/test.jpg");// 确保SDcard下面存在此张图片
		oks.setUrl("http://sharesdk.cn");
		oks.setComment("我是测试评论文本");
		oks.setSite(getString(R.string.app_name));
		oks.setSiteUrl("http://sharesdk.cn");

		// 启动分享GUI
		oks.show(this);
	}

	/**
	 * 判断是否登录
	 * 
	 * @return
	 */
	private boolean isSignIn() {
		boolean ret = false;
		ret = spHelper.getState();
		return ret;
      }
}