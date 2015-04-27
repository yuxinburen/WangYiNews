package com.davie.wangyinews;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.common.db.MySQLiteOpenHelper;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;



public class FavoriteActivity extends Activity {

	private ListView listView;
	private TextView txt_empty;
	private MySQLiteOpenHelper helper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorite);
		helper = new MySQLiteOpenHelper(this);
		initView();
		initData();
	}
	
	

	private void initView() {
		listView = (ListView) findViewById(R.id.listview_favorite);
		txt_empty = (TextView) findViewById(R.id.txt_empty);
	}

	private void initData() {
		//TODO 将收藏的列表展示出来
		String sql = " select title from tb_myfavorite ";
		List<Map<String, Object>> maps = helper.selectList(sql, null);
		List<String> list = new ArrayList<String>();
		if (maps.size() > 0) {
			for (Map<String, Object> entry : maps) {
				list.add(entry.get("title").toString());
			}
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		listView.setAdapter(adapter);
		listView.setEmptyView(txt_empty);
		
	}
	
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.txt_show:
			finish();
			break;
		}
	}
}
