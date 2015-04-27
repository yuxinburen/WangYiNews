package com.common.json;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.davie.domain.DownNew;
import com.davie.domain.ImageDetail;
import com.davie.domain.Login;
import com.davie.domain.New;
import com.davie.domain.Pic;
import com.davie.domain.Register;
import com.davie.domain.TopNew;
import com.davie.domain.Video;

public class JsonTools {

	public static New pareJson(String jsonStr) {
		New new1 = new New();
		try {
			JSONObject jsonObject = new JSONObject(jsonStr);
			JSONObject data = jsonObject.getJSONObject("data");

			// 上部的新闻
			JSONArray top_news = data.getJSONArray("top_news");
			List<TopNew> topNews = new ArrayList<TopNew>();
			for (int i = 0; i < top_news.length(); i++) {
				JSONObject dataObject = top_news.getJSONObject(i);
				TopNew topNew = new TopNew();

				topNew.setId(dataObject.optString("id"));
				topNew.setTitle(dataObject.optString("title"));
				topNew.setCover_pic(dataObject.optString("cover_pic"));

				topNews.add(topNew);
			}

			// 下部分的细纹
			JSONArray down_news = data.getJSONArray("down_news");
			List<DownNew> downNews = new ArrayList<DownNew>();
			for (int i = 0; i < down_news.length(); i++) {
				JSONObject dataObject = down_news.getJSONObject(i);
				DownNew downNew = new DownNew();

				downNew.setId(dataObject.optString("id"));
				downNew.setTitle(dataObject.optString("title"));
				downNew.setContent(dataObject.optString("content"));
				downNew.setCreate_time(dataObject.optString("create_time"));
				downNew.setCover_pic(dataObject.optString("cover_pic"));
				downNew.setDescript(dataObject.optString("descript"));
				downNew.setComment_total(dataObject.optString("comment_total"));

				downNews.add(downNew);
			}

			new1.setTopNews(topNews);
			new1.setDownNews(downNews);
			return new1;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<Pic> parsePic(String jsonStr) {
		List<Pic> list = new ArrayList<Pic>();
		try {
			JSONObject jsonObject = new JSONObject(jsonStr);
			// 全部的列表
			JSONArray jsonArray = jsonObject.optJSONArray("data");
			if (jsonArray != null) {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject dataObject = jsonArray.getJSONObject(i);
					Pic pic = new Pic();

					pic.setId(dataObject.optString("id"));
					pic.setTitle(dataObject.optString("title"));
					pic.setContent(dataObject.optString("content"));
					pic.setPic_total(dataObject.optString("pic_total"));
					JSONArray listaArray = dataObject.optJSONArray("pic_list");
					List<String> picList = new ArrayList<String>();

					if (listaArray != null) {
						for (int j = 0; j < listaArray.length(); j++) {
							String picStr = listaArray.getString(j);
							picList.add(picStr);
						}
					}

					pic.setDescript(dataObject.optString("descript"));

					pic.setPic_list(picList);
					list.add(pic);
				}
			}
			return list;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ImageDetail parseNewDetail(String jsonStr) {
		try {
			JSONObject jsonObject = new JSONObject(jsonStr);
			// 全部的列表
			JSONObject dataObject = jsonObject.getJSONObject("data");
			ImageDetail imageDetail = new ImageDetail();

			imageDetail.setNews_id(dataObject.optString("news_id"));
			imageDetail.setTitle(dataObject.optString("title"));
			imageDetail.setContent(dataObject.optString("content"));
			imageDetail.setCreate_time(dataObject.optString("create_time"));
			JSONArray listaArray = dataObject.optJSONArray("pic_list");
			List<String> picList = new ArrayList<String>();

			if (listaArray != null) {
				for (int j = 0; j < listaArray.length(); j++) {
					String picStr = listaArray.getString(j);
					picList.add(picStr);
				}
			}

			imageDetail.setCover_pic(dataObject.optString("cover_pic"));

			imageDetail.setPic_list(picList);

			return imageDetail;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<Video> parseVideo(String jsonStr) {
		List<Video> videoList = new ArrayList<Video>();
		try {
			JSONObject jsonObject = new JSONObject(jsonStr);
			// 全部的列表
			JSONArray dataArray = jsonObject.optJSONArray("data");
			if (dataArray != null) {
				for (int i = 0; i < dataArray.length(); i++) {
					JSONObject dataObject = dataArray.getJSONObject(i);
					Video video = new Video();

					video.setPic(dataObject.optString("pic"));
					video.setPlay_num(dataObject.optString("play_num"));
					video.setTitle(dataObject.optString("title"));
					video.setVideo_url(dataObject.optString("video_url"));

					videoList.add(video);
				}
			}
			return videoList;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * { "code": 200, "data": { "address": "67657", "birthday": "0000-00-00",
	 * "client_type": null, "create_time": "2015-01-07 16:10:16",
	 * "device_model": "", "device_token": "", "header_img":
	 * "http://www.juba.com/Public/upload/2015-01-22/100x100_54c0f77c9316f.jpg",
	 * "id": "10004", "ip": "192.168.27.212", "jid": "jid10004@1000phone.net",
	 * "last_login_time": "2015-02-25 21:57:38", "lbs_lat": "40.332595",
	 * "lbs_long": "-132.030319", "nickname": "2232232撒", "os_version": "",
	 * "sex": "", "status": "disabled", "token": "a9d80ce8c8d2f61a", "username":
	 * "testtoken" }, "message": "登录成功!" }
	 */
	/**
	 * 登录信息
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Login pareseLogin(String jsonStr) {
		try {
			JSONObject jsonObject = new JSONObject(jsonStr);
			JSONObject dataObject = jsonObject.optJSONObject("data");
			Login login = new Login();

			login.setCode(jsonObject.optInt("code"));
			if (dataObject != null) {
				login.setAddress(dataObject.optString("address"));
				login.setBirthday(dataObject.optString("birthday"));
				login.setClient_type(dataObject.optString("client_type"));
				login.setCreate_time(dataObject.optString("create_time"));

				login.setDevice_model(dataObject.optString("device_model"));
				login.setDevice_token(dataObject.optString("device_token"));
				login.setHeader_img(dataObject.optString("header_img"));
				login.setId(dataObject.optString("id"));

				login.setIp(dataObject.optString("ip"));
				login.setJid(dataObject.optString("jid"));
				login.setLast_login_time(dataObject
						.optString("last_login_time"));
				login.setLbs_lat(dataObject.optString("lbs_lat"));

				login.setLbs_long(dataObject.optString("lbs_long"));
				login.setNickname(dataObject.optString("nickname"));
				login.setOs_version(dataObject.optString("os_version"));
				login.setSex(dataObject.optString("sex"));

				login.setStatus(dataObject.optString("status"));
				login.setToken(dataObject.optString("token"));
				login.setUsername(dataObject.optString("username"));
				login.setMessage(dataObject.optString("message"));
			}
			return login;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<String> parseFavorite(String jsonStr) {
		List<String> favorites = new ArrayList<String>();
		try {
			JSONObject jsonObject = new JSONObject(jsonStr);
			// 全部的列表
			JSONArray datArray = jsonObject.getJSONArray("data");

			for (int i = 0; i < datArray.length(); i++) {
				JSONObject object = datArray.getJSONObject(i);
				favorites.add(object.optString("title"));
			}
			return favorites;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 注册信息
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Register pareseRegister(String jsonStr) {
		try {
			JSONObject jsonObject = new JSONObject(jsonStr);
			// 全部的列表
			JSONObject dataObject = jsonObject.getJSONObject("data");
			Register register = new Register();

			register.setAddress(dataObject.optString("address"));
			register.setBirthday(dataObject.optString("birthday"));
			register.setCreate_time(dataObject.optString("create_time"));

			register.setDevice_model(dataObject.optString("device_model"));
			register.setHeader_img(dataObject.optString("header_img"));
			register.setId(dataObject.optString("id"));
			register.setPassword(dataObject.optString("password"));

			register.setIp(dataObject.optString("ip"));
			register.setJid(dataObject.optString("jid"));
			register.setLbs_lat(dataObject.optString("lbs_lat"));

			register.setLbs_long(dataObject.optString("lbs_long"));
			register.setNickname(dataObject.optString("nickname"));
			register.setOs_version(dataObject.optString("os_version"));
			register.setSex(dataObject.optString("sex"));

			register.setToken(dataObject.optString("token"));
			register.setUsername(dataObject.optString("username"));
			register.setMessage(dataObject.optString("message"));

			return register;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
}
