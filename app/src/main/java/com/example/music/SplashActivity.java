package com.example.music;

import com.example.music.utils.BitmapUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class SplashActivity extends Activity {
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			startActivity(new Intent(SplashActivity.this, MainActivity.class));
			finish();
		};
	};
	ImageView splashIv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		splashIv = (ImageView) findViewById(R.id.splash_iv);
		splashIv.setImageBitmap(BitmapUtils.BitmapScale(this, R.drawable.loggin, 1, 1));
		handler.sendEmptyMessageDelayed(0, 2000);
	}

}
