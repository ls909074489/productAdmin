package com.yy.common.mail;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.yy.common.utils.Constants;
import com.yy.common.utils.DESEncryptUtil;
import com.yy.modules.sys.param.ParameterUtil;

/**
 * 发送邮件
 * @ClassName: MailSender
 * @author liusheng
 * @date 2016年3月14日 上午9:35:42
 */
public class MailSendUtil {
	private static Logger logger = Logger.getLogger(ThreadMail.class);
	
	
	/**
	 * 发送的邮件的主题
	 * @ClassName: sendSubject
	 * @author liusheng
	 * @date 2016年3月14日 上午11:06:46
	 */
	public static class sendSubject{
		//创建网点管理员
		public static String  CREATE_ADMIN_SUBJECT="网点管理员已开通";
	}
	
	/**
	 * 发送邮件内容
	 * @ClassName: sendContent
	 * @author liusheng
	 * @date 2016年3月14日 上午11:06:58
	 */
	public static class sendContent{
		//创建网点管理员
		public static String  CREATE_ADMIN_CONTENT="尊敬的 {user} 您好：<br>您已成功创建的网点管理员：{admin}，密码：{password}。"+
						"为了保障您的账户安全，请保管好并定期更改登录密码。请用该管理员账号进入系统申请对应人员的账号权限，该账号只作人员申请用途，谢谢。";
	}
	
	
	/**
	 * 发送邮件
	 * @Title: sendMail 
	 * @author liusheng
	 * @date 2016年7月26日 上午11:42:41 
	 * @param @param receiverMail
	 * @param @param sendSubject
	 * @param @param sendContent 设定文件 
	 * @return void 返回类型 
	 * @throws
	 */
	public static void  sendMail(String receiverMail,String sendSubject,String sendContent){
		sendMail(receiverMail, null, sendSubject, sendContent);
	}
	/**
	 * 
	 * @Title: sendMail 
	 * @author LiuSheng
	 * @date 2016年3月14日 上午9:54:12 
	 * @param @param receiverMail
	 * @param @param sendSubject 邮件主题
	 * @param @param sendContent 发件人邮件内容
	 * @param copyTo  抄送
	 * @return void 返回类型 
	 * @throws
	 */
	public static void  sendMail(String receiverMail,String copyTo,String sendSubject,String sendContent){
		try {
			String username = ParameterUtil.getParamValue("MAIL_USERNAME","csc@meizu.com");//邮件发送用户名
			String password = ParameterUtil.getParamValue("MAIL_PASSWORD","6dmMIxjp26RkWOF00sjmXw==");//邮件发送密码
			String name = ParameterUtil.getParamValue("MAIL_SENDNAME", "CSC管理系统");//发件人昵称
			DESEncryptUtil des=DESEncryptUtil.getInstanceContant();
			//发送 
			Mail mail = new Mail(username, des.decryption(password, Constants.MAIL_PASSWORD_SECRETKEY),
					receiverMail, name, sendContent, sendSubject);
			if(!StringUtils.isEmpty(copyTo)){//抄送
				mail.setCc(copyTo);
			}
			MailUtil.startSendEmail(mail);
		} catch (Exception e) {
			logger.info("mail:"+receiverMail+":" +sendContent+" exception:"+ e.toString());
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		/*DESEncryptUtil des=DESEncryptUtil.getInstanceContant();
		String password2 = "6dmMIxjp26RkWOF00sjmXw==";//邮件发送密码
		System.out.println(password2);
		
		System.out.println(Constants.MAIL_PASSWORD_SECRETKEY);
		try {
			System.out.println(des.decryption(password2, Constants.MAIL_PASSWORD_SECRETKEY));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String username = "csc@meizu.com";
		String password = "Team@201606";
		String receiver = "liusheng@meizu.com";
		String name = "发件人昵称";
		String sendContent = "发件人邮件内容222222222";
		String sendSubject = "邮件主题1111111111";
		
		//发信
		Mail mail = new Mail(username, password, receiver, name, sendContent, sendSubject);
		MailUtil.startSendEmail(mail);
		System.out.println("send end=====================");*/
		
		
		//sendMail("909074489@qq.com,liusheng@meizu.com", "sendSubject1111", "sendContent", "1009341816@qq.com,909074489@qq.com");
		
		String tt="423423@qq.com，wgweg@gege.com；523523@geg.com;wegwe@com";
		System.out.println(tt.replace(";", ",").replace("；", ",").replace("，", ","));
		
		System.out.println("send end=====================");
	}
}
