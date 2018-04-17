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
		// ------------------------------------����
		top = new JPanel();
		add(top, BorderLayout.NORTH);
		center = new JPanel();
		add(center, BorderLayout.CENTER);
		bottom = new JPanel();
		add(bottom, BorderLayout.SOUTH);

		jl1=new JLabel("�ļ���");
		jtf3=new JTextField(12);
		jb2=new JButton("ѡ��");
		top.add(jl1);
		top.add(jtf3);
		top.add(jb2);
		jl = new JLabel("---------------����---------------");
		center.add(jl);
		jb = new JButton("�������");
		jb1 = new JButton("��ʼ����");
		bottom.add(jb);
		bottom.add(jb1);

		jpanel = new JPanel();
		JLabel jlabel = new JLabel("�滻��");
		jtf1 = new JTextField(6);
		jtf2 = new JTextField(6);
		JButton jbutton = new JButton("OK");
		jpanel.add(jtf1);
		jpanel.add(jlabel);
		jpanel.add(jtf2);
		jpanel.add(jbutton);
		jbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JLabel l1=new JLabel(Util.autoCompleWithSpace("����"+value+"��["+jtf1.getText()+"]�滻��["+jtf2.getText()+"]",250));
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
		// ������button
		jb1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path=jtf3.getText();
				if(path!=null&&!path.equals("")&&map.size()>=1){
					Util.renamePic(path, map);
				}else{
					JOptionPane.showMessageDialog(null, "�����ϸ���Ҫ��1 ѡ���ļ��� 2 �������");
				}
			}
		});

		jb2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setApproveButtonText("ȷ��");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // ����ֻѡ��Ŀ¼
				int returnVal = chooser.showOpenDialog(ChangNameFrame.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					jtf3.setText(chooser.getSelectedFile().getAbsolutePath());
				}
			}
		});
	}
}
