package com.example.music.adapter;

import java.io.File;
import java.util.List;

import com.example.music.R;
import com.example.music.javabean.NetAlbum.Data;
import com.example.music.utils.ImageLoaderUtils;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyNetMusicGvAdapter extends BaseAdapter {
	Context context;
	List<Data> list;

	public MyNetMusicGvAdapter(Context context, List<Data> list) {
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

		convertView = View.inflate(context, R.layout.item_net_music, null);
		TextView netSongNameTv = (TextView) convertView.findViewById(R.id.item_net_album_tv);
		ImageView netSongImageIv = (ImageView) convertView.findViewById(R.id.item_net_album_iv);
		ImageLoaderUtils.getImageByLoader(list.get(position).picUrl, netSongImageIv);
		netSongNameTv.setText(list.get(position).name);

		return convertView;
	}

	public void setList(List<Data> list) {
		this.list = list;
		notifyDataSetChanged();
	}

}
