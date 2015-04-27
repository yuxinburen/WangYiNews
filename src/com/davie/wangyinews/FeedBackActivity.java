package com.davie.wangyinews;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class FeedBackActivity extends Activity implements OnClickListener {

	private TextView txt_show,txt_send,txt_content;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	private void init() {
		txt_show = (TextView) findViewById(R.id.txt_show);
		txt_send = (TextView) findViewById(R.id.txt_send);
		txt_content = (TextView) findViewById(R.id.txt_content);
		
		txt_show.setOnClickListener(this);
		txt_send.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.txt_show:
			finish();
			break;
		case R.id.txt_send:
			send();
			break;
		}
	}
	
	private void send(){
		String content = txt_content.getText().toString().trim();
		if(content.length()<=0)return;
		Toast.makeText(this, "反馈成功,请静待回复", Toast.LENGTH_LONG).show();
		txt_content.setText("");
	}
	
}
