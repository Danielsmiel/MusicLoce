package com.example.music.adapter;

import java.util.List;

import com.example.music.R;
import com.example.music.javabean.Song;
import com.example.music.utils.ImageLoaderUtils;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyFindMusicDetailLvAdapter extends BaseAdapter {

	Context context;
	List<Song> songList;

	public MyFindMusicDetailLvAdapter(Context context, List<Song> songList) {
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
		convertView = View.inflate(context, R.layout.item_find_music_detail, null);
		TextView songNameTv = (TextView) convertView.findViewById(R.id.item_find_music_detail_songname_tv);
		TextView singerNameTv = (TextView) convertView.findViewById(R.id.item_find_music_detail_albumname_tv);
		ImageView albumPicIv = (ImageView) convertView.findViewById(R.id.item_find_music_detail_albumpic_iv);
		final ImageView downIv = (ImageView) convertView.findViewById(R.id.item_find_detail_down_iv);
		final TextView downTv = (TextView) convertView.findViewById(R.id.item_find_detail_down_tv);
		songNameTv.setText(songList.get(position).songname);
		singerNameTv.setText(songList.get(position).singer + " - " + songList.get(position).albumName);
		ImageLoaderUtils.getImageByLoader(songList.get(position).picUrl, albumPicIv);
		downIv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
		return convertView;
	}

	public void setList(List<Song> songList) {
		this.songList = songList;
		notifyDataSetChanged();
	}

}
