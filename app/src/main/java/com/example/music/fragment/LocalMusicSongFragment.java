package com.example.music.fragment;

import java.util.List;

import com.example.music.R;
import com.example.music.adapter.MyLocalMusicSongLvAdapter;
import com.example.music.javabean.Song;
import com.example.music.utils.BroadCastConstant;
import com.example.music.utils.MusicConstant;
import com.example.music.utils.MusicUtils;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class LocalMusicSongFragment extends BaseFragment {
	ListView lv;
	List<Song> list;
	MyLocalMusicSongLvAdapter adapter;

	@Override
	public View initView() {
		View view = View.inflate(getActivity(), R.layout.frag_local_music_song, null);
		lv = (ListView) view.findViewById(R.id.frag_local_song_lv);
		lv.setOnItemClickListener(listener);
		return view;
	}

	/**
	 * 点击Item的监听事件
	 */
	OnItemClickListener listener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			MusicConstant.IS_FROM_NET = false;
			MusicConstant.PLAY_ITEM = position;
			MusicConstant.PLAY_LIST = list;
			Intent intent = new Intent();
			intent.putExtra("type", MusicConstant.MEDIA_PLAY);
			intent.putExtra("musicItem", MusicConstant.PLAY_ITEM);
			intent.setAction(BroadCastConstant.MUSIC_SERVICE_BROADCAST);
			getActivity().sendBroadcast(intent);
			Toast.makeText(getActivity(), "正在播放本地歌曲", 0).show();
		}
	};

	/**
	 * 在SetData里面给全局的List以及list对应的Item赋值
	 */
	@Override
	public void setData() {
		list = MusicUtils.getMediaData(getActivity());

		MusicConstant.PLAY_LIST_X = list;
		adapter = new MyLocalMusicSongLvAdapter(getActivity(), list);
		lv.setAdapter(adapter);

	}

}
