package com.davie.wangyinews;

import java.util.List;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.common.json.JsonTools;
import com.common.url.Contants;
import com.common.utils.SharedPreferencesHelper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyCommentsActivity extends Activity {

	private ListView listView;
	private TextView txt_empty;
	private RequestQueue queue;
	private StringRequest stringRequest;
	private SharedPreferencesHelper spHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mycomments);

		initView();
		initData();
	}

	private void initView() {
		listView = (ListView) findViewById(R.id.listview_favorite);
		txt_empty = (TextView) findViewById(R.id.txt_empty);
		listView.setEmptyView(txt_empty);
	}

	private void initData() {
		spHelper = new SharedPreferencesHelper(this);
		queue = Volley.newRequestQueue(this);
		if(!isSignIn()){
			Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent();
			intent.setClass(this, LoginActivity.class);
			startActivity(intent);
			return ;
		}
		String token = spHelper.getToken();
		String path = Contants.MYCOMMENTS + token;
		stringRequest = new StringRequest(path,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						List<String> list = JsonTools.parseFavorite(response);
						ArrayAdapter<String> adapter = new ArrayAdapter<String>(MyCommentsActivity.this,android.R.layout.simple_list_item_1,list);
						listView.setAdapter(adapter);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(MyCommentsActivity.this, "数据请求错误", Toast.LENGTH_SHORT).show();
					}
				});
		queue.add(stringRequest);
	}
	
	/**
	 * 点击关闭事件
	 * @param view
	 */
	public void onClick(View view){
		switch (view.getId()) {
		case R.id.txt_show:
			finish();
			break;
		}
	}
	
	/**
	 * 判断是否登陆
	 * @return
	 */
	private boolean isSignIn(){
		boolean ret = false;
		ret = spHelper.getState();
		return ret;
	}
}
