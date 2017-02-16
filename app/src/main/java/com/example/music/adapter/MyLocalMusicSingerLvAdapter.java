package com.example.music.adapter;

import java.util.List;

import com.example.music.R;
import com.example.music.javabean.Singer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyLocalMusicSingerLvAdapter extends BaseAdapter {
	Context context;
	List<Singer> list;

	public MyLocalMusicSingerLvAdapter(Context context, List<Singer> list) {
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
		convertView = View.inflate(context, R.layout.item_local_music_singer, null);
		TextView singerNameTv = (TextView) convertView.findViewById(R.id.item_local_music_singer_name_tv);
		TextView songNumTv = (TextView) convertView.findViewById(R.id.item_local_music_singer_songnum_tv);
		singerNameTv.setText(list.get(position).singerName);
		songNumTv.setText(list.get(position).songNum + " สื");
		return convertView;
	}

}
