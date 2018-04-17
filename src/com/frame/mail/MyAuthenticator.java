package com.frame.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MyAuthenticator extends Authenticator {
	String userName = null;
	String password = null;

	public MyAuthenticator() {
	}

	public MyAuthenticator(String username, String password) {
		this.userName = username;
		this.password = password;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password);
	}

	public static void sendMessage(String s1, String s2, String s3, String s4,
			String s5) {
		System.out.println(s1);
		System.out.println(s2);
		System.out.println(s3);
		System.out.println(s4);
		System.out.println(s5);
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.163.com");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName(s2);
		mailInfo.setPassword(s3);
		mailInfo.setFromAddress(s2);
		mailInfo.setToAddress(s1);
		mailInfo.setSubject(s4);
		mailInfo.setContent(s5);
		// mailInfo.setAttachFileNames("D:/file.xls");
		// 纯文本邮件
		// SimpleMailSender sms = new SimpleMailSender();
		// sms.sendTextMail(mailInfo);
		// HTML邮件
		SimpleMailSender.sendHtmlMail(mailInfo);
	}

}
