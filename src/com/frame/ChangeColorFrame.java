package com.frame;

import java.awt.BorderLayout;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.action.PicColor;
import com.action.Util;

@SuppressWarnings("serial")
public class ChangeColorFrame extends JFrame {

	public static TrayIcon trayIcon = null;
	private JLabel jl1, jl2, jl3, jl4;
	private JTextField jtf1, jtf2, jtf3, jtf4;
	private JButton jb, jb1;

	private ChangeColorFrame() {
	}

	private static ChangeColorFrame singleton = null;

	public static ChangeColorFrame getSingleton() {
		if (singleton == null) {
			singleton = new ChangeColorFrame();
		}
		return singleton;
	}

	public void showFrame() {
		// ------------------------------------布局
		JPanel center = new JPanel();
		add(center, BorderLayout.CENTER);
		// ----------------------------center

		jl1 = new JLabel("文件夹路径");
		jl2 = new JLabel("R");
		jl3 = new JLabel("G");
		jl4 = new JLabel("B");
		jtf1 = new JTextField(18);
		jtf2 = new JTextField(4);
		jtf3 = new JTextField(4);
		jtf4 = new JTextField(4);
		jb1 = new JButton("选择");
		center.add(jl1);
		center.add(jtf1);
		center.add(jb1);
		center.add(jl2);
		center.add(jtf2);
		center.add(jl3);
		center.add(jtf3);
		center.add(jl4);
		center.add(jtf4);
		jb = new JButton("ready go!");
		center.add(jb);
		jb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkColor()&&Util.checkPath(jtf1.getText())) {
					PicColor p = new PicColor();
					p.setR(Integer.parseInt(jtf2.getText()));
					p.setG(Integer.parseInt(jtf3.getText()));
					p.setB(Integer.parseInt(jtf4.getText()));
					p.changPathPicColor(jtf1.getText());
				}else{
					JOptionPane.showMessageDialog(null, "请正确输入");
				}
			}

			private boolean checkColor() {
				String r = jtf2.getText();
				String g = jtf3.getText();
				String b = jtf4.getText();
				if (Util.checkRGBInetger(r) && Util.checkRGBInetger(g)
						&& Util.checkRGBInetger(b)) {
					return true;
				}
				return false;
			}
		});
		jb1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setApproveButtonText("确定");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // 设置只选择目录
				int returnVal = chooser.showOpenDialog(ChangeColorFrame.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					jtf1.setText(chooser.getSelectedFile().getAbsolutePath());
				}
			}
		});
	}
}
