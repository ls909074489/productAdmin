package com.yy.common.mail;

import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yy.modules.dc.mail.MailTemplateEntity;
import com.yy.modules.dc.mail.MailTemplateService;

/**
 * 发送邮件service
 * @ClassName: MailSendService
 * @author liusheng
 * @date 2016年5月4日 下午1:56:09
 */
@Component
public class MailSendService {
	
	@Autowired
	private MailTemplateService mailTemplateService;//邮件模板service
	
	/**
	 * 根据邮件模板编码发送邮件
	 * @Title: sendMailByTemplateCode 
	 * @author liusheng
	 * @date 2016年5月4日 下午2:02:51 
	 * @param @param templateCode 设定文件 
	 * @return void 返回类型 
	 * @throws
	 */
	public void sendMailByTemplateCode(String templateCode){
		MailTemplateEntity template=mailTemplateService.findByCode(templateCode);
		if(template!=null&&!StringUtils.isEmpty(template.getReceiver())){
			template.settReceiver(template.getReceiver());
			template.settContent(template.getContent());
			sendTemplateMail(template);
		}
	}
	
	
	
	/**
	 * List<String[]> replaceList=new ArrayList<String[]>();
	 * replaceList.add(new String[]{"{1}","张三"});
	 * replaceList.add(new String[]{"{2}","下午好"});
	 * mailSendService.sendMailByTemplateCode("xxx",replaceList);
	 * 根据邮件模板编码发送邮件，并替换发件内容的替代占位字符串
	 * @Title: sendMailByTemplateCode 
	 * @author liusheng
	 * @date 2016年5月4日 下午2:03:45 
	 * @param @param templateCode
	 * @param @param replaceStr 设定文件 
	 * @return void 返回类型 
	 * @throws
	 */
	public void sendMailByTemplateCode(String templateCode,List<String[]> replaceList){
		MailTemplateEntity template=mailTemplateService.findByCode(templateCode);
		if(template!=null&&!StringUtils.isEmpty(template.getReceiver())){
			String content=template.getContent();
			if(replaceList!=null&&replaceList.size()>0&&!StringUtils.isEmpty(content)){
				for(String[] arr:replaceList){
					content=content.replace(arr[0], arr[1]);//我觉得应该是replaceAll
				}
				template.settContent(content);
			}
			sendTemplateMail(template);
		}
	}
	
	/**
	 * 自动替换模板中的【0】、【1】。。。替换符
	 * @param templateCode 代码
	 * @param arguments 参数
	 */
	public void sendMailByTemplateCodeAndMessage(String templateCode, Object ... arguments) {
		MailTemplateEntity template = mailTemplateService.findByCode(templateCode);
		if (template != null && StringUtils.isNotEmpty(template.getReceiver())) {
			String content = template.getContent();
			if (arguments != null && arguments.length > 0) {
				content = MessageFormat.format(content, arguments);
				template.settContent(content);
			}
			sendTemplateMail(template);
		}
	}
	
	
	
	/**
	 * 发送邮件模板并制定内容
	 * @Title: sendMailByTemplateCode 
	 * @author liusheng
	 * @date 2016年5月5日 上午9:54:23 
	 * @param @param templateCode  模板编码
	 * @param @param receiver  收件人多个分号隔开
	 * @param @param replaceList 替换字符
	 * @return void 返回类型 
	 * @throws
	 */
	public void sendMailByTemplateCode(String templateCode,String receiver,List<String[]> replaceList){
		MailTemplateEntity template=mailTemplateService.findByCode(templateCode);
		if(template!=null&&!StringUtils.isEmpty(template.getReceiver())){
			String content=template.getContent();
			if(replaceList!=null&&replaceList.size()>0&&!StringUtils.isEmpty(content)){
				for(String[] arr:replaceList){
					content=content.replace(arr[0], arr[1]);
				}
				template.settContent(content);
			}
			if(!StringUtils.isEmpty(receiver)){
				template.settReceiver(receiver);
			}
			sendTemplateMail(template);
		}
	}
	
