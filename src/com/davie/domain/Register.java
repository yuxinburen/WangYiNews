package com.davie.domain;

public class Register {

	private String address;
	private String birthday;
	private String create_time;
	private String device_model;
	private String header_img;
	private String id;
	private String jid;
	private String ip;
	private String lbs_lat;
	private String lbs_long;
	private String nickname;
	private String os_version;
	private String password;
	private String sex;
	private String token;
	private String username;
	private String message;
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
	public String getJid() {
		return jid;
	}
	public void setJid(String jid) {
		this.jid = jid;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
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
		return "Register [address=" + address + ", birthday=" + birthday
				+ ", create_time=" + create_time + ", device_model="
				+ device_model + ", header_img=" + header_img + ", id=" + id
				+ ", jid=" + jid + ", ip=" + ip + ", lbs_lat=" + lbs_lat
				+ ", lbs_long=" + lbs_long + ", nickname=" + nickname
				+ ", os_version=" + os_version + ", password=" + password
				+ ", sex=" + sex + ", token=" + token + ", username="
				+ username + ", message=" + message + "]";
	}
	public Register(String address, String birthday, String create_time,
			String device_model, String header_img, String id, String jid,
			String ip, String lbs_lat, String lbs_long, String nickname,
			String os_version, String password, String sex, String token,
			String username, String message) {
		super();
		this.address = address;
		this.birthday = birthday;
		this.create_time = create_time;
		this.device_model = device_model;
		this.header_img = header_img;
		this.id = id;
		this.jid = jid;
		this.ip = ip;
		this.lbs_lat = lbs_lat;
		this.lbs_long = lbs_long;
		this.nickname = nickname;
		this.os_version = os_version;
		this.password = password;
		this.sex = sex;
		this.token = token;
		this.username = username;
		this.message = message;
	}
	public Register() {
		super();
	}
}
