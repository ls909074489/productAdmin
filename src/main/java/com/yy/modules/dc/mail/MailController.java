package com.yy.modules.dc.mail;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yy.common.utils.Constants;
import com.yy.common.utils.DESEncryptUtil;
import com.yy.frame.controller.ActionResultModel;
import com.yy.modules.sys.param.ParameterEntity;
import com.yy.modules.sys.param.ParameterService;
import com.yy.modules.sys.param.ParameterUtil;

/**
 * 邮件配置
 * 
 * @ClassName: SmsConfigController
 * @author liusheng
 * @date 2016年1月5日 下午3:05:43
 */
@Controller
@RequestMapping(value = "/dc/mail")
public class MailController {
	
	@Autowired
	private ParameterService paramService;
	
	
	/**
	 * 邮件配置 @Title: page @author @date @param @return 设定文件 @return String 返回类型 @throws
	 */
	@RequestMapping(value = "/config", method = RequestMethod.GET)
	public String main() {
		return "modules/dc/mail/mail_config_main";
	}

	/**
	 * 获取邮件配置信息 @Title: dataSmsConfig @author liusheng @date 2016年2月15日 下午9:10:42 @param @param groupId @param @param
	 * request @param @return 设定文件 @return ActionResultModel<MailBean> 返回类型 @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/data_mailconfig")
	protected ActionResultModel<MailBean> dataSmsConfig(String groupId, ServletRequest request) {
		ActionResultModel<MailBean> arm = new ActionResultModel<MailBean>();
		try {
//			SAXReader reader = new SAXReader();
//			Document document = null;
//			try {
//				document = reader.read(new File(getClass().getResource("/mail/mail.xml").getPath()));
//			} catch (DocumentException e1) {
//				e1.printStackTrace();
//			}
//			Element element = document.getRootElement();
//			List<Element> elements = element.elements(); 
//			List<MailBean> list = new ArrayList<MailBean>();
//			MailBean bean = null;
//			for (Element e : elements) {
//				bean = new MailBean();
//				bean.setUuid(e.elementText("uuid"));
//				bean.setSmtpAddress(e.elementText("smtpAddress"));
//				bean.setPort(e.elementText("port"));
//				bean.setAnonymous(e.elementText("anonymous"));
//				bean.setSendAddress(e.elementText("sendAddress"));
//				bean.setSendName(e.elementText("sendName"));
//				bean.setUserName(e.elementText("userName"));
//				bean.setPassword(e.elementText("password"));
//				bean.setIsSsl(e.elementText("isSsl"));
//				bean.setRemark(e.elementText("remark"));
//				list.add(bean);
//			}
			List<MailBean> list = new ArrayList<MailBean>();
			MailBean bean = new MailBean();
//			bean.setSmtpAddress(ParameterUtil.getParamValue("MAIL_SENDADDRESS", ""));
//			bean.setPort(e.elementText("port"));
//			bean.setIsSsl(ParameterUtil.getParamValue("MAIL_ANONYMOUS", ""));
//			bean.setRemark(ParameterUtil.getParamValue("MAIL_ANONYMOUS", ""));
			
			bean.setAnonymous(ParameterUtil.getParamValue("MAIL_ANONYMOUS", ""));
			bean.setSendAddress(ParameterUtil.getParamValue("MAIL_SENDADDRESS", ""));
			bean.setSendName(ParameterUtil.getParamValue("MAIL_SENDNAME", ""));
			bean.setUserName(ParameterUtil.getParamValue("MAIL_USERNAME", ""));
			bean.setPassword(ParameterUtil.getParamValue("MAIL_PASSWORD", ""));
			list.add(bean);
			arm.setRecords(list);
			arm.setTotal(list.size());
			arm.setTotalPages(1);
			arm.setSuccess(true);
		} catch (ServiceException e) {
			arm.setSuccess(false);
			arm.setMsg(e.getMessage());
		}
		return arm;
	}

	/**
	 * 修改邮件配置 @Title: updateSmsconfig @author liusheng @date 2016年1月14日 下午2:44:01 @param @param request @param @param
	 * model @param @param entity @param @return 设定文件 @return ActionResultModel 返回类型 @throws
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/update_mailconfig")
	@ResponseBody
	public ActionResultModel updateSmsconfig(MailBean entity) {
		ActionResultModel<MailBean> arm = new ActionResultModel<MailBean>();
		try {
			/*SAXReader reader = new SAXReader();
			Document document = reader.read(new File(getClass().getResource("/mail/mail.xml").getPath()));
			Element element = document.getRootElement();
			List<Element> elements = element.elements();
			for (Element e : elements) {
				if (e.elementText("uuid").equals(entity.getUuid())) {
					e.element("smtpAddress").setText(entity.getSmtpAddress());
					e.element("port").setText(entity.getPort());
					e.element("anonymous").setText(entity.getAnonymous());
					e.element("sendAddress").setText(entity.getSendAddress());
					e.element("sendName").setText(entity.getSendName());
					e.element("userName").setText(entity.getUserName());
					e.element("password").setText(entity.getPassword());
					e.element("isSsl").setText(entity.getIsSsl());
					e.element("remark").setText(entity.getRemark());
					break;
				}
			}
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			XMLWriter Writer = new XMLWriter(
					new FileOutputStream(new File(getClass().getResource("/mail/mail.xml").getPath())), format);
			Writer.write(document);
			Writer.close();*/
			
//			bean.setAnonymous(ParameterUtil.getParamValue("MAIL_ANONYMOUS", ""));
//			bean.setSendAddress(ParameterUtil.getParamValue("MAIL_SENDADDRESS", ""));
//			bean.setSendName(ParameterUtil.getParamValue("MAIL_SENDNAME", ""));
//			bean.setUserName(ParameterUtil.getParamValue("MAIL_USERNAME", ""));
//			bean.setPassword(ParameterUtil.getParamValue("MAIL_PASSWORD", ""));
			
			List<ParameterEntity> entities=new ArrayList<ParameterEntity>();
			
			ParameterEntity p1=paramService.getByCode("MAIL_ANONYMOUS");
			p1.setParamtervalue(entity.getAnonymous());
			entities.add(p1);
			ParameterEntity p2=paramService.getByCode("MAIL_SENDADDRESS");
			p2.setParamtervalue(entity.getSendAddress());
			entities.add(p2);
			ParameterEntity p3=paramService.getByCode("MAIL_SENDNAME");
			p3.setParamtervalue(entity.getSendName());
			entities.add(p3);
			ParameterEntity p4=paramService.getByCode("MAIL_USERNAME");
			p4.setParamtervalue(entity.getUserName());
			entities.add(p4);
			
			ParameterEntity p5=paramService.getByCode("MAIL_PASSWORD");
			DESEncryptUtil des=DESEncryptUtil.getInstanceContant();
			String result = des.encryption(entity.getPassword(), Constants.MAIL_PASSWORD_SECRETKEY);
			if(!p5.getParamtervalue().equals(entity.getPassword())){//修改密码
				p5.setParamtervalue(result);
				entities.add(p5);
			}
			
			paramService.save(entities);
			
			arm.setRecords(entity);
			arm.setSuccess(true);
			arm.setMsg("操作成功!");
		} catch (Exception e) {
			arm.setSuccess(false);
			arm.setMsg("操作失败!");
			e.printStackTrace();
		}
		return arm;
	}

}
