package com.davie.wangyinews;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.common.image.BitmapCache;
import com.common.url.Contants;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener {

	private NetworkImageView imageView;
	private EditText edt_username, edt_password, edt_random;
	private Button btn_submit;
	private TextView txt_show;

	private RequestQueue queue;
	private ImageLoader imageLoader;

	private RequestParams params;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		initView();
		load();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	public void initView() {
		edt_username = (EditText) findViewById(R.id.edt_username_register);
		edt_password = (EditText) findViewById(R.id.edt_password_register);
		edt_random = (EditText) findViewById(R.id.edt_random_register);

		btn_submit = (Button) findViewById(R.id.btn_submit_register);
		imageView = (NetworkImageView) findViewById(R.id.img_rundow_register);
		txt_show = (TextView) findViewById(R.id.txt_show);
		
		
		btn_submit.setOnClickListener(this);
		txt_show.setOnClickListener(this);

	}

	public void load() {
		Toast.makeText(this, "初始化验证码", Toast.LENGTH_SHORT).show();
		String url = Contants.RANDOM + getSerialNumber();
		queue = Volley.newRequestQueue(this);
		imageLoader = new ImageLoader(queue, BitmapCache.getInstance());
		imageView.setImageUrl(url, imageLoader);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.txt_show:
			finish();
			break;
		case R.id.btn_submit_register:
			register();// 注册
			break;
		}
	}

	private void register() {
		String username = edt_username.getText().toString().trim();
		String password = edt_password.getText().toString().trim();
		String random = edt_random.getText().toString().trim();
		if (username == null || password == null || random == null) {
			Toast.makeText(this, "请将注册信息填写完整", Toast.LENGTH_SHORT).show();
			return;
		} else {
			params = new RequestParams();
			params.put("username", username);
			params.put("password", password);
			params.put("sequence", getSerialNumber());
			new AsyncHttpClient().get(Contants.REGISTER, params,
					new AsyncHttpResponseHandler() {
						@Override
						public void onFailure(int arg0, Header[] arg1,
								byte[] arg2, Throwable arg3) {
							Toast.makeText(RegisterActivity.this, "注册失败",
									Toast.LENGTH_LONG).show();
						}

						@Override
						public void onSuccess(int responseCode, Header[] arg1,
								byte[] response) {
							int code = 0;
							try {
								JSONObject obj = new JSONObject(new String(
										response));
								code = obj.optInt("code");
								if (code == 200) {
									Toast.makeText(RegisterActivity.this,
											"注册成功", Toast.LENGTH_LONG).show();
									RegisterActivity.this.finish();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					});
		}
	}

	/**
	 * 工具方法:获取sequence
	 * 
	 * @return
	 */
	private static String getSerialNumber() {
		String serialNumber = null;
		try {
			Class<?> c = Class.forName("android.os.SystemProperties");
			Method get = c.getMethod("get", String.class);
			serialNumber = (String) get.invoke(c, "ro.serialno");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return serialNumber;
	}
}
