package com.davie.domain;

import java.util.List;

public class New {

	private String code ;
	private List<TopNew> topNews;
	private List<DownNew> downNews;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<TopNew> getTopNews() {
		return topNews;
	}
	public void setTopNews(List<TopNew> topNews) {
		this.topNews = topNews;
	}
	public List<DownNew> getDownNews() {
		return downNews;
	}
	public void setDownNews(List<DownNew> downNews) {
		this.downNews = downNews;
	}
	@Override
	public String toString() {
		return "New [code=" + code + ", topNews=" + topNews + ", downNews="
				+ downNews + "]";
	}
	public New(String code, List<TopNew> topNews, List<DownNew> downNews) {
		super();
		this.code = code;
		this.topNews = topNews;
		this.downNews = downNews;
	}
	public New() {
		super();
	}
}
