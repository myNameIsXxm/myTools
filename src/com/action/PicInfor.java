package com.action;

public class PicInfor {

	private String picUrl;
	private int width;
	private int height;
	private int top;
	private int left;


	public PicInfor() {}
	public PicInfor(String picUrl, int width, int height, int top, int left) {
		this.picUrl = picUrl;
		this.width = width;
		this.height = height;
		this.top = top;
		this.left = left;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getTop() {
		return top;
	}
	public void setTop(int top) {
		this.top = top;
	}
	public int getLeft() {
		return left;
	}
	public void setLeft(int left) {
		this.left = left;
	}
}
