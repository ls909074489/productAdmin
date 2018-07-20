package com.yy.common.mail;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

public class ThreadMail implements Runnable {
	private static Logger logger = Logger.getLogger(ThreadMail.class);
 
	String smtp = "";
	String port = "";
	InternetAddress from;
	String to = "";
	String cc = "";
	String content = "";
	String subject = "";
	String user = "";
	String pass = "";
	boolean auth = false;
	boolean debug = false;
	public boolean status = true;
	public String error = "";

	// 收信
	String pop3 = "";
	String pop3Host = "";
	
	

	public static void main(String[] args) throws InterruptedException, MessagingException, IOException {
		String username = "csc@meizu.com";
		String password = "Team@201606";
		String receiver = "909074489@qq.com";
		String name = "发件人昵称";
		String sendContent = "发件人邮件内容";
		String sendSubject = "邮件主题";
//		// 发信
//		ThreadMail tmail = new ThreadMail();
//		String smtp = "smtp.163.com";
//		tmail.setSmtp(smtp);
//		tmail.setUser(username);
//		tmail.setPass(password);
//		tmail.setAuth(true);
//		tmail.setTo(receiver);
//		try {
//			String nick = javax.mail.internet.MimeUtility.encodeText(name);
//			tmail.setFrom(new InternetAddress(nick + "<" + username + ">"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		tmail.setSubject(sendSubject + new Date());
//		String content = sendContent + new Date();
//		tmail.setContent(content);
//		Thread t = new Thread(tmail);
//		t.start();
		
		//发信
		Mail mail = new Mail(username, password, receiver, name, sendContent, sendSubject);
		MailUtil.startSendEmail(mail);
		

		// 收信
		// ApplicationContext ac = new ClassPathXmlApplicationContext(new
		// String[] { "applicationContext.xml" });
		// TmpFromEmailService tmpSer = (TmpFromEmailService)
		// ac.getBean("tmpFromEmailService");
		// List<TmpFromEmail> list = tmpSer.getListEmail();
		// for (int i = 0; i < list.size(); i++) {
		// TmpFromEmail tmp = list.get(i);
		// String pop3 = "imap";
		// String host = "";
		// if(tmp.getEmail().indexOf("tom.com")!=-1) {
		// pop3 = "pop";
		// host = "pop."+ tmp.getEmail().split("@")[1];
		// } else {
		// host = "imap."+ tmp.getEmail().split("@")[1];
		//
		// }
		// tmail.setPop3(pop3);
		// tmail.setPop3Host(host);
		// tmail.setPass(tmp.getPassword());
		// tmail.setUser(tmp.getEmail());
		// tmail.setAuth(true);
		// //读邮件
		// tmail.readEmail();
		// }
		// tmail.setPop3("imap");
		// tmail.setPop3Host("imap.yeah.net");
		// tmail.setPass("guozhiju");
		// tmail.setUser("guozhiju1@yeah.net");
		// tmail.setAuth(true);
		// // 读邮件
		// tmail.readEmail();

	}

	public ThreadMail() {
	}

