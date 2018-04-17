package com.frame;

import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import com.action.Data;
import com.action.Util;

@SuppressWarnings("serial")
public class trayPopup extends PopupMenu {
	public trayPopup() {
		MenuItem item1 = new MenuItem("图片换色");
		MenuItem item2 = new MenuItem("退出程序");
		MenuItem item3 = new MenuItem("屏幕画图");
		MenuItem item4 = new MenuItem("批量改名");
		MenuItem item5 = new MenuItem("守护进程");
		MenuItem item6 = new MenuItem("图片合成");
		MenuItem item7 = new MenuItem("守护内存");
		MenuItem item8 = new MenuItem("备份过程");
		MenuItem item9 = new MenuItem("EXCEL比对");
		MenuItem item10 = new MenuItem("关于软件");

		add(item7);
		add(item6);
		add(item5);
		add(item8);
		add(item1);
		add(item4);
		add(item3);
		add(item10);
		add(item9);
		add(item2);
		// 图片合成
		item6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CombPicFrame m = CombPicFrame.getSingleton();
				m.setTitle("图片合成");
				m.showFrame();
				m.setSize(283, 283);
				m.setLocation(400, 400);
				m.setVisible(true);
				m.requestFocus();
				m.setResizable(false);
				m.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						CombPicFrame.getSingleton().setVisible(false);
					}
				});
			}
		});
		// 守护进程
		item5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DaemonFrame m = DaemonFrame.getSingleton();
				m.setTitle("守护进程");
				m.showFrame();
				m.setSize(309, 213);
				m.setLocation(400, 400);
				m.setVisible(true);
				m.requestFocus();
				m.setResizable(false);
				m.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						DaemonFrame.getSingleton().setVisible(false);
					}
				});
			}
		});
		// 守护进程
		item9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ExcelCompFrame m = ExcelCompFrame.getSingleton();
				m.setTitle("守护进程");
				m.showFrame();
				m.setSize(300, 238);
				m.setLocation(400, 400);
				m.setVisible(true);
				m.requestFocus();
				m.setResizable(false);
				m.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						ExcelCompFrame.getSingleton().setVisible(false);
					}
				});
			}
		});
		// 备份过程
		item8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProBackFrame m = ProBackFrame.getSingleton();
				m.setTitle("备份过程");
				m.showFrame();
				m.setSize(300, 334);
				m.setLocation(400, 400);
				m.setVisible(true);
				m.requestFocus();
				m.setResizable(false);
				m.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						ProBackFrame.getSingleton().setVisible(false);
					}
				});
			}
		});
		// 守护内存
		item7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MemoryLargerRestartFrame m = MemoryLargerRestartFrame.getSingleton();
				m.setTitle("守护内存");
				m.showFrame();
				m.setSize(309, 241);
				m.setLocation(400, 400);
				m.setVisible(true);
				m.requestFocus();
				m.setResizable(false);
				m.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						DaemonFrame.getSingleton().setVisible(false);
					}
				});
			}
		});
		// 屏幕画图
		item3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Util.exeRun(Data.screenDrawPath);
			}
		});
		// 作者信息
		item10.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				about.getSingleton().showFrame();
			}
		});
		// 退出程序
		item2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		// 换色
		item1.addActionListener(new ActionListener() {
			@SuppressWarnings("restriction")
			public void actionPerformed(ActionEvent e) {
				ChangeColorFrame m = ChangeColorFrame.getSingleton();
				m.setTitle("图片换色");
				m.showFrame();
				m.setSize(674, 63);
				m.setLocation(400, 400);
				m.setVisible(true);
				m.requestFocus();
				m.setResizable(false);
				m.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						ChangeColorFrame.getSingleton().setVisible(false);
					}
				});
			}
		});
		// 改名
		item4.addActionListener(new ActionListener() {
			@SuppressWarnings("restriction")
			public void actionPerformed(ActionEvent e) {
				ChangNameFrame m = ChangNameFrame.getSingleton();
				m.setTitle("改名");
				m.showFrame();
				m.setSize(283, 145);
				m.setLocation(400, 400);
				m.setVisible(true);
				m.requestFocus();
				m.setResizable(false);
			}
		});
	}
}
