package com.example.music.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.music.javabean.FindSong;
import com.example.music.javabean.Singer;
import com.example.music.javabean.Song;

public class PaseJsonUtils {
	Song song = new Song();

	public static List<String> parseSongJson(String jsonStr) {
		List<String> strList = new ArrayList<String>();
		try {

			JSONObject jsonObject = new JSONObject(jsonStr);
			JSONObject dataObject = jsonObject.getJSONObject("data");
			JSONArray keywordArray = dataObject.getJSONArray("keyword");
			for (int i = 0; i < keywordArray.length(); i++) {
				JSONObject keyObject = keywordArray.getJSONObject(i);
				String name = keyObject.getString("val");
				strList.add(name);
			}
			JSONArray singerArray = dataObject.getJSONArray("singer");
			for (int i = 0; i < singerArray.length(); i++) {
				JSONObject singerObject = singerArray.getJSONObject(i);
				String name = singerObject.getString("alias_name");
				strList.add(name);
			}
			JSONArray songArray = dataObject.getJSONArray("song");
			for (int i = 0; i < songArray.length(); i++) {
				JSONObject songObject = songArray.getJSONObject(i);
				String name = songObject.getString("name");
				strList.add(name);
			}
			return strList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strList;
	}

	public static List<Song> parseSongListJson(String jsonStr) {
		List<Song> songList = new ArrayList<Song>();
		try {
			JSONObject allObject = new JSONObject(jsonStr);
			JSONArray dataArray = allObject.getJSONArray("data");
			for (int i = 0; i < dataArray.length(); i++) {
				Song song = new Song();
				JSONObject dataObject = dataArray.getJSONObject(i);
				song.albumName = dataObject.getString("albumName");
				song.ablumId = dataObject.getInt("albumId");
				song.songname = dataObject.getString("name");
				song.picUrl = dataObject.getString("picUrl");
				song.singer = dataObject.getString("singerName");
				JSONArray urlList = dataObject.getJSONArray("urlList");
				if (urlList.length() > 0) {
					JSONObject urlObject = urlList.getJSONObject(0);
					song.duration = urlObject.getInt("duration");
					song.size = urlObject.getInt("size");
					song.path = urlObject.getString("url");
					songList.add(song);
				}
			}
			LogUtils.LogI(" «≤‚ ‘JSON", songList.size() + "");
			return songList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return songList;
	}

}