	/**
	 * 发送Email方法
	 * 
	 * @return boolean
	 */
	public void run() {
		try {
			// System.out.println(Thread.currentThread().getName()+" excute ......");

			// 创建属性对象
			Properties props = new Properties();
			// 设置邮件传输协议为：smtp
			props.put("mail.transpost.protocol", "smtp");
			// 设置邮件服务器地址
			props.put("mail.smtp.host", this.smtp);
			// 设置域名主机
			// props.put("mail.smtp.localhost", this.smtp);
			// 设置邮件服务器端口
			// props.put("mail.smtp.port",this.port);

			// 创建session对象
			Session sendMailSession = null;

			if (this.auth) {
				// 设置邮件验证为真
				props.put("mail.smtp.auth", "true");
				SmtpAuthenticator sa = new SmtpAuthenticator(this.user, this.pass);
				sendMailSession = Session.getInstance(props, sa);

			} else {
				sendMailSession = Session.getInstance(props, null);
			}

			// 设置输出调试信息
			sendMailSession.setDebug(this.debug);

			// 创建信息对象
			final MimeMessage newMessage = new MimeMessage(sendMailSession);

			// 设置发信人地址
			newMessage.setFrom(this.from);

			// 设置收信人地址，可以支持多用户发送
			newMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(this.to));

			// 设置抄送人地址
			if(!StringUtils.isEmpty(this.cc)){
				newMessage.setRecipients(Message.RecipientType.CC,InternetAddress.parse(this.cc));
			}

			// 设置信件正文
			newMessage.setContent(content, "text/html;charset=utf-8");

			// 设置信件主题
			newMessage.setSubject(this.subject, "utf-8");

			// 设置信件发送日期
			newMessage.setSentDate(new Date());

			// 发送邮件
			Transport.send(newMessage);
		} catch (MessagingException e) {
			this.status = false;
			this.error = e.toString();
			e.printStackTrace();
			System.out.println("from=" + this.user + "  to=" + to + " title=" + subject + " send mail Failed...");
			logger.info("excetion:" + e.toString());
		}
	}

	/**
	 * 设置smtp服务器地址
	 * 
	 * @param smtp
	 */
	public void setSmtp(String smtp) {
		this.smtp = smtp;
	}

	/**
	 * 设置端口
	 * 
	 * @param port
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * 设置发信人地址
	 * 
	 * @param from
	 */
	public void setFrom(InternetAddress from) {
		this.from = from;
	}

	/**
	 * 设置收信人地址，可以支持多用户发送(,分隔)
	 * 
	 * @param to
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * 设置抄送人地址
	 * 
	 * @param from
	 */
	public void setCC(String cc) {
		this.cc = cc;
	}

	/**
	 * 设置正文内容text格式
	 * 
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 设置标题
	 * 
	 * @param subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * 验证的用户名
	 * 
	 * @param user
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * 验证的用户密码
	 * 
	 * @param pass
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}

	/**
	 * 是否验证
	 * 
	 * @param auth
	 */
	public void setAuth(boolean auth) {
		this.auth = auth;
	}

	/**
	 * 是否输出调试信息
	 * 
	 * @param debug
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void setError(String error) {
		this.error = error;
	}

	public void setPop3(String pop3) {
		this.pop3 = pop3;
	}

	public void setPop3Host(String pop3Host) {
		this.pop3Host = pop3Host;
	}

	/**
	 * 读邮件
	 * 
	 * @throws MessagingException
	 */
	public void readEmail() throws MessagingException {
		Properties props = new Properties();
		// 存储接收邮件服务器使用的协议，这里以POP3为例
		// props.setProperty("mail.store.protocol", "imap");
		props.setProperty("mail.store.protocol", this.pop3);
		// 设置接收邮件服务器的地址，这里还是以网易163为例
		// props.setProperty("mail.pop3.host", "imap.yeah.net");
		props.setProperty("mail." + this.pop3 + ".host", this.pop3Host);
		// 根据属性新建一个邮件会话.
		Session session = Session.getInstance(props);
		// 从会话对象中获得POP3协议的Store对象
		// Store store = session.getStore("pop3");
		Store store = session.getStore(this.pop3);
		// 如果需要查看接收邮件的详细信息，需要设置Debug标志
		session.setDebug(false);

		// 以某个邮箱帐户的身份连接上POP3 或IMAP4服务器。
		// 连接邮件服务器
		// store.connect("pop3.yeah.net", "guozhiju1@yeah.net", "guozhiju");
		store.connect(this.pop3Host, this.user, this.pass);

		// 获取邮件服务器的收件箱
		Folder folder = store.getFolder("INBOX");
		// 以只读权限打开收件箱
		folder.open(Folder.READ_ONLY);

		// 获取收件箱中的邮件，也可以使用getMessage(int 邮件的编号)来获取具体某一封邮件
		Message message[] = folder.getMessages();
		for (int i = 0, n = message.length; i < n; i++) {
			// 获取邮件具体信息
			try {
				mailReceiver(message[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		// 关闭连接
		folder.close(false);
		store.close();
	}

	/**
	 * 解析邮件
	 * 
	 * @param messages
	 *            邮件对象
	 * @param i
	 * @return
	 * @throws IOException
	 * @throws MessagingException
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	private void mailReceiver(Message msg) throws Exception {
		// 发件人信息
		Address[] froms = msg.getFrom();
		if (froms != null) {
			// System.out.println("发件人信息:" + froms[0]);
			InternetAddress addr = (InternetAddress) froms[0];
			System.out.println("发件人地址:" + addr.getAddress());
			System.out.println("发件人显示名:" + addr.getPersonal());
		}
		System.out.println("邮件主题:" + msg.getSubject());
		// 21cn解决方案：因为21cn自己重构了。我们把他重新new一下
		MimeMessage mMessage = new MimeMessage((MimeMessage) msg);
		// getContent() 是获取包裹内容, Part相当于外包装
		Object o = mMessage.getContent();
		if (o instanceof Multipart) {
			Multipart multipart = (Multipart) o;
			reMultipart(multipart);
		} else if (o instanceof Part) {
			Part part = (Part) o;
			rePart(part);
		} else {
			System.out.println("普通类型" + msg.getContentType());
			System.out.println("普通内容" + msg.getContent());
		}
	}

	/**
	 * @param part
	 *            解析内容
	 * @throws Exception
	 */
	private void rePart(Part part) throws MessagingException, UnsupportedEncodingException, IOException, FileNotFoundException {
		if (part.getDisposition() != null) {

			String strFileNmae = MimeUtility.decodeText(part.getFileName()); // MimeUtility.decodeText解决附件名乱码问题
			System.out.println("发现附件: " + MimeUtility.decodeText(part.getFileName()));
			System.out.println("内容类型: " + MimeUtility.decodeText(part.getContentType()));
			System.out.println("附件内容:" + part.getContent());
			InputStream in = part.getInputStream();// 打开附件的输入流
			// 读取附件字节并存储到文件中
			java.io.FileOutputStream out = new FileOutputStream(strFileNmae);
			int data;
			while ((data = in.read()) != -1) {
				out.write(data);
			}
			in.close();
			out.close();
		} else {
			if (part.getContentType().startsWith("text/plain")) {
				System.out.println("文本内容：" + part.getContent());
			} else {

				System.out.println("HTML内容：" + part.getContent());
				// 处理退信
				handleBackMail(part.getContent());
			}
		}
	}

	private void handleBackMail(Object content2) {
		String contentText = content2 == null ? "1" : (String) content2;
		// 163
		String msg = handleServer(contentText);
		System.out.println("msg--email=" + msg);
		if (msg != null && msg.length() > 0 && msg.indexOf("@") > -1) {
			// 处理email
			String fileName = "D:/tmp/email.txt";
			String content = msg + "\r\n";
			appendMethodA(fileName, content);
		}
	}

	private String handleServer(String content2) {
		String msg = handleServer163(content2);
		if (msg != null && msg.length() > 0 && msg.indexOf("@") > -1) {
			return msg;
		}

		return "";
	}

	private String handleServer163(String content2) {
		content2 = content2.replace("\r\n", "").replace(" ", "");
		if (content2.indexOf("<html>") != -1) {
			return "";
		}
		if (content2.indexOf("收件人似乎在网络世界隐形了") != -1 || content2.indexOf("我们仍然无法找到您要发送的邮件地址") != -1) {
			System.out.println("HTML内容： 地址错误");
			System.out.println(content2.indexOf("收件人"));
			String[] contArr = content2.split("收件人");
			for (int i = 0; i < contArr.length; i++) {
				if (contArr[i].indexOf("@") != -1) {
					String[] marr = contArr[i].split("退信原因");
					return marr[0] + "-----validEmail";
				}
			}

		} else if (content2.indexOf("垃圾邮件让邮箱小易很烦心") != -1) {
			System.out.println("HTML内容： 垃圾邮件让邮箱小易很烦心");
			String[] contArr = content2.split("收件人");
			for (int i = 0; i < contArr.length; i++) {
				if (contArr[i].indexOf("@") != -1) {
					String[] marr = contArr[i].split("退信原因");
					return marr[0] + "-----rubbish";
				}
			}
		}
		return "";
	}

	/**
	 * @param multipart
	 *            // 接卸包裹（含所有邮件内容(包裹+正文+附件)）
	 * @throws Exception
	 */
	private int reMultipart(Multipart multipart) throws Exception {
		// System.out.println("邮件共有" + multipart.getCount() + "部分组成");
		// 依次处理各个部分
		for (int j = 0, n = multipart.getCount(); j < n; j++) {
			// System.out.println("处理第" + j + "部分");
			Part part = multipart.getBodyPart(j);// 解包, 取出 MultiPart的各个部分,
													// 每部分可能是邮件内容,
			// 也可能是另一个小包裹(MultipPart)
			// 判断此包裹内容是不是一个小包裹, 一般这一部分是 正文 Content-Type: multipart/alternative
			if (part.getContent() instanceof Multipart) {
				Multipart p = (Multipart) part.getContent();// 转成小包裹
				// 递归迭代
				reMultipart(p);
			} else {
				rePart(part);
			}
		}
		return 0;
	}

	/**
	 * A方法追加文件：使用RandomAccessFile
	 */
	public static void appendMethodA(String fileName, String content) {
		try {
			// 打开一个随机访问文件流，按读写方式
			RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
			// 文件长度，字节数
			long fileLength = randomFile.length();
			// 将写文件指针移到文件尾。
			randomFile.seek(fileLength);
			randomFile.writeBytes(content);
			randomFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
