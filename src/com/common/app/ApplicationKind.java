package com.common.app;

import android.app.Application;
/**
 * 定义好的全局变量，用于所有的Activity使用
 * @author asus
 */
public class ApplicationKind extends Application{
	private String token;
	private String userName;
	private String touXiangUrl;
	public String getUserName() { 
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getTouXiangUrl() {
		return touXiangUrl;
	}
	public void setTouXiangUrl(String touXiangUrl) {
		this.touXiangUrl = touXiangUrl;
	}
}
