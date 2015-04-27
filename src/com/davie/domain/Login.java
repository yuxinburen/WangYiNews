package com.davie.domain;

import java.io.Serializable;



public class Login implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int code;
	private String address;
	private String birthday;
	private String client_type;
	private String create_time;
	private String device_model;
	private String device_token;
	private String header_img;
	private String id;
	private String ip;
	private String jid;
	private String last_login_time;
	private String lbs_lat;
	private String lbs_long;
	private String nickname;
	private String os_version;
	private String sex;
	private String status;
	private String username;
	private String message;
	private String token;
	
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getClient_type() {
		return client_type;
	}
	public void setClient_type(String client_type) {
		this.client_type = client_type;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getDevice_model() {
		return device_model;
	}
	public void setDevice_model(String device_model) {
		this.device_model = device_model;
	}
	public String getDevice_token() {
		return device_token;
	}
	public void setDevice_token(String device_token) {
		this.device_token = device_token;
	}
	public String getHeader_img() {
		return header_img;
	}
	public void setHeader_img(String header_img) {
		this.header_img = header_img;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getJid() {
		return jid;
	}
	public void setJid(String jid) {
		this.jid = jid;
	}
	public String getLast_login_time() {
		return last_login_time;
	}
	public void setLast_login_time(String last_login_time) {
		this.last_login_time = last_login_time;
	}
	public String getLbs_lat() {
		return lbs_lat;
	}
	public void setLbs_lat(String lbs_lat) {
		this.lbs_lat = lbs_lat;
	}
	public String getLbs_long() {
		return lbs_long;
	}
	public void setLbs_long(String lbs_long) {
		this.lbs_long = lbs_long;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getOs_version() {
		return os_version;
	}
	public void setOs_version(String os_version) {
		this.os_version = os_version;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "Login [address=" + address + ", birthday=" + birthday
				+ ", client_type=" + client_type + ", create_time="
				+ create_time + ", device_model=" + device_model
				+ ", device_token=" + device_token + ", header_img="
				+ header_img + ", id=" + id + ", ip=" + ip + ", jid=" + jid
				+ ", last_login_time=" + last_login_time + ", lbs_lat="
				+ lbs_lat + ", lbs_long=" + lbs_long + ", nickname=" + nickname
				+ ", os_version=" + os_version + ", sex=" + sex + ", status="
				+ status + ", username=" + username + ", message=" + message
				+ "]";
	}
	public Login(String address, String birthday, String client_type,
			String create_time, String device_model, String device_token,
			String header_img, String id, String ip, String jid,
			String last_login_time, String lbs_lat, String lbs_long,
			String nickname, String os_version, String sex, String status,
			String username, String message) {
		super();
		this.address = address;
		this.birthday = birthday;
		this.client_type = client_type;
		this.create_time = create_time;
		this.device_model = device_model;
		this.device_token = device_token;
		this.header_img = header_img;
		this.id = id;
		this.ip = ip;
		this.jid = jid;
		this.last_login_time = last_login_time;
		this.lbs_lat = lbs_lat;
		this.lbs_long = lbs_long;
		this.nickname = nickname;
		this.os_version = os_version;
		this.sex = sex;
		this.status = status;
		this.username = username;
		this.message = message;
	}
	public Login() {
		super();
	}
}
