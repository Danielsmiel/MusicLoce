package com.example.music.adapter;

import java.util.List;

import com.example.music.R;
import com.example.music.javabean.FindSong;
import com.example.music.utils.LogUtils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyFindMusicLvAdapter extends BaseAdapter {
	Context context;
	List<String> strList;

	public MyFindMusicLvAdapter(Context context, List<String> strList) {
		this.context = context;
		this.strList = strList;
	}

	@Override
	public int getCount() {
		return strList.size();
	}

	@Override
	public Object getItem(int position) {
		return strList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = View.inflate(context, R.layout.item_find_music, null);
		TextView songName = (TextView) convertView.findViewById(R.id.item_find_music_song_tv);
		songName.setText(strList.get(position));
		return convertView;
	}

	public void setList(List<String> strList) {
		this.strList = strList;
		notifyDataSetChanged();
	}

}
