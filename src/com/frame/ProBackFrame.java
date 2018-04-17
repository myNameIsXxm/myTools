package com.frame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.action.Util;

@SuppressWarnings("serial")
public class ProBackFrame extends JFrame {

	private JLabel title, title2, jl1, jl2, jl3, jl4, jl5, jl9;
	private JTextField jtf1, jtf2, jtf3, jtf4, jtf5, jtf9;
	private JButton jb, jb1, jb2, jb3;
	private Thread daemonThread;

	private ProBackFrame() {
	}

	private static ProBackFrame singleton = null;

	public static ProBackFrame getSingleton() {
		if (singleton == null) {
			singleton = new ProBackFrame();
		}
		return singleton;
	}

	public void showFrame() {
		// ------------------------------------布局
		// JPanel top = new JPanel();
		// add(top, BorderLayout.NORTH);
		final JPanel center = new JPanel();
		add(center, BorderLayout.CENTER);
		JPanel bottom = new JPanel();
		add(bottom, BorderLayout.SOUTH);
		// ----------------------------center

		title = new JLabel("------------------------ 预定方案 ------------------------");
		center.add(title);
		Set<String> set = new HashSet<String>();
		Properties props = new Properties();
		final String configPath = "database.properties";
		try {
			props.load(new BufferedInputStream(new FileInputStream(configPath)));
			Enumeration en = props.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				set.add(key.split("_")[0]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (final String s : set) {
			JButton jba = new JButton(s);
			jba.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					jtf1.setText(Util.getValueByKey(configPath, s + "_ip"));
					jtf2.setText(Util.getValueByKey(configPath, s + "_db"));
					jtf9.setText(Util.getValueByKey(configPath, s + "_username"));
					jtf4.setText(Util.getValueByKey(configPath, s + "_password"));

				}
			});
			center.add(jba);
		}

		title2 = new JLabel("------------------------ 参数配置 ------------------------");

		jl1 = new JLabel("   IP地址");
		jl9 = new JLabel("   用户名");
		jl2 = new JLabel("   数据库");
		jl3 = new JLabel("日志文件");
		jl4 = new JLabel("       密码");
		jl5 = new JLabel("输出文件");
		jtf1 = new JTextField("10.41.192.61", 20);
		jtf2 = new JTextField("orcl", 20);
		jtf9 = new JTextField("czops", 20);
		jtf3 = new JTextField("C://daemon.log", 20);
		jtf4 = new JTextField("czops", 20);
		jtf5 = new JTextField("C:\\back", 14);
		jb2 = new JButton("选择");

		center.add(title2);
		center.add(jl1);
		center.add(jtf1);
		center.add(jl2);
		center.add(jtf2);
		center.add(jl9);
		center.add(jtf9);
		center.add(jl4);
		center.add(jtf4);
		center.add(jl5);
		center.add(jtf5);
		center.add(jb2);
		center.add(jl3);
		center.add(jtf3);
		// ----------------------------buttom
		jb = new JButton("开始导出");
		jb1 = new JButton("查看日志");
		jb3 = new JButton("打开文件夹");
		bottom.add(jb);
		bottom.add(jb1);
		bottom.add(jb3);
		jb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				daemonThread = new Thread(new Runnable() {
					@Override
					public void run() {
						String OWNER = jtf9.getText().toUpperCase();
						String PASS = jtf4.getText();
						String URL = "jdbc:oracle:thin:@" + jtf1.getText() + ":1521/" + jtf2.getText();
						String FILE_PATH = jtf5.getText()+ "\\";
						String logpath = jtf3.getText();
						File file = new File(FILE_PATH);
						if (!file.exists()) {
							file.mkdirs();
						}
						Connection con = null;
						PreparedStatement pStemt = null;
						try {
							con = DriverManager.getConnection(URL, OWNER, PASS);
							String pro = "select object_name as name from user_procedures";
							pStemt = con.prepareStatement(pro);
							ResultSet executeQuery = pStemt.executeQuery();
							while (executeQuery.next()) {
								pro = executeQuery.getString("NAME");
								create(FILE_PATH, pro.toUpperCase(), OWNER, logpath, URL, PASS);
							}
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							try {
								con.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					}
				});
				daemonThread.start();
			}

			public void create(String filepath, String name, String OWNER, String logpath, String URL, String PASS)
					throws IOException {
				String filename = name + ".sql";
				Util.log(logpath, "路径：" + filepath + filename);
				File file = new File(filepath);
				if (!file.exists()) {
					file.mkdirs();
				}
				FileWriter fw = new FileWriter(filepath + filename);
				PrintWriter pw = new PrintWriter(fw);

				String sql = "SELECT TEXT FROM ALL_SOURCE WHERE OWNER = '" + OWNER + "' AND NAME = '" + name
						+ "' ORDER BY type,LINE";
				PreparedStatement pStemt = null;
				try {
					Connection con = DriverManager.getConnection(URL, OWNER, PASS);
					pStemt = con.prepareStatement(sql);
					ResultSet executeQuery = pStemt.executeQuery();
					while (executeQuery.next()) {
						pw.print(executeQuery.getString("TEXT"));
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					pw.flush();
					pw.close();
				}
			}
		});
		jb1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Util.openFile2(jtf3.getText());
			}
		});
		// 输出
		jb2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setApproveButtonText("确定");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // 设置只选择目录
				int returnVal = chooser.showOpenDialog(ProBackFrame.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					jtf5.setText(chooser.getSelectedFile().getAbsolutePath());
				}
			}
		});

		// 打开result
		jb3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Util.openFile(jtf5.getText());
			}
		});
	}

	public static void main(String[] args) {
		ProBackFrame m = ProBackFrame.getSingleton();
		m.setTitle("备份过程");
		m.showFrame();
		m.setSize(300, 334);
		m.setLocation(400, 400);
		m.setVisible(true);
		m.requestFocus();
		m.setResizable(false);
		m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
