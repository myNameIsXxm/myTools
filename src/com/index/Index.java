package com.index;

import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.frame.trayPopup;

public class Index {
	public static void main(String[] args) {
		try {
			TrayIcon icon = new TrayIcon(ImageIO.read(Index.class.getResource("/img/icon.gif")), "西西小工具",
					new trayPopup());
			icon.setImageAutoSize(true);
			SystemTray.getSystemTray().add(icon);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
}