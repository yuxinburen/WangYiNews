package com.davie.domain;

public class Video {
	private String pic;
	private String play_num;
	private String title;
	private String video_url;
	
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getPlay_num() {
		return play_num;
	}
	public void setPlay_num(String play_num) {
		this.play_num = play_num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getVideo_url() {
		return video_url;
	}
	public void setVideo_url(String video_url) {
		this.video_url = video_url;
	}
	public Video(String pic, String play_num, String title, String video_url) {
		super();
		this.pic = pic;
		this.play_num = play_num;
		this.title = title;
		this.video_url = video_url;
	}
	public Video() {
		super();
	}
	@Override
	public String toString() {
		return "Video [pic=" + pic + ", play_num=" + play_num + ", title="
				+ title + ", video_url=" + video_url + "]";
	}
}
