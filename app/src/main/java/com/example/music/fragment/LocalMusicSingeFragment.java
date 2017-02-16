package com.example.music.fragment;

import java.util.ArrayList;
import java.util.List;

import com.example.music.R;
import com.example.music.adapter.MyLocalMusicSingerLvAdapter;
import com.example.music.adapter.MyLocalMusicSongLvAdapter;
import com.example.music.javabean.Singer;
import com.example.music.javabean.Song;
import com.example.music.utils.BroadCastConstant;
import com.example.music.utils.MusicConstant;
import com.example.music.utils.MusicUtils;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class LocalMusicSingeFragment extends BaseFragment {
	ListView singerLv, alertLv;
	Button alertFinishBtn;
	TextView alertSingerNameTv;
	View viewDialog;
	AlertDialog.Builder builder;
	// ���AlertDialog���ڹر�AlertDialog
	AlertDialog alert;
	List<Singer> singerList;
	List<Song> songList;
	MyLocalMusicSingerLvAdapter singerAdapter;
	MyLocalMusicSongLvAdapter songAdapter;

	@Override
	public View initView() {
		View view = View.inflate(getActivity(), R.layout.frag_local_music_singer, null);
		singerLv = (ListView) view.findViewById(R.id.frag_local_singer_lv);
		singerList = MusicUtils.getSingerData(getActivity());
		singerLv.setOnItemClickListener(singerListener);
		return view;
	}

	OnItemClickListener singerListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// ÿ�ε���������ͼ����Ȼ��ͼ��һֱ���ڣ��ᱨ��
			viewDialog = null;
			// ������ͼ
			viewDialog = View.inflate(getActivity(), R.layout.alert_dialog_singer, null);
			builder = new AlertDialog.Builder(getActivity());
			builder.setView(viewDialog);
			// ����LV�ķ���
			setAlertDialogLv(position);
			alertSingerNameTv = (TextView) viewDialog.findViewById(R.id.alert_singer_name_tv);
			// �ر�AlertDialog
			builder.setCancelable(false);
			alertSingerNameTv.setText(singerList.get(position).singerName);
			alert = builder.show();
			alertFinishBtn = (Button) viewDialog.findViewById(R.id.alert_singer_exit_btn);
			alertFinishBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					alert.dismiss();
				}
			});

		}

		/**
		 * ����alertDialog�����ListView
		 * 
		 * @param position
		 */
		private void setAlertDialogLv(int position) {
			songList = new ArrayList<Song>();
			alertLv = (ListView) viewDialog.findViewById(R.id.alert_singer_lv);

			// ƥ�������PLAY_LIST_X����Ǿ��Բ���ߵ�
			for (int i = 0; i < MusicConstant.PLAY_LIST_X.size(); i++) {
				if (singerList.get(position).singerName.equals(MusicConstant.PLAY_LIST_X.get(i).singer)) {
					songList.add(MusicConstant.PLAY_LIST_X.get(i));
				}
			}
			// ������
			songAdapter = new MyLocalMusicSongLvAdapter(getActivity(), songList);
			alertLv.setAdapter(songAdapter);
			// ����¼�
			alertLv.setOnItemClickListener(alertListener);
		}
	};

	/**
	 * �ڲ���Lv��Item�ĵ���¼�
	 */
	OnItemClickListener alertListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// ��ȫ�ֵ�List��ֵ���Լ�position
			MusicConstant.PLAY_LIST = songList;
			MusicConstant.PLAY_ITEM = position;
			Intent intent = new Intent();
			intent.setAction(BroadCastConstant.MUSIC_SERVICE_BROADCAST);
			intent.putExtra("musicItem", MusicConstant.PLAY_ITEM);
			intent.putExtra("type", MusicConstant.MEDIA_PLAY);
			getActivity().sendBroadcast(intent);
			alert.dismiss();
		}
	};

	@Override
	public void setData() {
		singerAdapter = new MyLocalMusicSingerLvAdapter(getActivity(), singerList);
		singerLv.setAdapter(singerAdapter);
	}

}
