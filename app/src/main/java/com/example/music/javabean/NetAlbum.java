package com.example.music.javabean;

import java.util.List;

public class NetAlbum {
	public List<Data> data;
	public int pageCount;
	public int totalCount;

	public class Data {
		public int albumId;
		public List<AlbumRightkey> albumRightkey;
		public String description;
		public int id;
		public int isExclusive;
		public String lang;
		public String name;
		public String picUrl;
		public String publishDate;
		public int publishYear;
		public String singerName;
		public List<Integer> songs;
		public int status;
		public int week;
		public int year;
	}

	public class AlbumRightkey {
		public boolean buy;
		public boolean buyFlag;
		public int count;
		public String dmsg;
		public int price;
	}
}
