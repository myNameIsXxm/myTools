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
		MenuItem item1 = new MenuItem("ͼƬ��ɫ");
		MenuItem item2 = new MenuItem("�˳�����");
		MenuItem item3 = new MenuItem("��Ļ��ͼ");
		MenuItem item4 = new MenuItem("��������");
		MenuItem item5 = new MenuItem("�ػ�����");
		MenuItem item6 = new MenuItem("ͼƬ�ϳ�");
		MenuItem item7 = new MenuItem("�ػ��ڴ�");
		MenuItem item8 = new MenuItem("���ݹ���");
		MenuItem item9 = new MenuItem("EXCEL�ȶ�");
		MenuItem item10 = new MenuItem("�������");

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
		// ͼƬ�ϳ�
		item6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CombPicFrame m = CombPicFrame.getSingleton();
				m.setTitle("ͼƬ�ϳ�");
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
		// �ػ�����
		item5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DaemonFrame m = DaemonFrame.getSingleton();
				m.setTitle("�ػ�����");
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
		// �ػ�����
		item9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ExcelCompFrame m = ExcelCompFrame.getSingleton();
				m.setTitle("�ػ�����");
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
		// ���ݹ���
		item8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProBackFrame m = ProBackFrame.getSingleton();
				m.setTitle("���ݹ���");
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
		// �ػ��ڴ�
		item7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MemoryLargerRestartFrame m = MemoryLargerRestartFrame.getSingleton();
				m.setTitle("�ػ��ڴ�");
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
		// ��Ļ��ͼ
		item3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Util.exeRun(Data.screenDrawPath);
			}
		});
		// ������Ϣ
		item10.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				about.getSingleton().showFrame();
			}
		});
		// �˳�����
		item2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		// ��ɫ
		item1.addActionListener(new ActionListener() {
			@SuppressWarnings("restriction")
			public void actionPerformed(ActionEvent e) {
				ChangeColorFrame m = ChangeColorFrame.getSingleton();
				m.setTitle("ͼƬ��ɫ");
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
		// ����
		item4.addActionListener(new ActionListener() {
			@SuppressWarnings("restriction")
			public void actionPerformed(ActionEvent e) {
				ChangNameFrame m = ChangNameFrame.getSingleton();
				m.setTitle("����");
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
