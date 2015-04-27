package com.davie.wangyinews;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SplashActivity extends Activity implements OnClickListener{

	
	private Button btn_enter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		btn_enter = (Button) findViewById(R.id.btn_enter);
		btn_enter.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_enter:
			Intent intent = new Intent();
			intent.setClass(this, MainActivity.class);
			startActivity(intent);
			finish();
			
			break;

		default:
			break;
		}
	}
	
}