	/**
	 * 发送邮件
	 * @Title: sendMail 
	 * @author liusheng
	 * @date 2016年7月26日 上午9:43:57 
	 * @param @param receiver 收件人
	 * @param @param copyTo 抄送
	 * @param @param content 发送内容 
	 * @return void 返回类型 
	 * @throws
	 */
	public void sendMail(String receiverTo, String copyTo,String title,String content){
		String receiver="";
		receiverTo=receiverTo.replace("；", ",").replace(";", ",").replace("，", ",");
		for(String r:receiverTo.split(",")){
			receiver=r.trim()+",";
		}
		if(receiver.indexOf(",")>0){
			receiver=receiver.substring(0, receiver.lastIndexOf(","));
		}
		
		String cc=null;
		if(!StringUtils.isEmpty(copyTo)){
			copyTo=copyTo.replace("；", ",").replace(";", ",").replace("，", ",");
			for(String c:copyTo.split(",")){
				cc=c.trim()+",";
			}
			if(cc.indexOf(",")>0){
				cc=cc.substring(0, cc.lastIndexOf(","));
			}
		}
		MailSendUtil.sendMail(receiver,cc, title, content);
	}
	
	/**
	 * 发送邮件
	 * @param receiverTo 收件人
	 * @param copyTo 抄送
	 * @param title 邮件主题
	 * @param templateCode 模板编码
	 * @param replaceList 
	 */
	public void sendMail(String receiverTo, String copyTo,String title,String templateCode,List<String[]> replaceList){
		
		MailTemplateEntity template=mailTemplateService.findByCode(templateCode);
		String content = template.getContent();
		if( template!=null && !StringUtils.isEmpty(receiverTo) && replaceList.size()>0 ){
			for(String[] arr:replaceList){
				content=content.replace(arr[0], arr[1]);
			}
			sendMail(receiverTo,copyTo,title,content);
		}
		
	}
	
	/**
	 * 发送邮件
	 * @param receiverTo 收件人
	 * @param copyTo 抄送
	 * @param title 邮件主题
	 * @param templateCode 模板编码
	 * @param arguments 参数
	 */
	public void sendMail(String receiverTo, String copyTo,String title,String templateCode,Object... arguments ){
		
		MailTemplateEntity template=mailTemplateService.findByCode(templateCode);
		String content = template.getContent();
		if( template!=null && !StringUtils.isEmpty(receiverTo) && arguments != null && arguments.length > 0){
			
			content = MessageFormat.format(content, arguments);
			sendMail(receiverTo,copyTo,title,content);
			
		}
		
	}
	
	
	
	/**
	 * 发送邮件模板
	 * @Title: sendTemplateMail 
	 * @author liusheng
	 * @date 2016年5月4日 下午2:08:28 
	 * @param @param template 设定文件 
	 * @return void 返回类型 
	 * @throws
	 */
	private void sendTemplateMail(MailTemplateEntity template){
		//替换中文的分号
//		template.settReceiver(template.getReceiver().replace("；", ";"));
//		for(String receiver:template.gettReceiver().split(";")){
//			MailSendUtil.sendMail(receiver, template.getTitle(), template.gettContent(),template.getCopyTo());
//		}
		String receiver="";
		template.settReceiver(template.getReceiver().replace("；", ",").replace(";", ",").replace("，", ","));
		for(String r:template.getReceiver().split(",")){
			receiver=r.trim()+",";
		}
		if(receiver.indexOf(",")>0){
			receiver=receiver.substring(0, receiver.lastIndexOf(","));
		}
		
		String copyTo=null;
		if(!StringUtils.isEmpty(template.getCopyTo())){
			template.setCopyTo(template.getCopyTo().replace("；", ",").replace(";", ",").replace("，", ","));
			
			for(String c:template.getCopyTo().split(",")){
				copyTo=c.trim()+",";
			}
			if(copyTo.indexOf(",")>0){
				copyTo=copyTo.substring(0, copyTo.lastIndexOf(","));
			}
		}
		MailSendUtil.sendMail(receiver,copyTo, template.getTitle(), template.gettContent());
	}
}
