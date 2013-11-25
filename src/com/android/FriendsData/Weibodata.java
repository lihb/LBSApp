package com.android.FriendsData;

import com.google.android.maps.GeoPoint;


public class Weibodata {
	
	public String userName;
	public String content;
	public String img_url;
	public GeoPoint point;
	
	
	
	public Weibodata(GeoPoint point, String userName, String content, String img_url) {
		super();
		this.userName = userName;
		this.content = content;
		this.img_url = img_url;
		this.point = point;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("姓名："+this.userName);
		sb.append("\n内容：- " + this.content);
	    sb.append("\n头像 - " + this.img_url);
	    sb.append("\n位置 - " + this.point);
		return  sb.toString();
	}
	
	
}
