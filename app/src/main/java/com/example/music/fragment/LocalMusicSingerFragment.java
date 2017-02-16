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

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

public class LocalMusicSingerFragment extends BaseFragment {
	ListView lv, popuLv;
	List<Singer> singerList;
	List<Song> songList;
	MyLocalMusicSingerLvAdapter singerAdapter;
	MyLocalMusicSongLvAdapter songAdapter;
	PopupWindow popuWindow;
	View viewPopu;

	@Override
	public View initView() {
		viewPopu = View.inflate(getActivity(), R.layout.popu_singer, null);
		popuWindow = new PopupWindow(viewPopu, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		View view = View.inflate(getActivity(), R.layout.frag_local_music_singer, null);
		singerList = MusicUtils.getSingerData(getActivity());
		lv = (ListView) view.findViewById(R.id.frag_local_singer_lv);
		lv.setOnItemClickListener(listener);
		return view;
	}

	/**
	 * singerLv的点击事件，点击弹出一个popuWindow
	 */
	OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			setPopuLv(position);
			popuWindow.setFocusable(true);
			popuWindow.setHeight(android.app.ActionBar.LayoutParams.WRAP_CONTENT);
			popuWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_home));
			popuWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
			popuLv.setOnItemClickListener(popuListener);
		}

		/**
		 * popuWindowLv的点击事件，点击发送广播，并且给全局的歌单赋值
		 */
		OnItemClickListener popuListener = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				MusicConstant.IS_FROM_NET = false;
				MusicConstant.PLAY_LIST = songList;
				MusicConstant.PLAY_ITEM = position;
				Intent intent = new Intent();
				intent.setAction(BroadCastConstant.MUSIC_SERVICE_BROADCAST);
				intent.putExtra("musicItem", MusicConstant.PLAY_ITEM);
				intent.putExtra("type", MusicConstant.MEDIA_PLAY);
				getActivity().sendBroadcast(intent);
				Toast.makeText(getActivity(), "正在播放本地歌曲", 0).show();
			}
		};

		/**
		 * 设置popuWindow的Lv
		 * 
		 * @param position
		 */
		private void setPopuLv(int position) {
			songList = new ArrayList<Song>();
			popuLv = (ListView) viewPopu.findViewById(R.id.popu_singer_lv);
			// 循环是为了从数据库中取得的值匹配所有歌曲，如果歌曲符合，就拉到一个新的集合里面去
			for (int i = 0; i < MusicConstant.PLAY_LIST_X.size(); i++) {
				if (singerList.get(position).singerName.equals(MusicConstant.PLAY_LIST_X.get(i).singer)) {
					songList.add(MusicConstant.PLAY_LIST_X.get(i));
				}
			}
			songAdapter = new MyLocalMusicSongLvAdapter(getActivity(), songList);
			popuLv.setAdapter(songAdapter);
		}
	};

	@Override
	public void setData() {
		singerAdapter = new MyLocalMusicSingerLvAdapter(getActivity(), singerList);
		lv.setAdapter(singerAdapter);
	}

}
