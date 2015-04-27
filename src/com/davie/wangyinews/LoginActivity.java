package com.davie.wangyinews;

import java.util.HashMap;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tpl.OnLoginListener;
import cn.sharesdk.tpl.SignupPage;
import cn.sharesdk.tpl.ThirdPartyLogin;
import cn.sharesdk.tpl.UserInfo;

import com.common.json.JsonTools;
import com.common.url.Contants;
import com.common.utils.SharedPreferencesHelper;
import com.davie.domain.Login;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class LoginActivity extends Activity implements OnClickListener,
		PlatformActionListener, Callback {

	private TextView txt_show;
	private EditText edt_usernam, edt_password;
	private Button btn_submit;
	private TextView txt_land_sina, txt_land_tencent;
	private TextView txt_register;

	private static final int MSG_AUTH_CANCEL = 2;
	private static final int MSG_AUTH_ERROR = 3;
	private static final int MSG_AUTH_COMPLETE = 4;

	private static String APPKEY = "27fe7909f8e8";
	// 填写从短信SDK应用后台注册得到的APPSECRET
	private static String APPSECRET = "3c5264e7e05b8860a9b98b34506cfa6e";

	private OnLoginListener signupListener;


	private Handler handler;

	private RequestParams params;
	
	
	private SharedPreferencesHelper spHelper;
	
	/** 设置授权回调，用于判断是否进入注册 */
	public void setOnLoginListener(OnLoginListener l) {
		this.signupListener = l;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		handler = new Handler(this);

		ThirdPartyLogin tpl = new ThirdPartyLogin();
		tpl.setSMSSDKAppkey(APPKEY, APPSECRET);
		tpl.setOnLoginListener(new OnLoginListener() {
			public boolean onSignin(String platform, HashMap<String, Object> res) {
				// 在这个方法填写尝试的代码，返回true表示还不能登录，需要注册
				// 此处全部给回需要注册
				// signin代表登陆
				return true;
			}

			public boolean onSignUp(UserInfo info) {// sign up 代表注册
				// 填写处理注册信息的代码，返回true表示数据合法，注册页面可以关闭
				LoginActivity.this.finish();// 关闭LoginActivity页面,跳转到个人中心

				return true;
			}
		});

		init();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	private void init() {
		edt_usernam = (EditText) findViewById(R.id.edt_username_login);
		edt_password = (EditText) findViewById(R.id.edt_password_login);
		btn_submit = (Button) findViewById(R.id.btn_submit_login);
		txt_land_sina = (TextView) findViewById(R.id.txt_land_sina);
		txt_land_tencent = (TextView) findViewById(R.id.txt_land_tencent);
		txt_register = (TextView) findViewById(R.id.txt_register);
		txt_show = (TextView) findViewById(R.id.txt_show);

		btn_submit.setOnClickListener(this);
		txt_land_sina.setOnClickListener(this);
		txt_land_tencent.setOnClickListener(this);
		txt_register.setOnClickListener(this);
		txt_show.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.txt_show:
			finish();
			break;
		case R.id.btn_submit_login:
			Toast.makeText(this, " The Action is login ", Toast.LENGTH_SHORT).show();
			login();
			break;
		case R.id.txt_land_sina:
			/**
			 * 新浪微博第三方登陆
			 */
			Platform sina = ShareSDK.getPlatform(SinaWeibo.NAME);
			authorize(sina);
			break;
		case R.id.txt_land_tencent:
			// QQ空间
			Platform qzone = ShareSDK.getPlatform(QZone.NAME);
			authorize(qzone);
			break;
		case R.id.txt_register:
			register();// 注册
			break;
		}
	}

	private void register() {
		Intent intent = new Intent();
		intent.setClass(this, RegisterActivity.class);
		startActivity(intent);
	}

	private void login() {
		String username = edt_usernam.getText().toString().trim();
		String password = edt_password.getText().toString().trim();
		if (username.length() <= 0 || password.length() <= 0) {
			Toast.makeText(this, "请填写完整账号和密码", Toast.LENGTH_SHORT).show();
			return;
		} else {
			params = new RequestParams();
			params.put("username", username);
			params.put("password", password);
			new AsyncHttpClient().get(Contants.LOGIN, params,
					new AsyncHttpResponseHandler() {
						@Override
						public void onFailure(int arg0, Header[] arg1,
								byte[] arg2, Throwable arg3) {
							Toast.makeText(LoginActivity.this, "登陆失败",
									Toast.LENGTH_LONG).show();
						}

						@Override
						public void onSuccess(int responseCode, Header[] arg1,
								byte[] response) {
							Login login = JsonTools.pareseLogin(new String(
									response));
							int code = login.getCode();
							if (code == 200) {
								String token = login.getToken();
								String userName = login.getUsername();
								String headerUrl = login.getHeader_img();
								
								//数据持久化
								try{
									spHelper = new SharedPreferencesHelper(LoginActivity.this);
									spHelper.saveNickName(login.getNickname());//昵称
									spHelper.saveSex(login.getSex());//性别
									spHelper.saveHeaderImg(headerUrl);//头像路径
									spHelper.saveToken(token);
									spHelper.saveUsername(userName);
									spHelper.updateState(true);//登录成功,内容可读
									Toast.makeText(LoginActivity.this, "登陆成功",
											Toast.LENGTH_LONG).show();
									Intent intent = new Intent();
									Bundle bundle = new Bundle();
									bundle.putString("username", userName);
									intent.putExtras(bundle);
									setResult(Activity.RESULT_OK, intent);  
									LoginActivity.this.finish();
								}catch(Exception e){
									e.printStackTrace();
									Toast.makeText(LoginActivity.this, "登陆失败",
											Toast.LENGTH_LONG).show();
								}
							}
						}
					});
		}
	}

	// 执行授权,获取用户信息
	// 文档：http://wiki.mob.com/Android_%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E8%B5%84%E6%96%99
	private void authorize(Platform plat) {
		if (plat == null) {
			return;
		}

		plat.setPlatformActionListener(this);
		// 关闭SSO授权
		plat.SSOSetting(true);
		plat.showUser(null);
	}

	@Override
	public void onCancel(Platform platform, int action) {
		if (action == Platform.ACTION_USER_INFOR) {
			handler.sendEmptyMessage(MSG_AUTH_CANCEL);
		}
	}

	@Override
	public void onComplete(Platform platform, int action,
			HashMap<String, Object> res) {
		if (action == Platform.ACTION_USER_INFOR) {
			Message msg = new Message();
			msg.what = MSG_AUTH_COMPLETE;
			msg.obj = new Object[] { platform.getName(), res };
			handler.sendMessage(msg);
		}
	}

	@Override
	public void onError(Platform arg0, int arg1, Throwable arg2) {}

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case MSG_AUTH_CANCEL: {
			// 取消授权
			Toast.makeText(this, R.string.auth_cancel, Toast.LENGTH_SHORT)
					.show();
		}
			break;
		case MSG_AUTH_ERROR: {
			// 授权失败
			Toast.makeText(this, R.string.auth_error, Toast.LENGTH_SHORT)
					.show();
		}
			break;
		case MSG_AUTH_COMPLETE: {
			// 授权成功
			Object[] objs = (Object[]) msg.obj;
			String platform = (String) objs[0];
			HashMap<String, Object> res = (HashMap<String, Object>) objs[1];
			if (signupListener != null
					&& signupListener.onSignin(platform, res)) {
				SignupPage signupPage = new SignupPage();
				signupPage.setOnLoginListener(signupListener);
				signupPage.setPlatform(platform);
				signupPage.show(this, null);
			}
		}
			break;
		}
		return false;
	}
}
