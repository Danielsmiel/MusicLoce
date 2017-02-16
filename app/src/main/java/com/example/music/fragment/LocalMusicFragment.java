package com.example.music.fragment;

import com.example.music.R;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class LocalMusicFragment extends BaseFragment implements OnCheckedChangeListener {
	BaseFragment localMusicSongFragment, localMusicSingerFragment, localMusicAlbumFragment;
	FrameLayout localMusicFl;
	RadioGroup rg;

	@Override
	public View initView() {
		View view = View.inflate(getActivity(), R.layout.frag_local_music, null);
		localMusicSongFragment = new LocalMusicSongFragment();
		swichFragment(localMusicSongFragment);
		localMusicSingerFragment = new LocalMusicSingeFragment();
		localMusicAlbumFragment = new LocalMusicAlbumFragment();
		localMusicFl = (FrameLayout) view.findViewById(R.id.frag_local_fl);
		rg = (RadioGroup) view.findViewById(R.id.frag_local_music_rg);
		rg.setOnCheckedChangeListener(this);
		return view;
	}

	@Override
	public void setData() {

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.frag_local_song_rb:
			swichFragment(localMusicSongFragment);
			break;
		case R.id.frag_local_singer_rb:
			swichFragment(localMusicSingerFragment);
			break;
		case R.id.frag_local_album_rb:
			swichFragment(localMusicAlbumFragment);
			break;
		}
	}

	/**
	 * 替换帧布局的老三套
	 * 
	 * @param fragment
	 */
	private void swichFragment(Fragment fragment) {
		getFragmentManager().beginTransaction().replace(R.id.frag_local_fl, fragment).commit();
	}

}
