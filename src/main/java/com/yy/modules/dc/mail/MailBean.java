package com.yy.modules.dc.mail;

/**
 * 短信配置
 * @ClassName: SmsBean
 * @author liusheng 
 * @date 2016年1月14日 下午1:01:41
 */
public class MailBean {
	
	private String uuid;//
	private String smtpAddress;//SMTP服务器地址
	private String port;//端口
	private String anonymous;//匿名发送
	private String sendAddress;//发件人地址
	private String sendName;//发件人显示名称
	private String userName;//用户名
	private String password;//密码
	private String isSsl;//是否启用SSL加密
	private String remark;//备注
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getSmtpAddress() {
		return smtpAddress;
	}
	public void setSmtpAddress(String smtpAddress) {
		this.smtpAddress = smtpAddress;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getAnonymous() {
		return anonymous;
	}
	public void setAnonymous(String anonymous) {
		this.anonymous = anonymous;
	}
	public String getSendAddress() {
		return sendAddress;
	}
	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
	}
	public String getSendName() {
		return sendName;
	}
	public void setSendName(String sendName) {
		this.sendName = sendName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIsSsl() {
		return isSsl;
	}
	public void setIsSsl(String isSsl) {
		this.isSsl = isSsl;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
