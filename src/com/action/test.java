package com.action;

import java.awt.Font;

public class test {
	public static void main(String[] args) {
		String string="  ";
		Integer width=Util.getStrigWidthAndHeight(string,new Font("ËÎÌו",Font.PLAIN, 10)).get("width");
		System.out.println(width);
	}
}
