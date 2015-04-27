package com.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesHelper {

	private SharedPreferences prefs;
	private Editor editor;
	private static final String PREFS_FILE_NAME = "loginState";
	private static final String STATE = "state";
	private static final String HEADER_IMG = "header_img";
	private static final String NICKNAME = "nickname";
	private static final String SEX = "sex";
	private static final String USERNAME="username";
	private static final String TOKEN = "token";
	

	public SharedPreferencesHelper(Context context) {
		super();
		this.prefs = context.getSharedPreferences(PREFS_FILE_NAME,
				Context.MODE_PRIVATE);
		this.editor = this.prefs.edit();
	}

	public boolean updateState(boolean state) {
		try {
			editor.putBoolean(STATE, state);
			editor.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean getState() {
		boolean value = prefs.getBoolean(STATE, false);
		return value;
	}

	public boolean saveNickName(String value) {
		boolean ret = saveDataToPrefs(NICKNAME, value);
		return ret;
	}

	public boolean saveHeaderImg(String value) {
		boolean ret = saveDataToPrefs(HEADER_IMG, value);
		return ret;
	}

	public boolean saveSex(String value) {
		boolean ret = saveDataToPrefs(SEX, value);
		return ret;
	}
	
	
	public boolean saveUsername(String value){
		boolean ret = saveDataToPrefs(USERNAME, value);
		return ret;
	}
	
	public boolean saveToken(String value){
		boolean ret = saveDataToPrefs(TOKEN, value);
		return ret;
	}

	// 向prefs中插入数据
	private boolean saveDataToPrefs(String key, String value) {
		try {
			editor.putString(key, value);
			editor.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// 从prefs中查找数据
	public String getNickName() {
		String value = prefs.getString(NICKNAME, null);
		return value;
	}

	public String getHeaderImg() {
		String value = prefs.getString(HEADER_IMG, null);
		return value;
	}

	public String getSex() {
		String value = prefs.getString(SEX, null);
		return value;
	}
	
	public String getUsername() {
		String value = prefs.getString(USERNAME, null);
		return value;
	}
	
	public String getToken(){
		String value = prefs.getString(TOKEN, null);
		return value;
	}

	
}
