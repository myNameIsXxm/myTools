package com.frame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.action.Util;

/**
 * �ڴ��������
 */
public class MemoryLargerRestartFrame extends JFrame {

	private JLabel title, jl1, jl2, jl3, jl4, jl9;
	private JTextField jtf1, jtf2, jtf3, jtf4, jtf9;
	private JButton jb, jb1, jb2;
	private Thread daemonThread;
	private boolean isThreadAlive = false;

	private MemoryLargerRestartFrame() {
	}

	private static MemoryLargerRestartFrame singleton = null;

	public static MemoryLargerRestartFrame getSingleton() {
		if (singleton == null) {
			singleton = new MemoryLargerRestartFrame();
		}
		return singleton;
	}

	public void showFrame() {
		// ------------------------------------����
		JPanel top = new JPanel();
		add(top, BorderLayout.NORTH);
		final JPanel center = new JPanel();
		add(center, BorderLayout.CENTER);
		JPanel bottom = new JPanel();
		add(bottom, BorderLayout.SOUTH);
		// -------------------------------top
		title = new JLabel("------------------------ �������� ------------------------");
		top.add(title);
		// ----------------------------center

		jl1 = new JLabel("��������");
		jl9 = new JLabel("�����");
		jl2 = new JLabel("ִ������");
		jl3 = new JLabel("��־�ļ�");
		jl4 = new JLabel("�ڴ�����");
		jtf1 = new JTextField("lsass.exe", 20);
		jtf2 = new JTextField("shutdown -r -t 5", 20);
		jtf9 = new JTextField("5", 20);
		jtf3 = new JTextField("C://memory.log", 20);
		jtf4 = new JTextField("2048", 20);

		center.add(jl1);
		center.add(jtf1);
		center.add(jl4);
		center.add(jtf4);
		center.add(jl2);
		center.add(jtf2);
		center.add(jl9);
		center.add(jtf9);
		center.add(jl3);
		center.add(jtf3);
		// ----------------------------buttom
		jb = new JButton("��ʼ/ֹͣ");
		jb1 = new JButton("�鿴��־");
		jb2 = new JButton("�ػ�״̬");
		bottom.add(jb);
		bottom.add(jb1);
		bottom.add(jb2);
		jb.addActionListener(new ActionListener() {
			private String getShowNum(Long nc) {
				if (nc >= 1000000000l) {
					return new DecimalFormat("#.00").format(nc / 1000000000.0) + "GB";
				} else if (nc >= 1000000l) {
					return new DecimalFormat("#.00").format(nc / 1000000.0) + "MB";
				} else if (nc >= 1000l) {
					return new DecimalFormat("#.00").format(nc / 1000.0) + "KB";
				} else {
					return nc + "B";
				}

			}

			public void actionPerformed(ActionEvent e) {
				if (!isThreadAlive) {
					isThreadAlive = true;
					daemonThread = new Thread(new Runnable() {
						@Override
						public void run() {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
							String task = jtf1.getText();
							String shutCommand = jtf2.getText();
							Integer interval = Integer.parseInt(jtf9.getText());
							String file = jtf3.getText();
							Long criticalValue = Long.parseLong(jtf4.getText());
							String s;
							boolean isAlive = false;
							java.io.BufferedReader in;
							while (isThreadAlive) {
								try {
									java.lang.Process p = java.lang.Runtime.getRuntime().exec("tasklist");
									in = new java.io.BufferedReader(new java.io.InputStreamReader(p.getInputStream()));
									isAlive = false;
									String taskMsg = "";
									while ((s = in.readLine()) != null) {
										if (s.startsWith(task)) {
											taskMsg = s;
											break;
										}
									}
									in.close();
									System.out.println(taskMsg);
									String tasks[] = taskMsg.split(" ");
									Long nc = Long.parseLong(tasks[tasks.length - 2].replaceAll(",", ""));
									String dw = tasks[tasks.length - 1];
									if (dw.toUpperCase().equals("B")) {
										nc = nc * 1;
									} else if (dw.toUpperCase().equals("K")) {
										nc = nc * 1000;
									} else if (dw.toUpperCase().equals("M")) {
										nc = nc * 1000000;
									} else if (dw.toUpperCase().equals("G")) {
										nc = nc * 1000000000;
									}
									if (nc / 1000000 > criticalValue) {
										Util.log(file, "<" + sdf.format(new Date()) + "> " + task
												+ " is too larger , memory��" + getShowNum(nc));
										java.lang.Runtime.getRuntime().exec(shutCommand);
									} else {
										Util.log(file, "<" + sdf.format(new Date()) + "> " + task + " is good , memory��"
												+ getShowNum(nc));
									}
									Thread.sleep(interval * 1000);
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							}
						}
					});
					daemonThread.start();
				} else {
					isThreadAlive = false;
				}
			}
		});
		jb1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Util.openFile2(jtf3.getText());
			}
		});
		jb2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg = "";
				if (isThreadAlive) {
					msg = "�����ػ���...";
				} else {
					msg = "�ػ���ֹͣ��";
				}
				JOptionPane.showMessageDialog(null, msg);

			}
		});
	}

	public static void main(String[] args) {
		MemoryLargerRestartFrame m = MemoryLargerRestartFrame.getSingleton();
		m.setTitle("�ػ�����");
		m.showFrame();
		m.setSize(309, 241);
		m.setLocation(400, 400);
		m.setVisible(true);
		m.requestFocus();
		m.setResizable(true);
		m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
