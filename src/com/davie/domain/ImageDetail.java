package com.davie.domain;

import java.io.Serializable;
import java.util.List;

public class ImageDetail implements Serializable{

	private static final long serialVersionUID = 1L;
	private String news_id;
	private String title;
	private String content;
	private String create_time;
	private List<String> pic_list;
	private String cover_pic;
	public String getNews_id() {
		return news_id;
	}
	public void setNews_id(String news_id) {
		this.news_id = news_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public List<String> getPic_list() {
		return pic_list;
	}
	public void setPic_list(List<String> pic_list) {
		this.pic_list = pic_list;
	}
	public String getCover_pic() {
		return cover_pic;
	}
	public void setCover_pic(String cover_pic) {
		this.cover_pic = cover_pic;
	}
	@Override
	public String toString() {
		return "ImageDetail [news_id=" + news_id + ", title=" + title
				+ ", content=" + content + ", create_time=" + create_time
				+ ", pic_list=" + pic_list + ", cover_pic=" + cover_pic + "]";
	}
	public ImageDetail(String news_id, String title, String content,
			String create_time, List<String> pic_list, String cover_pic) {
		super();
		this.news_id = news_id;
		this.title = title;
		this.content = content;
		this.create_time = create_time;
		this.pic_list = pic_list;
		this.cover_pic = cover_pic;
	}
	public ImageDetail() {
		super();
	}
	
}
