package com.frame;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.action.Util;

@SuppressWarnings("serial")
public class about extends JFrame {

	private about() {
	}

	private static about singleton;

	public static about getSingleton() {
		if (singleton == null) {
			singleton = new about();
		}
		return singleton;
	}

	public JLabel[] jl = new JLabel[5];
	public JButton jb;
	private int width = 200;

	public void showFrame() {
		Font f = new Font("Segoe Print", Font.PLAIN, 15);
		setBounds(400, 400, width, 190);
		setResizable(false);
		setVisible(true);
		JPanel jp = new JPanel();
		jp.setLayout(null);
		add(jp, BorderLayout.CENTER);
		jl[0] = new JLabel("XixiBox");
		jl[1] = new JLabel("Version:1.0.3");
		jl[2] = new JLabel("BY Xi Xiaoming");
		jl[3] = new JLabel("2013/06/05");
		jl[4] = new JLabel(
				"<HTML><font color=blue><U>xixiaomingzaq@163.com</U></font></HTML>");
		jl[4].setCursor(new Cursor(12));
		jl[4].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				new sendMail("xixiaomingzaq@163.com");
			}
		});
		for (int i = 0; i < jl.length; i++) {
			jl[i].setFont(f);
			jp.add(jl[i]);
			if (i == 4)
				jl[i].setBounds(
						(width - Util.getStrigWidthAndHeight(
								"xixiaomingzaq@163.com", f).get("width")) / 2,
						5 + 25 * i, 200, 25);
			else
				jl[i].setBounds(
						(width - Util.getStrigWidthAndHeight(jl[i].getText(),
								f).get("width")) / 2, 5 + 25 * i, 200, 25);

		}
		jb = new JButton("OK");
		jb.setFont(f);
		jp.add(jb);
		jb.setBounds((width-70)/2, jl.length * 25 + 5, 70, 25);
		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				about.getSingleton().setVisible(false);
			}
		});
	}

	public static void main(String[] args) {
		about.getSingleton().showFrame();
		about.getSingleton().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
