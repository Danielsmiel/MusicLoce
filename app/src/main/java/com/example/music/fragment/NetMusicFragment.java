package com.example.music.fragment;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import com.example.music.NetMusicListActivity;
import com.example.music.R;
import com.example.music.adapter.MyNetMusicGvAdapter;
import com.example.music.javabean.NetAlbum;
import com.example.music.javabean.NetAlbum.Data;
import com.example.music.utils.NetPathUtils;
import com.example.music.view.PullToRefreshView;
import com.example.music.view.PullToRefreshView.OnFooterLoadListener;
import com.example.music.view.PullToRefreshView.OnHeaderRefreshListener;
import com.google.gson.Gson;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.Toast;

public class NetMusicFragment extends BaseFragment {
	PullToRefreshView refreshView;
	FrameLayout netFl;
	GridView netGv;
	MyNetMusicGvAdapter adapter;
	List<Data> list;

	@Override
	public View initView() {
		View view = View.inflate(getActivity(), R.layout.frag_net_music, null);
		netFl = new FrameLayout(getActivity());
		refreshView = (PullToRefreshView) view.findViewById(R.id.net_music_pull);
		setPullListener();
		netGv = (GridView) view.findViewById(R.id.net_music_gv);
		list = new ArrayList<NetAlbum.Data>();

		getNetData(1, 0);
		return view;
	}

	public void getNetData(int PAGE, final int type) {
		RequestParams params = new RequestParams(NetPathUtils.MUSIC_PATH_HEAD + PAGE + NetPathUtils.MUSIC_PATH_END);
		x.http().get(params, new CommonCallback<String>() {

			public void onSuccess(String result) {
				Gson gson = new Gson();
				NetAlbum netAlbum = gson.fromJson(result, NetAlbum.class);
				setNetData(type, netAlbum.data);
			}

			public void onCancelled(CancelledException cex) {

			}

			public void onError(Throwable ex, boolean isOnCallback) {
				ex.printStackTrace();
				Toast.makeText(getActivity(), "请检查网络", 0).show();
			}

			public void onFinished() {

			}
		});
	}

	public void setNetData(int type, List<Data> list) {
		switch (type) {
		case 0:
			this.list = list;
			adapter.setList(list);
			break;
		case 1:
			this.list = list;
			adapter.setList(list);
			refreshView.onHeaderRefreshFinish();
			Toast.makeText(getActivity(), "刷新成功", 0).show();
			break;
		case 2:
			this.list.addAll(list);
			adapter.setList(this.list);
			refreshView.onFooterLoadFinish();
			Toast.makeText(getActivity(), "加载成功", 0).show();
			break;
		}
	}

	int count = 1;

	public void setPullListener() {
		refreshView.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {

			@Override
			public void onHeaderRefresh(PullToRefreshView view) {
				getNetData(1, 1);
				count = 1;
			}
		});
		refreshView.setOnFooterLoadListener(new OnFooterLoadListener() {

			@Override
			public void onFooterLoad(PullToRefreshView view) {
				count++;
				getNetData(count, 2);
			}
		});
	}

	@Override
	public void setData() {
		adapter = new MyNetMusicGvAdapter(getActivity(), list);
		netGv.setAdapter(adapter);
		netGv.setOnItemClickListener(onItemClickListener);
	}

	OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent intent = new Intent(getActivity(), NetMusicListActivity.class);
			intent.putExtra("albumId", list.get(position).albumId);
			startActivity(intent);
		}
	};
}
