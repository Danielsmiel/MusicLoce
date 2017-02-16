package com.example.music.fragment;

import java.util.ArrayList;
import java.util.List;

import com.example.music.R;
import com.example.music.adapter.MyFindMusicDetailLvAdapter;
import com.example.music.javabean.Song;
import com.example.music.utils.BroadCastConstant;
import com.example.music.utils.FormatUtils;
import com.example.music.utils.HttpUtils;
import com.example.music.utils.LogUtils;
import com.example.music.utils.MusicConstant;
import com.example.music.utils.MusicUtils;
import com.example.music.utils.NetPathUtils;
import com.example.music.utils.PaseJsonUtils;
import com.example.music.view.PullToRefreshView;
import com.example.music.view.PullToRefreshView.OnFooterLoadListener;
import com.example.music.view.PullToRefreshView.OnHeaderRefreshListener;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class FindMusicDetailFragment extends BaseFragment {
	ListView findDetailLv;
	String songName;
	List<Song> songList;
	MyFindMusicDetailLvAdapter adapter;
	PullToRefreshView findRefreshView;
	TextView headTv;
	static int type = 0;

	/**
	 * 转码+格式化
	 * 
	 * @param songName
	 */
	public FindMusicDetailFragment(String songName) {
		this.songName = songName;

	}

	@Override
	public View initView() {
		View findView = View.inflate(getActivity(), R.layout.frag_find_music_detail, null);
		findDetailLv = (ListView) findView.findViewById(R.id.frag_find_detail_lv);
		headTv = (TextView) findView.findViewById(R.id.frag_find_detail_tv);
		headTv.setText("搜索: " + songName);
		this.songName = FormatUtils.formatStr(songName);
		findRefreshView = (PullToRefreshView) findView.findViewById(R.id.frag_find_detail_pull);
		setPullListener();
		findDetailLv.setOnItemClickListener(itemClickListener);
		setLvData();
		new FindDetail().execute(1 + "");
		return findView;
	}

	/**
	 * Itme点击事件
	 */
	OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			MusicConstant.PLAY_LIST = songBufferList;
			MusicConstant.PLAY_ITEM = position;
			MusicConstant.IS_FROM_NET = true;
			MusicUtils.setNetSingerPic(MusicConstant.PLAY_LIST.get(position).ablumId);
			Intent intent = new Intent();
			intent.setAction(BroadCastConstant.MUSIC_SERVICE_BROADCAST);
			intent.putExtra("type", MusicConstant.MEDIA_PLAY);
			intent.putExtra("musicItem", position);
			getActivity().sendBroadcast(intent);
		}
	};

	/**
	 * 网络请求，自己写的
	 * 
	 * @param songName:歌曲名称
	 * @param page:第几页
	 */
	private void getNetSongDataByMine(String page) {
		String result;
		result = HttpUtils.requestStringByGet(
				NetPathUtils.MUSIC_FIND_DETAIL_HEAD + page + NetPathUtils.MUSIC_FIND_DETAIL_END + songName);
		songList = PaseJsonUtils.parseSongListJson(result);

	}

	/**
	 * 给ListView设置数据
	 */
	public void setLvData() {
		songList = new ArrayList<Song>();
		adapter = new MyFindMusicDetailLvAdapter(getActivity(), songList);
		findDetailLv.setAdapter(adapter);
	}

	/**
	 * 下拉上拉监听器
	 */
	static int count = 1;

	private void setPullListener() {
		findRefreshView.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {

			@Override
			public void onHeaderRefresh(PullToRefreshView view) {
				count = 1;
				type = 1;
				new FindDetail().execute(count + "");
			}
		});

		findRefreshView.setOnFooterLoadListener(new OnFooterLoadListener() {

			@Override
			public void onFooterLoad(PullToRefreshView view) {
				count++;
				type = 2;
				new FindDetail().execute(count + "");
			}
		});
	}

	/**
	 * 设置网路数据
	 * 
	 * @param result
	 */
	List<Song> songBufferList = new ArrayList<Song>();

	private void setNetData(List<Song> result) {
		switch (type) {
		case 0:
			songBufferList = result;
			adapter.setList(result);
			break;
		case 1:
			songBufferList = result;
			adapter.setList(songBufferList);
			findRefreshView.onHeaderRefreshFinish();
			break;
		case 2:
			if (result != null) {
				songBufferList.addAll(result);
			}
			adapter.setList(songBufferList);
			findRefreshView.onFooterLoadFinish();
			break;

		default:
			break;
		}
	}

	@Override
	public void setData() {

	}

	/**
	 * 网络线程
	 * 
	 * @author user
	 *
	 */
	class FindDetail extends AsyncTask<String, String, List<Song>> {

		@Override
		protected List<Song> doInBackground(String... params) {
			getNetSongDataByMine(params[0]);
			return songList;
		}

		@Override
		protected void onPostExecute(List<Song> result) {
			super.onPostExecute(result);
			// 刷新adapter

			setNetData(result);
		}
	}
}
