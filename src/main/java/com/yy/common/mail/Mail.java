package com.yy.common.mail;

import java.io.Serializable;

public class Mail implements Serializable {
	private static final long serialVersionUID = -7421859877223415544L;

	public static final String ENCODEING = "text/html;charset=utf-8";// "UTF-8";

	private String host; // 服务器地址

	private String sender; // 发件人的邮箱

	private String receiver; // 收件人的邮箱

	private String name; // 发件人昵称

	private String username; // 账号

	private String password; // 密码

	private String subject; // 邮件主题

	private String message; // 信息(支持HTML)

	private int num; // 一次发送多少封邮件

	private String content; // html邮件内容

	private String text; // 普通邮件文本内容

	private String cc; // 抄送

	private String bcc; // 暗抄
	private String emailBathStr; // emailStr : 如， email1,emal3....

	// /**
	// * 邮件中的图片，为空时无图片。map中的key为图片ID，value为图片地址
	// */
	private String pictures;
	private String picturesName;
	// /**
	// * 邮件中的附件，为空时无附件。map中的key为附件ID，value为附件地址
	// */
	private String files;
	private String filesName;

	public String getPicturesName() {
		return picturesName;
	}

	public void setPicturesName(String picturesName) {
		this.picturesName = picturesName;
	}

	public String getFilesName() {
		return filesName;
	}

	public void setFilesName(String filesName) {
		this.filesName = filesName;
	}

	public String getPictures() {
		return pictures;
	}

	public void setPictures(String pictures) {
		this.pictures = pictures;
	}

	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public String getEmailBathStr() {
		return emailBathStr;
	}

	public void setEmailBathStr(String emailBathStr) {
		this.emailBathStr = emailBathStr;
	}

	@SuppressWarnings("unused")
	private Mail(){}
	
	/**
	 * 默认host是用户邮件名smtp+后缀组成，如果不一致请自行设置
	 * @param receiver	接收邮箱
	 * @param username	发件邮箱
	 * @param password	发件密码
	 * @param name	昵称
	 * @param content	发件内容
	 * @param subject	主题
	 */
	public Mail(String username, String password, String receiver, String name, String content, String subject) {
		super();
		this.username = username;
		this.password = password;
		this.receiver = receiver;
		this.name = name;
		this.content = content;
		this.host = "smtp." + username.split("@")[1];
		this.subject = subject;
	}
	
}
