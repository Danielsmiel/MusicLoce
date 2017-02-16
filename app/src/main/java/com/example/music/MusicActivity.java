package com.example.music;

import com.example.music.utils.BitmapUtils;
import com.example.music.utils.BroadCastConstant;
import com.example.music.utils.FormatUtils;
import com.example.music.utils.ImageLoaderUtils;
import com.example.music.utils.MusicConstant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MusicActivity extends Activity implements OnClickListener {
	SeekBar bar;
	ImageView backgroundIv;
	Button previousBtn, playBtn, nextBtn, leftBtn;
	TextView firstTv, maxTv, songNameTv, singerNameTv;
	/**
	 * Handler,里面设置的是SeekBar的自启动以及随时随地的读取MEDIA_PROGRASS这个全局变量 老师说的很简单啊，当前的
	 */
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			if (msg.what == 0) {
				bar.setMax(MusicConstant.MEDIA_MAX);
				bar.setProgress(MusicConstant.MEDIA_PROGRASS);
				firstTv.setText(FormatUtils.formatTime(MusicConstant.MEDIA_PROGRASS));
				maxTv.setText(FormatUtils.formatTime(MusicConstant.MEDIA_MAX));
				handler.sendEmptyMessageDelayed(0, 1000);
			} else {
				handler.sendEmptyMessageDelayed(1, 10);

			}

		};
	};

	/**
	 * 开始方法，启动Handler
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music);
		initView();
		handler.sendEmptyMessage(0);
		setButton();
		setText();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (MusicConstant.IS_FROM_NET && MusicConstant.SINGER_RUL_PIC_PATH != null) {
			ImageLoaderUtils.getImageByLoader(MusicConstant.SINGER_RUL_PIC_PATH, backgroundIv);

		}
	}

	/**
	 * 判断是否是播放状态，如果是，换图片，不是，则换图
	 */
	private void setButton() {
		if (MusicConstant.IS_PLAYING) {
			playBtn.setBackgroundResource(R.drawable.play_pause);
		} else {
			playBtn.setBackgroundResource(R.drawable.play);
		}
	}

	private void setText() {
		if (MusicConstant.PLAY_LIST != null && MusicConstant.PLAY_ITEM != -1) {
			songNameTv.setText(MusicConstant.PLAY_LIST.get(MusicConstant.PLAY_ITEM).songname);
			singerNameTv.setText(MusicConstant.PLAY_LIST.get(MusicConstant.PLAY_ITEM).singer);
		}
	}

	private void initView() {
		backgroundIv = (ImageView) findViewById(R.id.music_background_iv);
		backgroundIv.setImageBitmap(BitmapUtils.BitmapScale(this, R.drawable.bg_nophoto, 1, 1));
		firstTv = (TextView) findViewById(R.id.music_fristtime_tv);
		maxTv = (TextView) findViewById(R.id.music_secondtime_tv);
		songNameTv = (TextView) findViewById(R.id.music_song_name_tv);
		singerNameTv = (TextView) findViewById(R.id.music_singer_name_tv);
		previousBtn = (Button) findViewById(R.id.music_previous_btn);
		playBtn = (Button) findViewById(R.id.music_play_btn);
		nextBtn = (Button) findViewById(R.id.music_next_btn);
		leftBtn = (Button) findViewById(R.id.music_left);
		bar = (SeekBar) findViewById(R.id.music_sb);
		playBtn.setOnClickListener(this);
		nextBtn.setOnClickListener(this);
		previousBtn.setOnClickListener(this);
		leftBtn.setOnClickListener(this);
		bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if (fromUser) {
					// 给全局变量赋值，拖动到哪里，赋多少值，然后发送广播
					MusicConstant.MEDIA_PROGRASS = progress;
					Intent intent = new Intent();
					intent.setAction(BroadCastConstant.MUSIC_SERVICE_BROADCAST);
					intent.putExtra("type", MusicConstant.MEDIA_SEEK);
					sendBroadcast(intent);
				}
			}
		});
	}

	// 三个键的点击事件
	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		intent.setAction(BroadCastConstant.MUSIC_SERVICE_BROADCAST);
		switch (v.getId()) {
		case R.id.music_previous_btn:
			playBtn.setBackgroundResource(R.drawable.play_pause);
			intent.putExtra("type", MusicConstant.MEDIA_LAST);
			setText();
			break;
		case R.id.music_play_btn:
			if (MusicConstant.IS_PLAYING && MusicConstant.PLAY_ITEM != -1) {
				intent.putExtra("type", MusicConstant.MEDIA_PAUSE);
				playBtn.setBackgroundResource(R.drawable.play);
			} else {
				intent.putExtra("type", MusicConstant.MEDIA_PLAY);
				intent.putExtra("musicItem", MusicConstant.PLAY_ITEM);
				intent.putExtra("isPause", true);
				playBtn.setBackgroundResource(R.drawable.play_pause);
			}
			break;
		case R.id.music_next_btn:
			playBtn.setBackgroundResource(R.drawable.play_pause);
			intent.putExtra("type", MusicConstant.MEDIA_NEXT);
			setText();
			break;
		case R.id.music_left:
			finish();
			break;
		}
		sendBroadcast(intent);
	}
}
