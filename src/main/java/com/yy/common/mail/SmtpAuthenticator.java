package com.yy.common.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SmtpAuthenticator extends Authenticator {
	private String username;
	private String password;

	/**
	 * 用户名和密码
	 * 
	 * @param username
	 * @param password
	 */
	public SmtpAuthenticator(String username, String password) {
		this.username = username;
		this.password = password;

	}

	/**
	 * 覆写原有方法
	 */
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password);
	}

}