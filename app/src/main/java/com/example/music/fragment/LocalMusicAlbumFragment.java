package com.example.music.fragment;

import com.example.music.R;

import android.view.View;

public class LocalMusicAlbumFragment extends BaseFragment {

	@Override
	public View initView() {
		View view = View.inflate(getActivity(), R.layout.frag_local_music_album, null);
		return view;
	}

	@Override
	public void setData() {

	}

}
