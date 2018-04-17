package com.frame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.action.Combining;
import com.action.Data;
import com.action.Util;

@SuppressWarnings("serial")
public class CombPicFrame extends JFrame {

	private JLabel title, jl, jl1, jl2, jl3, jl4, jl5, jl6, jl9;
	private JTextField jtf1, jtf2, jtf3, jtf4, jtf5, jtf6, jtf9;
	private JButton jb, jb1, jb2, jb3, jb4;

	private CombPicFrame() {
	}

	private static CombPicFrame singleton = null;

	public static CombPicFrame getSingleton() {
		if (singleton == null) {
			singleton = new CombPicFrame();
		}
		return singleton;
	}

	public void showFrame() {
		// ------------------------------------布局
		JPanel top = new JPanel();
		add(top, BorderLayout.NORTH);
		final JPanel center = new JPanel();
		add(center, BorderLayout.CENTER);
		JPanel bottom = new JPanel();
		add(bottom, BorderLayout.SOUTH);
		// -------------------------------top
		title = new JLabel(
				"------------------------ 所需文件 ------------------------");
		top.add(title);
		// ----------------------------center

		jl = new JLabel(
				"------------------------ 定制区域 ------------------------");
		jl1 = new JLabel("底图路径");
		jl9 = new JLabel("输出文件");
		jl2 = new JLabel("车标文件");
		jl3 = new JLabel("外边距-上");
		jl4 = new JLabel("外边距-下");
		jl5 = new JLabel("外边距-左");
		jl6 = new JLabel("外边距-右");
		jtf1 = new JTextField(Util.getValueByKey(Data.dataPath, "screen"), 12);
		jtf2 = new JTextField(Util.getValueByKey(Data.dataPath, "logo"), 12);
		jtf3 = new JTextField(Util.getValueByKey(Data.dataPath, "top"), 4);
		jtf4 = new JTextField(Util.getValueByKey(Data.dataPath, "bottom"), 4);
		jtf5 = new JTextField(Util.getValueByKey(Data.dataPath, "left"), 4);
		jtf6 = new JTextField(Util.getValueByKey(Data.dataPath, "right"), 4);
		jtf9 = new JTextField(Util.getValueByKey(Data.dataPath, "result"), 12);
		jb1 = new JButton("选择");
		jb2 = new JButton("选择");
		jb3 = new JButton("选择");

		center.add(jl1);
		center.add(jtf1);
		center.add(jb1);
		center.add(jl2);
		center.add(jtf2);
		center.add(jb2);
		center.add(jl9);
		center.add(jtf9);
		center.add(jb3);
		center.add(jl);
		center.add(jl3);
		center.add(jtf3);
		center.add(jl4);
		center.add(jtf4);
		center.add(jl5);
		center.add(jtf5);
		center.add(jl6);
		center.add(jtf6);
		// ----------------------------buttom
		jb = new JButton("开始导图");
		jb4 = new JButton("打开合成图片");
		bottom.add(jb);
		bottom.add(jb4);
		jb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Util.checkPath(jtf2.getText(), jtf1.getText(),
						jtf9.getText())
						&& checkMargin()) {
					Combining com = new Combining();
					com.setLogoPath(jtf2.getText());
					Util.writeProperties(Data.dataPath, "logo", jtf2.getText());
					com.setMarginBottom(Integer.parseInt(jtf4.getText()));
					Util.writeProperties(Data.dataPath, "bottom",
							jtf4.getText());
					com.setMarginLeft(Integer.parseInt(jtf5.getText()));
					Util.writeProperties(Data.dataPath, "left", jtf5.getText());
					com.setMarginRight(Integer.parseInt(jtf6.getText()));
					Util.writeProperties(Data.dataPath, "right", jtf6.getText());
					com.setMarginTop(Integer.parseInt(jtf3.getText()));
					Util.writeProperties(Data.dataPath, "top", jtf3.getText());
					com.setScreenPic(jtf1.getText());
					Util.writeProperties(Data.dataPath, "screen",
							jtf1.getText());
					String result = jtf9.getText();
					com.setResultpath(result);
					Util.writeProperties(Data.dataPath, "result", result);
					Util.newFilePath(result);
					com.refreshData();
					com.combiningPic();
				} else {
					JOptionPane.showMessageDialog(null, "请正确输入");
				}
			}

			private boolean checkMargin() {
				String r = jtf3.getText();
				String g = jtf4.getText();
				String b = jtf5.getText();
				String s = jtf6.getText();
				if (Util.checkInetger(r) && Util.checkInetger(g)
						&& Util.checkInetger(b) && Util.checkInetger(s)) {
					return true;
				}
				return false;
			}
		});

		// 底图选择
		jb1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setApproveButtonText("确定");
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returnVal = chooser.showOpenDialog(CombPicFrame.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					jtf1.setText(chooser.getSelectedFile().getAbsolutePath());
				}
			}
		});
		// logo图选择
		jb2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setApproveButtonText("确定");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // 设置只选择目录
				int returnVal = chooser.showOpenDialog(CombPicFrame.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					jtf2.setText(chooser.getSelectedFile().getAbsolutePath());
				}
			}
		});

		// 输出
		jb3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setApproveButtonText("确定");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // 设置只选择目录
				int returnVal = chooser.showOpenDialog(CombPicFrame.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					jtf9.setText(chooser.getSelectedFile().getAbsolutePath());
				}
			}
		});

		// 打开result
		jb4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Util.openFile(jtf9.getText());
			}
		});
	}
}
