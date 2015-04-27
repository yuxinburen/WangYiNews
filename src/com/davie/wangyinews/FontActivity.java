package com.davie.wangyinews;


import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class FontActivity extends Activity implements OnClickListener {

	private TextView txt_show,txt_onlyone,txt_biggest,txt_bigger,txt_big,txt_center,txt_small;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_font);
		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	private void init() {
		txt_show = (TextView) findViewById(R.id.txt_show);
		txt_onlyone = (TextView) findViewById(R.id.txt_onlyone);
		txt_biggest = (TextView) findViewById(R.id.txt_biggest);
		txt_bigger = (TextView) findViewById(R.id.txt_bigger);
		txt_big = (TextView) findViewById(R.id.txt_big);
		txt_center = (TextView) findViewById(R.id.txt_center);
		txt_small = (TextView) findViewById(R.id.txt_small);
		
		txt_onlyone.setOnClickListener(this);
		txt_biggest.setOnClickListener(this);
		txt_bigger.setOnClickListener(this);
		txt_big.setOnClickListener(this);
		txt_center.setOnClickListener(this);
		txt_small.setOnClickListener(this);
		txt_show.setOnClickListener(this);
		
		
		//默认字体为中等字体
		Drawable img = getResources().getDrawable(R.drawable.night_subs_cell_checked_mainpage);
		// 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
		img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
		txt_center.setCompoundDrawables(null, null, img, null); //设置右图标
			
	}

	@Override
	public void onClick(View v) {
		loadTextView();
		switch (v.getId()) {
		case R.id.txt_show:
			finish();
			break;
		case R.id.txt_onlyone:
			setDrawable(txt_onlyone);
			break;
		case R.id.txt_biggest:
			setDrawable(txt_biggest);
			break;
		case R.id.txt_bigger:
			setDrawable(txt_bigger);
			break;
		case R.id.txt_big:
			setDrawable(txt_big);
			break;
		case R.id.txt_center:
			setDrawable(txt_center);
			break;
		case R.id.txt_small:
			setDrawable(txt_small);
			break;
		}
	}
	
	private void loadTextView(){
		txt_onlyone.setCompoundDrawables(null, null, null, null);
		txt_biggest.setCompoundDrawables(null, null, null, null);
		txt_bigger.setCompoundDrawables(null, null, null, null);
		txt_big.setCompoundDrawables(null, null, null, null);
		txt_center.setCompoundDrawables(null, null, null, null);
		txt_small.setCompoundDrawables(null, null, null, null);
	}
	
	private void setDrawable(TextView txtView){
		//默认字体为中等字体
		Drawable img = getResources().getDrawable(R.drawable.night_subs_cell_checked_mainpage);
		// 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
		img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
		txtView.setCompoundDrawables(null, null, img, null); //设置右图标
	}
}
