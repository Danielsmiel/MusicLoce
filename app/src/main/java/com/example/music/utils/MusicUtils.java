package com.example.music.utils;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import com.example.music.javabean.NetSong.NetSongList;
import com.example.music.javabean.Singer;
import com.example.music.javabean.SingerPicUrl;
import com.example.music.javabean.Song;
import com.google.gson.Gson;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.widget.Toast;

public class MusicUtils {

	public static List<Song> getMediaData(Context context) {
		List<Song> list = new ArrayList<Song>();
		Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null,
				null, MediaStore.Audio.AudioColumns.IS_MUSIC);

		while (cursor.moveToNext()) {
			Song song = new Song();
			song.id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
			song.songname = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
			song.albumName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
			song.singer = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
			song.path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
			song.duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
			song.size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
			if (song.size > 1000 * 800) {
				// 注释部分是切割标题，分离出歌曲名和歌手 （本地媒体库读取的歌曲信息不规范）
				if (song.songname.contains("-")) {
					String[] str = song.songname.split("-");
					song.singer = str[0].trim();
					song.songname = str[1].trim();
				}
				list.add(song);
			}
		}
		cursor.close();
		return list;
	}

	public static List<Singer> getSingerData(Context context) {
		List<Singer> list = new ArrayList<Singer>();
		String[] mediaColumns = new String[] { MediaStore.Audio.Artists.NUMBER_OF_TRACKS,
				MediaStore.Audio.Artists.ARTIST };
		Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI, mediaColumns,
				null, null, MediaStore.Audio.Artists.ARTIST);
		while (cursor.moveToNext()) {
			Singer singer = new Singer();
			singer.songNum = cursor.getInt(cursor.getColumnIndex("NUMBER_OF_TRACKS"));
			singer.singerName = cursor.getString(cursor.getColumnIndex("ARTIST"));
			list.add(singer);
		}
		cursor.close();
		return list;
	}

	/**
	 * 把网络的音乐列表变为本地的
	 * 
	 * @param songList
	 */
	public static List<Song> setLocalMusicList(List<NetSongList> songList, Context context, String singerPicUrl) {
		List<Song> netToLocal = new ArrayList<Song>();
		try {
			for (int i = 0; i < songList.size(); i++) {

				Song song = new Song();
				song.albumName = songList.get(i).albumName;
				song.duration = songList.get(i).urlList.get(1).duration;
				song.id = i;
				song.path = songList.get(i).urlList.get(1).url;
				song.singer = songList.get(i).singerName;
				song.size = songList.get(i).urlList.get(1).size;
				song.songname = songList.get(i).name;
				song.singerPicPath = singerPicUrl;
				netToLocal.add(song);
			}
			return netToLocal;
		} catch (Exception e) {
			Toast.makeText(context, "要付费哦", 0).show();

			e.printStackTrace();
			return null;
		}

	}

	public static void setNetSingerPic(int albumId) {
		RequestParams params = new RequestParams(NetPathUtils.MUSIC_ALBUM_PATH + albumId);
		x.http().get(params, new CommonCallback<String>() {
			public void onSuccess(String result) {
				Gson gson = new Gson();
				SingerPicUrl singerPic = gson.fromJson(result, SingerPicUrl.class);
				MusicConstant.SINGER_RUL_PIC_PATH = null;
				MusicConstant.SINGER_RUL_PIC_PATH = singerPic.data.singerPicUrl;
			}

			public void onCancelled(CancelledException cex) {

			}

			public void onError(Throwable ex, boolean isOnCallback) {
				ex.printStackTrace();
			}

			public void onFinished() {

			}
		});

	}

}
