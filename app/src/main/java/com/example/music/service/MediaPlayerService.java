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
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

public class MediaPlayerService extends Service implements OnPreparedListener, OnCompletionListener {

	MediaPlayer mediaPlayer;
	View musicView, mainView;
	/**
	 * handlerÿ�����Ӹ���һ��MusicContast����ĳ����������������HandlerҲ�ܽ��յ�ˢ�µĶ���
	 */
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
				MusicConstant.MEDIA_PROGRASS = mediaPlayer.getCurrentPosition();
				handler.sendEmptyMessageDelayed(0, 500);
			}
		};
	};

	/**
	 * Service�Դ��ķ���������õ��˹㲥�����ûʲô����
	 */
	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}

	/**
	 * ��ʼ����������Ҫ�Թ㲥����ע��
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setOnPreparedListener(this);
		mediaPlayer.setOnCompletionListener(this);
		IntentFilter filter = new IntentFilter();
		filter.addAction(BroadCastConstant.MUSIC_SERVICE_BROADCAST);
		registerReceiver(new MediaBroadCastReceiver(), filter);
	}

	/**
	 * ��ɵļ����¼�
	 */
	@Override
	public void onCompletion(MediaPlayer mp) {
		nextMusic();
	}

	/**
	 * ����ģʽ
	 * 
	 * @param path
	 */
	public void play(String path, Boolean isPause) {

		try {
			if (isPause) {
				mediaPlayer.start();
				MusicConstant.IS_PLAYING = mediaPlayer.isPlaying();
			} else {
				mediaPlayer.reset();
				mediaPlayer.setDataSource(path);
				mediaPlayer.prepareAsync();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * ׼���ļ����¼�
	 */
	@Override
	public void onPrepared(MediaPlayer mp) {
		mediaPlayer.start();
		MusicConstant.IS_PLAYING = mediaPlayer.isPlaying();
		MusicConstant.MEDIA_MAX = mediaPlayer.getDuration();
		Intent intent = new Intent();
		intent.setAction(BroadCastConstant.MUSIC_UPDATA_BROADCAST);
		intent.putExtra("songName", MusicConstant.PLAY_LIST.get(MusicConstant.PLAY_ITEM).songname);
		intent.putExtra("singer", MusicConstant.PLAY_LIST.get(MusicConstant.PLAY_ITEM).singer);
		sendBroadcast(intent);
		handler.removeMessages(0);
		handler.sendEmptyMessage(0);
	}

	/**
	 * ��ͣ
	 */
	public void pause() {
		if (mediaPlayer != null) {
			mediaPlayer.pause();
			MusicConstant.IS_PLAYING = mediaPlayer.isPlaying();
		}
	}

	/**
	 * �Զ�������һ���ķ���
	 */
	private void nextMusic() {
		MusicConstant.PLAY_ITEM++;
		if (MusicConstant.PLAY_ITEM >= MusicConstant.PLAY_LIST.size()) {
			MusicConstant.PLAY_ITEM = 0;
		}
		play(MusicConstant.PLAY_LIST.get(MusicConstant.PLAY_ITEM).path, false);
		MusicConstant.IS_PLAYING = mediaPlayer.isPlaying();
	}

	/**
	 * �Զ�������һ���ķ���
	 */
	private void lastMusic() {
		MusicConstant.PLAY_ITEM--;
		if (MusicConstant.PLAY_ITEM < 0) {
			MusicConstant.PLAY_ITEM = 0;
		}
		play(MusicConstant.PLAY_LIST.get(MusicConstant.PLAY_ITEM).path, false);
		MusicConstant.IS_PLAYING = mediaPlayer.isPlaying();
	}

	public class MediaBroadCastReceiver extends BroadcastReceiver {

		public void onReceive(Context context, Intent intent) {
			switch (intent.getIntExtra("type", -1)) {
			case MusicConstant.MEDIA_PLAY:
				play(MusicConstant.PLAY_LIST.get(intent.getIntExtra("musicItem", -1)).path,
						intent.getBooleanExtra("isPause", false));
				break;
			case MusicConstant.MEDIA_PAUSE:
				pause();
				break;
			case MusicConstant.MEDIA_SEEK:
				mediaPlayer.seekTo(MusicConstant.MEDIA_PROGRASS);
				break;
			case MusicConstant.MEDIA_NEXT:
				nextMusic();
				break;
			case MusicConstant.MEDIA_LAST:
				lastMusic();
				break;
			}
		}
	}

}
