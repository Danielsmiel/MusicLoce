package com.example.music;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import com.example.music.adapter.MyNetSongListLvAdapter;
import com.example.music.javabean.NetSong;
import com.example.music.javabean.NetSong.NetSongData;
import com.example.music.javabean.NetSong.NetSongList;
import com.example.music.javabean.NetSong.UrlList;
import com.example.music.javabean.Song;
import com.example.music.utils.BroadCastConstant;
import com.example.music.utils.DownLoadMusicUtils;
import com.example.music.utils.FormatUtils;
import com.example.music.utils.ImageLoaderUtils;
import com.example.music.utils.LogUtils;
import com.example.music.utils.MusicConstant;
import com.example.music.utils.MusicUtils;
import com.example.music.utils.NetPathUtils;
import com.google.gson.Gson;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NetMusicListActivity extends Activity {
	int albumId;
	Button exitBtn;
	View lvHeader;
	ImageView albumPicIv;
	TextView albumNameTv, langTv, singerNameTv, publishDateTv;
	ListView netSongLv;
	List<NetSongList> songList;
	MyNetSongListLvAdapter adapter;
	NetSongData data;
	List<UrlList> urlList;
	int downLoadPosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_net_music_list);
		lvHeader = View.inflate(this, R.layout.header_net_music_list, null);
		albumId = getIntent().getIntExtra("albumId", -1);
		initView();
		initHeader();
		setLvData();
		setNetData();
	}

	/**
	 * ��ʼ������ͼ�Ŀؼ�
	 */
	private void initView() {
		exitBtn = (Button) findViewById(R.id.net_music_detail_exit_btn);
		netSongLv = (ListView) findViewById(R.id.net_music_detail_lv);
		netSongLv.setOnItemClickListener(itemClickListener);
		netSongLv.setOnItemLongClickListener(itemLongClickListener);
		exitBtn.setOnClickListener(listener);
	}

	/**
	 * ��ʼ��Header�Ŀؼ�
	 */
	private void initHeader() {
		albumPicIv = (ImageView) lvHeader.findViewById(R.id.header_net_lv_pic_iv);
		albumNameTv = (TextView) lvHeader.findViewById(R.id.header_net_album_name_tv);
		langTv = (TextView) lvHeader.findViewById(R.id.header_net_album_lang_tv);
		singerNameTv = (TextView) lvHeader.findViewById(R.id.header_net_album_singer_tv);
		publishDateTv = (TextView) lvHeader.findViewById(R.id.header_net_album_date_tv);
	}

	/**
	 * ���ݴ���Activity��albumId��ѯ��������
	 */
	public void setNetData() {
		RequestParams params = new RequestParams(NetPathUtils.MUSIC_ALBUM_PATH + albumId);

		x.http().get(params, new CommonCallback<String>() {
			public void onSuccess(String result) {
				Gson gson = new Gson();
				NetSong netSong = gson.fromJson(result, NetSong.class);
				data = netSong.data;
				setHeaderData(netSong.data);
				songList = netSong.data.songList;
				adapter.setList(songList);

			}

			public void onCancelled(CancelledException cex) {

			}

			public void onError(Throwable ex, boolean isOnCallback) {
				ex.printStackTrace();
				Toast.makeText(NetMusicListActivity.this, "�赥ˢ��ʧ�ܣ���������", 0).show();
			}

			public void onFinished() {

			}
		});
	}

	/**
	 * ����list��Header������
	 */
	public void setHeaderData(NetSongData data) {
		ImageLoaderUtils.getImageByLoader(data.picUrl, albumPicIv);
		albumNameTv.setText("ר����: " + data.name);
		langTv.setText("����: " + data.lang);
		singerNameTv.setText("������: " + data.singerName);
		publishDateTv.setText("��������: " + data.publishDate);

	}

	/**
	 * ����ListView������
	 */
	private void setLvData() {
		songList = new ArrayList<NetSong.NetSongList>();
		adapter = new MyNetSongListLvAdapter(NetMusicListActivity.this, songList);
		netSongLv.addHeaderView(lvHeader, null, false);
		netSongLv.setAdapter(adapter);
	}

	/**
	 * ���ذ�ť�ĵ���¼�
	 */
	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			finish();
		}
	};

	/**
	 * ÿ��Ŀ�ĵ��ʱ��
	 */
	OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			try {
				List<Song> localSongList = MusicUtils.setLocalMusicList(songList, NetMusicListActivity.this,
						data.singerPicUrl);
				if (localSongList.size() > 0) {
					MusicConstant.PLAY_LIST = localSongList;
					MusicConstant.IS_FROM_NET = true;
					LogUtils.LogI("����ablumId", albumId+"");
					MusicUtils.setNetSingerPic(albumId);
					Intent intent = new Intent();
					urlList = songList.get(position - 1).urlList;
					intent.setAction(BroadCastConstant.MUSIC_SERVICE_BROADCAST);
					MusicConstant.PLAY_ITEM = position - 1;
					intent.putExtra("musicItem", MusicConstant.PLAY_ITEM);
					intent.putExtra("type", MusicConstant.MEDIA_PLAY);
					sendBroadcast(intent);
					// Toast.makeText(NetMusicListActivity.this,
					// "���ڲ��������������ע������", 0).show();
					finish();
				} else {
					Toast.makeText(NetMusicListActivity.this, "��Ҳ��֪��Ϊɶ���׸費�ܲ���ͦ��", 0).show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	/**
	 * �������ص���¼�
	 */
	OnItemLongClickListener itemLongClickListener = new OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

			Builder builder = new Builder(NetMusicListActivity.this);
			builder.setTitle("����");
			urlList = songList.get(position - 1).urlList;
			builder.setMessage("����ʱ����" + FormatUtils.formatTime(urlList.get(0).duration) + " Min" + "\n" + "������С��"
					+ FormatUtils.formatSize(urlList.get(0).size) + " Mb");
			//
			builder.setNegativeButton("ȡ��", null);
			builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Toast.makeText(NetMusicListActivity.this, "��ʼ����", 0).show();
					downLoadPosition = position - 1;
					new DownLoadMusic().execute();

				}
			});
			builder.setIcon(R.drawable.ic_launcher);
			builder.show();
			return false;
		}
	};

	class DownLoadMusic extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			DownLoadMusicUtils.downLoadMusicByGet(urlList.get(0).url,
					songList.get(downLoadPosition).singerName + "-" + songList.get(downLoadPosition).name);
			return null;
		}

	}

}
