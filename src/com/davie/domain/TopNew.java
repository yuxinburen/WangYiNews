package com.davie.domain;

import java.io.Serializable;

public class TopNew implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id ;
	private String title;
	private String cover_pic;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCover_pic() {
		return cover_pic;
	}
	public void setCover_pic(String cover_pic) {
		this.cover_pic = cover_pic;
	}
	public TopNew(String id, String title, String cover_pic) {
		super();
		this.id = id;
		this.title = title;
		this.cover_pic = cover_pic;
	}
	public TopNew() {
		super();
	}
	@Override
	public String toString() {
		return "TopNew [id=" + id + ", title=" + title + ", cover_pic="
				+ cover_pic + "]";
	}
	
	
}
