package com.example.music.service;

import com.example.music.utils.BroadCastConstant;
import com.example.music.utils.MusicConstant;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.IBinder;

public class MediaPlayService extends Service implements OnPreparedListener, OnCompletionListener {
	MediaPlayer mediaPlayer;
	MusicServiceBroadCastResceiver rescever;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setOnPreparedListener(this);
		mediaPlayer.setOnCompletionListener(this);
		rescever = new MusicServiceBroadCastResceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(BroadCastConstant.MUSIC_SERVICE_BROADCAST);
		registerReceiver(new MusicServiceBroadCastResceiver(), filter);
	}

	private void play(String path) {
		try {
			mediaPlayer.reset();
			mediaPlayer.setDataSource(path);
			mediaPlayer.prepareAsync();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 播放下一曲
	 */
	private void nextMusic() {
		MusicConstant.PLAY_ITEM++;
		// 1. 如果播放音频的位置变量小于播放列表的长度
		// 继续播放当前位置的音频
		if (MusicConstant.PLAY_ITEM < MusicConstant.PLAY_LIST.size()) {
		}
		// 2.如果大于，把位置赋值为0，丛头开始播放
		else {
			MusicConstant.PLAY_ITEM = 0;
		}
		play(MusicConstant.PLAY_LIST.get(MusicConstant.PLAY_ITEM).path);
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		nextMusic();
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		mediaPlayer.start();

	}

	/**
	 * 接收广播用的 处理对应的动作
	 * 
	 * @author user
	 *
	 */
	class MusicServiceBroadCastResceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (MusicConstant.PLAY_LIST != null && MusicConstant.PLAY_LIST.size() > 0) {
				play(MusicConstant.PLAY_LIST.get(MusicConstant.PLAY_ITEM).path);

			}
		}
	}
}
