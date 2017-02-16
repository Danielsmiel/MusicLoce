package com.example.music.adapter;

import java.util.List;

import com.example.music.R;
import com.example.music.javabean.Song;
import com.example.music.utils.MusicConstant;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyLocalMusicSongLvAdapter extends BaseAdapter {

	Context context;
	List<Song> list;

	public MyLocalMusicSongLvAdapter(Context context, List<Song> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = View.inflate(context, R.layout.item_local_music_song, null);
		TextView songId = (TextView) convertView.findViewById(R.id.item_local_music_song_id_tv);
		TextView songName = (TextView) convertView.findViewById(R.id.item_local_music_song_name_tv);
		TextView songAlbum = (TextView) convertView.findViewById(R.id.item_local_music_song_album_tv);
		songId.setText(list.get(position).id + "");
		songName.setText(list.get(position).songname);
		songAlbum.setText(list.get(position).singer);
		return convertView;
	}

	public void setList(List<Song> list) {
		this.list = list;
		notifyDataSetChanged();
	}
}
