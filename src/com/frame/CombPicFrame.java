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
		// ------------------------------------����
		JPanel top = new JPanel();
		add(top, BorderLayout.NORTH);
		final JPanel center = new JPanel();
		add(center, BorderLayout.CENTER);
		JPanel bottom = new JPanel();
		add(bottom, BorderLayout.SOUTH);
		// -------------------------------top
		title = new JLabel(
				"------------------------ �����ļ� ------------------------");
		top.add(title);
		// ----------------------------center

		jl = new JLabel(
				"------------------------ �������� ------------------------");
		jl1 = new JLabel("��ͼ·��");
		jl9 = new JLabel("����ļ�");
		jl2 = new JLabel("�����ļ�");
		jl3 = new JLabel("��߾�-��");
		jl4 = new JLabel("��߾�-��");
		jl5 = new JLabel("��߾�-��");
		jl6 = new JLabel("��߾�-��");
		jtf1 = new JTextField(Util.getValueByKey(Data.dataPath, "screen"), 12);
		jtf2 = new JTextField(Util.getValueByKey(Data.dataPath, "logo"), 12);
		jtf3 = new JTextField(Util.getValueByKey(Data.dataPath, "top"), 4);
		jtf4 = new JTextField(Util.getValueByKey(Data.dataPath, "bottom"), 4);
		jtf5 = new JTextField(Util.getValueByKey(Data.dataPath, "left"), 4);
		jtf6 = new JTextField(Util.getValueByKey(Data.dataPath, "right"), 4);
		jtf9 = new JTextField(Util.getValueByKey(Data.dataPath, "result"), 12);
		jb1 = new JButton("ѡ��");
		jb2 = new JButton("ѡ��");
		jb3 = new JButton("ѡ��");

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
		jb = new JButton("��ʼ��ͼ");
		jb4 = new JButton("�򿪺ϳ�ͼƬ");
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
					JOptionPane.showMessageDialog(null, "����ȷ����");
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

		// ��ͼѡ��
		jb1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setApproveButtonText("ȷ��");
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returnVal = chooser.showOpenDialog(CombPicFrame.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					jtf1.setText(chooser.getSelectedFile().getAbsolutePath());
				}
			}
		});
		// logoͼѡ��
		jb2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setApproveButtonText("ȷ��");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // ����ֻѡ��Ŀ¼
				int returnVal = chooser.showOpenDialog(CombPicFrame.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					jtf2.setText(chooser.getSelectedFile().getAbsolutePath());
				}
			}
		});

		// ���
		jb3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setApproveButtonText("ȷ��");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // ����ֻѡ��Ŀ¼
				int returnVal = chooser.showOpenDialog(CombPicFrame.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					jtf9.setText(chooser.getSelectedFile().getAbsolutePath());
				}
			}
		});

		// ��result
		jb4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Util.openFile(jtf9.getText());
			}
		});
	}
}
