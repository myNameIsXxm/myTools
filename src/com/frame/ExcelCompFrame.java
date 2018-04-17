package com.frame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.action.Util;

@SuppressWarnings("serial")
public class ExcelCompFrame extends JFrame {

	private JLabel title2, jl1, jl2, jl3, jl4, jl9;
	private JTextField jtf1, jtf2, jtf3, jtf4, jtf9;
	private JButton jb, jb1;
	private Thread daemonThread;

	private ExcelCompFrame() {
	}

	private static ExcelCompFrame singleton = null;

	public static ExcelCompFrame getSingleton() {
		if (singleton == null) {
			singleton = new ExcelCompFrame();
		}
		return singleton;
	}

	public void showFrame() {
		// ------------------------------------布局
		final JPanel center = new JPanel();
		add(center, BorderLayout.CENTER);
		JPanel bottom = new JPanel();
		add(bottom, BorderLayout.SOUTH);
		// ----------------------------center
		title2 = new JLabel("------------------------ 参数配置 ------------------------");
		jl1 = new JLabel("   Excel1");
		jl9 = new JLabel("   Excel2");
		jl2 = new JLabel("Column1");
		jl3 = new JLabel("日志文件");
		jl4 = new JLabel("Column2");
		jtf1 = new JTextField("C://js.xls", 20);
		jtf2 = new JTextField("3", 20);
		jtf9 = new JTextField("C://jssh.xls", 20);
		jtf3 = new JTextField("C://daemon.log", 20);
		jtf4 = new JTextField("4", 20);

		center.add(title2);
		center.add(jl1);
		center.add(jtf1);
		center.add(jl2);
		center.add(jtf2);
		center.add(jl9);
		center.add(jtf9);
		center.add(jl4);
		center.add(jtf4);
		center.add(jl3);
		center.add(jtf3);
		// ----------------------------buttom
		jb = new JButton("开始分析");
		jb1 = new JButton("查看日志");
		bottom.add(jb);
		bottom.add(jb1);
		jb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				daemonThread = new Thread(new Runnable() {
					@Override
					public void run() {
						String cs1 = jtf1.getText();
						String cs2 = jtf2.getText();
						String cs3 = jtf9.getText();
						String cs4 = jtf4.getText();
						String cs5 = jtf3.getText();
						try {
							// -------
							Set<String> set1 = new HashSet<String>();
							HSSFSheet sheet = new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(cs1)))
									.getSheetAt(0);
							for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
								String va = sheet.getRow(i).getCell(Integer.parseInt(cs2) - 1).getStringCellValue();
								if (!va.isEmpty()) {
									set1.add(va);
								}
							}
							// -------
							Set<String> set2 = new HashSet<String>();
							sheet = new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(cs3))).getSheetAt(0);
							for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
								String va = sheet.getRow(i).getCell(Integer.parseInt(cs4) - 1).getStringCellValue();
								if (!va.isEmpty()) {
									set2.add(va);
								}
							}
							Util.log(cs5, "Excel1数据量:" + set1.size());
							Util.log(cs5, "Excel2数据量:" + set2.size());
							// 比较set1和set2
							Util.logNoLine(cs5, "Excel1中存在，Excel2中不存在:");
							for (String s : set1) {
								if (!set2.contains(s)) {
									Util.logNoLine(cs5, s + " ");
								}
							}
							Util.log(cs5, "");
							Util.logNoLine(cs5, "Excel2中存在，Excel1中不存在:");
							for (String s : set2) {
								if (!set1.contains(s)) {
									Util.logNoLine(cs5, s + " ");
								}
							}
							Util.log(cs5, "");
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
				daemonThread.start();
			}

		});
		jb1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Util.openFile2(jtf3.getText());
			}
		});

	}

	public static void main(String[] args) {
		ExcelCompFrame m = ExcelCompFrame.getSingleton();
		m.setTitle("备份过程");
		m.showFrame();
		m.setSize(300, 238);
		m.setLocation(400, 400);
		m.setVisible(true);
		m.requestFocus();
		m.setResizable(false);
		m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
