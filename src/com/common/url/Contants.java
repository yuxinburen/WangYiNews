package com.common.url;

public class Contants {
	
	//新闻分类
	public static final String CATE_LIST = "http://1000phone.net:8088/qfapp/index.php/juba/news/cate_list?type=";
	
	//新闻列表
	public static final String NEWS_LIST = "http://1000phone.net:8088/qfapp/index.php/juba/news/get_news_list?cate_id=";
	
	//图盘新闻详情 和文字新闻详情一个同一个接口
	public static final String NEWS_DETAIL = "http://1000phone.net:8088/qfapp/index.php/juba/news/news_detail?news_id=";
	
	//图片新闻列表 
	public static final String PIC_NEWS = "http://1000phone.net:8088/qfapp/index.php/juba/news/get_pic_news_list?cate_id=";
	
	public static final String VIDEO = "http://1000phone.net:8088/qfapp/index.php/juba/news/get_video_news_list?cate_id=";
	
	//登录
	public static final String LOGIN = "http://1000phone.net:8088/qfapp/index.php/juba/index/do_login?";
	
	//评论
	public static final String COMMENT = "http://1000phone.net:8088/qfapp/index.php/juba/news/add_comment?";
	
	//收藏
	public static final String COLLECT = "http://1000phone.net:8088/qfapp/index.php/juba/news/stow_news?id=";
	
	//我的收藏
	public static final String FAVORITE = "http://1000phone.net:8088/qfapp/index.php/juba/news/get_stow_news?token=";
	
	//我的跟帖(评论)
	public static final String MYCOMMENTS = "http://1000phone.net:8088/qfapp/index.php/juba/news/get_my_comment?token=";
	
	//修改个人资料
	public static final String UPDATE = "http://1000phone.net:8088/qfapp/index.php/juba/user/do_mod_user?";
	
	//注册
	/**
	 * 参数名			必选		类型及范围		说明
	 * username		是		string		账号名称
	 * password		是		string		账号密码
     * sequence		是		string		手机序列号
	 * verify_code	是		string		验证码，访问验证码接口时返回的数字
	 * nickname		否		string		用户昵称
	 * sex			否		enum		M：代表 男 W：代表 女，默认为保密
	 * birthday		否		date		出生日期：比如 1990-01-02
	 * address		否		string		家庭住址
	 * format		否		string		json 或 xml，返回值的数据格式，默认为json
	 */
	public static final String REGISTER = "http://1000phone.net:8088/qfapp/index.php/juba/index/do_register?";
	
	
	public static final String RANDOM = "http://1000phone.net:8088/qfapp/index.php/juba/index/verify_code?sequence=";
}
