package com.davie.wangyinews;


import org.apache.http.Header;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.common.url.Contants;
import com.common.utils.SharedPreferencesHelper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class UpdateActivity extends Activity implements OnClickListener {

	private TextView txt_show;//头部
	
	private EditText edt_username_update, edt_sex_update;
	private Button btn_submit_update;
	private TextView txt_username;

	private RequestParams params;
	
	
	private SharedPreferencesHelper spHelper;

	private String username;

	private String sex;

	private SharedPreferencesHelper helper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update);
		helper = new SharedPreferencesHelper(this);
		
		init();
		loadData();

	}
	
	private void loadData(){
		if(helper.getState()){
			String nick = helper.getNickName();
			String sex = helper.getSex();
			String username  = helper.getUsername();
			txt_username.setText("用户名:   "+username);
			edt_username_update.setText(nick);
			edt_sex_update.setText(sex);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	private void init() {
		edt_username_update = (EditText) findViewById(R.id.edt_username_update);
		edt_sex_update = (EditText) findViewById(R.id.edt_sex_update);
		btn_submit_update = (Button) findViewById(R.id.btn_submit_update);
		txt_username = (TextView) findViewById(R.id.txt_username);
		txt_show = (TextView) findViewById(R.id.txt_show);

		btn_submit_update.setOnClickListener(this);
		edt_username_update.setOnClickListener(this);
		edt_sex_update.setOnClickListener(this);
		txt_username.setOnClickListener(this);
		txt_show.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.txt_show:
			finish();
			break;
		case R.id.btn_submit_update:
			//ToDo提交
			update();
			break;
		}
	}

	private void update() {
		username = edt_username_update.getText().toString().trim();
		sex = edt_sex_update.getText().toString().trim();
		if (username.length() <= 0 || sex.length() <= 0) {
			Toast.makeText(this, "请填写完整个人信息", Toast.LENGTH_SHORT).show();
			return;
		} else {
			
			
			String token = helper.getToken();
			params = new RequestParams();
			params.put("nickname", username);
			params.put("sex", sex);
			params.put("token", token);
			new AsyncHttpClient().get(Contants.UPDATE, params,
					new AsyncHttpResponseHandler() {
						@Override
						public void onFailure(int arg0, Header[] arg1,
								byte[] arg2, Throwable arg3) {
							Toast.makeText(UpdateActivity.this, "修改失败",
									Toast.LENGTH_LONG).show();
						}

						@Override
						public void onSuccess(int responseCode, Header[] arg1,
								byte[] response) {
							try {
								JSONObject object = new JSONObject(new String(response));
								int code = object.optInt("code");
								if (code == 200) {
									//数据持久化
									try{
										spHelper = new SharedPreferencesHelper(UpdateActivity.this);
										spHelper.saveNickName(username);//昵称
										spHelper.saveSex(sex);//性别
										Toast.makeText(UpdateActivity.this, "修改成功",
												Toast.LENGTH_LONG).show();
										UpdateActivity.this.finish();
									}catch(Exception e){
										e.printStackTrace();
										Toast.makeText(UpdateActivity.this, "修改失败",
												Toast.LENGTH_LONG).show();
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
		}
	}
}
