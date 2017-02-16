package com.example.music.javabean;

import java.util.List;

public class NetSong {
	public NetSongData data;

	public class NetSongData {
		public String lang;
		public String picUrl;
		public String publishDate;
		public String singerName;
		public int albumId;
		public String singerPicUrl;
		public String name;
		public List<NetSongList> songList;

	}

	public class NetSongList {
		public String albumName;
		public String name;
		public String singerName;
		public List<UrlList> urlList;
	}

	public class UrlList {
		// ��������
		public int duration;
		// ������С
		public int size;
		public String sffix;
		public String typeDescription;
		public String url;
	};
}
