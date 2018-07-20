package com.yy.common.mail;

import javax.mail.internet.InternetAddress;

import org.apache.log4j.Logger;

/**
 * 发送邮件,
 */
public class MailUtil {
	private static Logger logger = Logger.getLogger(MailUtil.class);

	
	/**
	 * 发送邮件
	 */
	public static void startSendEmail(Mail mail) {
		try {

			ThreadMail tmail = new ThreadMail();
			tmail.setSmtp(mail.getHost());
			tmail.setUser(mail.getUsername());
			tmail.setPass(mail.getPassword());
			tmail.setSubject(mail.getSubject());
			tmail.setContent(mail.getContent());
			tmail.setAuth(true);
			tmail.setTo(mail.getReceiver());//收件人
			tmail.setCC(mail.getCc());//抄送人
			
			String nick = "";
			try{
				nick = javax.mail.internet.MimeUtility.encodeText(mail.getName());//昵称
			} catch(Exception e) {
			}
			tmail.setFrom(new InternetAddress(nick + " <" + mail.getUsername() + ">"));//来源哪个邮箱
			Thread t = new Thread(tmail);
			t.start();
		} catch (Exception e) {
			logger.error("发送邮件错误：", e);
		}
	}
}