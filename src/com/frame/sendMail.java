package com.frame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.frame.mail.MyAuthenticator;

@SuppressWarnings("serial")
public class sendMail extends JFrame {
	public sendMail(String sendto) {
		add(new JLabel("�����ʼ�"), BorderLayout.NORTH);
		JPanel jp = new JPanel();
		add(jp, BorderLayout.CENTER);
		JLabel jl1 = new JLabel("������");
		JLabel jl2 = new JLabel("����");
		JLabel jl3 = new JLabel("�ռ���");
		JLabel jl4 = new JLabel(" ����");
		JLabel jl5 = new JLabel(" ����");
		final JTextField jtf1 = new JTextField(20);
		final JTextField jtf2 = new JTextField(20);
		JTextField jtf3 = new JTextField(sendto,20);
		final JTextField jtf5 = new JTextField(20);
		final JTextArea jta = new JTextArea(4,20);
		JButton jb = new JButton("ȷ������");
		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MyAuthenticator.sendMessage("xixiaomingzaq@163.com",jtf1.getText(),jtf2.getText(),jtf5.getText(),jta.getText());
				//�ռ���,������,����,�ʼ�����,�ʼ�����
			}
		});
		jp.add(jl1);
		jp.add(jtf1);
		jp.add(jl2);
		jp.add(jtf2);
		jp.add(jl3);
		jp.add(jtf3);
		jp.add(jl5);
		jp.add(jtf5);
		jp.add(jl4);
		jp.add(jta);
		jp.add(jb);
		setTitle("�ʼ�����");
		setBounds(400, 400, 300, 270);
		setVisible(true);
		setResizable(false);
	}
}
