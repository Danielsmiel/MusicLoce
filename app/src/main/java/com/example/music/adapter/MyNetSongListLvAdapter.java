package com.example.music.adapter;

import java.util.List;

import com.example.music.NetMusicListActivity;
import com.example.music.R;
import com.example.music.javabean.NetSong.NetSongList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MyNetSongListLvAdapter extends BaseAdapter {

	Context context;
	List<NetSongList> songList;

	public MyNetSongListLvAdapter(Context context, List<NetSongList> songList) {
		this.context = context;
		this.songList = songList;
	}

	@Override
	public int getCount() {
		return songList.size();
	}

	@Override
	public Object getItem(int position) {
		return songList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = View.inflate(context, R.layout.item_net_music_list, null);
		TextView songNameTv = (TextView) convertView.findViewById(R.id.item_net_list_songname_tv);
		TextView singerNameTv = (TextView) convertView.findViewById(R.id.item_net_list_singername_tv);
		songNameTv.setText(songList.get(position).name);
		singerNameTv.setText(songList.get(position).singerName + " - " + songList.get(position).albumName);
		return convertView;
	}

	public void setList(List<NetSongList> songList) {
		this.songList = songList;
		notifyDataSetChanged();
		Toast.makeText(context, "歌单刷新成功", 0).show();
	}
}
