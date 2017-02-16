package com.example.music.fragment;

import java.util.ArrayList;
import java.util.List;

import com.example.music.R;
import com.example.music.adapter.MyLocalMusicSingerLvAdapter;
import com.example.music.adapter.MyLocalMusicSongLvAdapter;
import com.example.music.javabean.Singer;
import com.example.music.javabean.Song;
import com.example.music.utils.BroadCastConstant;
import com.example.music.utils.MusicConstant;
import com.example.music.utils.MusicUtils;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class LocalMusicSingeFragment extends BaseFragment {
	ListView singerLv, alertLv;
	Button alertFinishBtn;
	TextView alertSingerNameTv;
	View viewDialog;
	AlertDialog.Builder builder;
	// 这个AlertDialog用于关闭AlertDialog
	AlertDialog alert;
	List<Singer> singerList;
	List<Song> songList;
	MyLocalMusicSingerLvAdapter singerAdapter;
	MyLocalMusicSongLvAdapter songAdapter;

	@Override
	public View initView() {
		View view = View.inflate(getActivity(), R.layout.frag_local_music_singer, null);
		singerLv = (ListView) view.findViewById(R.id.frag_local_singer_lv);
		singerList = MusicUtils.getSingerData(getActivity());
		singerLv.setOnItemClickListener(singerListener);
		return view;
	}

	OnItemClickListener singerListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// 每次点击先清空视图，不然视图会一直存在，会报错
			viewDialog = null;
			// 引入视图
			viewDialog = View.inflate(getActivity(), R.layout.alert_dialog_singer, null);
			builder = new AlertDialog.Builder(getActivity());
			builder.setView(viewDialog);
			// 设置LV的方法
			setAlertDialogLv(position);
			alertSingerNameTv = (TextView) viewDialog.findViewById(R.id.alert_singer_name_tv);
			// 关闭AlertDialog
			builder.setCancelable(false);
			alertSingerNameTv.setText(singerList.get(position).singerName);
			alert = builder.show();
			alertFinishBtn = (Button) viewDialog.findViewById(R.id.alert_singer_exit_btn);
			alertFinishBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					alert.dismiss();
				}
			});

		}

		/**
		 * 设置alertDialog里面的ListView
		 * 
		 * @param position
		 */
		private void setAlertDialogLv(int position) {
			songList = new ArrayList<Song>();
			alertLv = (ListView) viewDialog.findViewById(R.id.alert_singer_lv);

			// 匹配歌曲，PLAY_LIST_X这个是绝对不会边的
			for (int i = 0; i < MusicConstant.PLAY_LIST_X.size(); i++) {
				if (singerList.get(position).singerName.equals(MusicConstant.PLAY_LIST_X.get(i).singer)) {
					songList.add(MusicConstant.PLAY_LIST_X.get(i));
				}
			}
			// 老三套
			songAdapter = new MyLocalMusicSongLvAdapter(getActivity(), songList);
			alertLv.setAdapter(songAdapter);
			// 点击事件
			alertLv.setOnItemClickListener(alertListener);
		}
	};

	/**
	 * 内部的Lv的Item的点击事件
	 */
	OnItemClickListener alertListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// 给全局的List赋值，以及position
			MusicConstant.PLAY_LIST = songList;
			MusicConstant.PLAY_ITEM = position;
			Intent intent = new Intent();
			intent.setAction(BroadCastConstant.MUSIC_SERVICE_BROADCAST);
			intent.putExtra("musicItem", MusicConstant.PLAY_ITEM);
			intent.putExtra("type", MusicConstant.MEDIA_PLAY);
			getActivity().sendBroadcast(intent);
			alert.dismiss();
		}
	};

	@Override
	public void setData() {
		singerAdapter = new MyLocalMusicSingerLvAdapter(getActivity(), singerList);
		singerLv.setAdapter(singerAdapter);
	}

}
