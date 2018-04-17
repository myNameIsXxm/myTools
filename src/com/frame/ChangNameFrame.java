package com.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.action.Util;

public class ChangNameFrame extends JFrame {
	private JLabel jl,jl1;
	private JButton jb, jb1,jb2;
	private JPanel jpanel, top, center, bottom;
	private JTextField jtf1,jtf2,jtf3;
	private Map<String,String> map=new HashMap<String,String>();
	private int value=1;

	private ChangNameFrame() {
	}

	private static ChangNameFrame singleton = null;

	public static ChangNameFrame getSingleton() {
		if (singleton == null) {
			singleton = new ChangNameFrame();
		}
		return singleton;
	}

	public void showFrame() {
		// ------------------------------------布局
		top = new JPanel();
		add(top, BorderLayout.NORTH);
		center = new JPanel();
		add(center, BorderLayout.CENTER);
		bottom = new JPanel();
		add(bottom, BorderLayout.SOUTH);

		jl1=new JLabel("文件夹");
		jtf3=new JTextField(12);
		jb2=new JButton("选择");
		top.add(jl1);
		top.add(jtf3);
		top.add(jb2);
		jl = new JLabel("---------------条件---------------");
		center.add(jl);
		jb = new JButton("添加条件");
		jb1 = new JButton("开始改名");
		bottom.add(jb);
		bottom.add(jb1);

		jpanel = new JPanel();
		JLabel jlabel = new JLabel("替换成");
		jtf1 = new JTextField(6);
		jtf2 = new JTextField(6);
		JButton jbutton = new JButton("OK");
		jpanel.add(jtf1);
		jpanel.add(jlabel);
		jpanel.add(jtf2);
		jpanel.add(jbutton);
		jbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JLabel l1=new JLabel(Util.autoCompleWithSpace("条件"+value+"：["+jtf1.getText()+"]替换成["+jtf2.getText()+"]",250));
				value++;
				center.add(l1);
				center.remove(jpanel);
				jb.setEnabled(true);
				map.put(jtf1.getText(), jtf2.getText());
				Dimension d = ChangNameFrame.getSingleton().getSize();
				ChangNameFrame.getSingleton().setSize((int) d.getWidth(),
						(int) d.getHeight() - 25);
				center.validate();
			}
		});

		jb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jtf1.setText("");
				jtf2.setText("");
				center.add(jpanel);
				Dimension d = ChangNameFrame.getSingleton().getSize();
				ChangNameFrame.getSingleton().setSize((int) d.getWidth(),
						(int) d.getHeight() + 50);
				jb.setEnabled(false);
			}
		});
		// 改名的button
		jb1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path=jtf3.getText();
				if(path!=null&&!path.equals("")&&map.size()>=1){
					Util.renamePic(path, map);
				}else{
					JOptionPane.showMessageDialog(null, "不符合改名要求：1 选择文件夹 2 添加条件");
				}
			}
		});

		jb2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setApproveButtonText("确定");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // 设置只选择目录
				int returnVal = chooser.showOpenDialog(ChangNameFrame.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					jtf3.setText(chooser.getSelectedFile().getAbsolutePath());
				}
			}
		});
	}
}
