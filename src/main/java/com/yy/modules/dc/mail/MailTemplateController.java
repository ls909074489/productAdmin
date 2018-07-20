package com.yy.modules.dc.mail;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yy.frame.controller.ActionResultModel;
import com.yy.frame.controller.BaseController;
import com.yy.modules.sys.user.UserEntity;

/**
 * 邮件模板controller
 * @ClassName: MailTemplateController
 * @author  
 * @date 2016年24月16日 09:09:18
 */
@Controller
@RequestMapping(value = "/dc/mail_template")
public class MailTemplateController extends BaseController<MailTemplateEntity> {

	@Autowired
	private MailSenderService mailSenderService;
	

	/**
	 * 
	 * @Title: 邮件模板
	 * @author 
	 * @date 2016年24月16日 09:09:18
	 * @param @return 设定文件 
	 * @return String 返回类型 
	 * @throws
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String view() {
		return "modules/dc/mail/mail_template_main";
	}

	
	/**
	 * 选择岗位
	 * @Title: template 
	 * @author liusheng
	 * @date 2016年1月12日 下午4:48:33 
	 * @param @return 设定文件 
	 * @return String 返回类型 
	 * @throws
	 */
	@RequestMapping(value="/selectStation",method = RequestMethod.GET)
	public String selectStation() {
		return "modules/dc/mail/mail_template_selectstation";
	}
	
	
	@RequestMapping(value = "/findStation")
	@ResponseBody
	private ActionResultModel<UserEntity> findStation(@RequestParam(value = "roleId")String roleId){
		ActionResultModel<UserEntity> arm = new ActionResultModel<UserEntity>();
		try {
			List<UserEntity> users =new ArrayList<UserEntity>();
			UserEntity u1=new UserEntity();
			u1.setUuid("1");
			u1.setUsername("销售人员");
			UserEntity u2=new UserEntity();
			u2.setUuid("2");
			u2.setUsername("客服人员");
			users.add(u1);
			users.add(u2);
			arm.setRecords(users);
			arm.setSuccess(true);
		} catch (Exception e) {
			arm.setSuccess(false);
			arm.setMsg(e.getMessage());
		}

		return arm;
	}
	
	/**
	 * 跳转到收件人维护页面
	 * @Title: view 
	 * @author liusheng
	 * @date 2016年9月1日 上午10:45:49 
	 * @param @return 设定文件 
	 * @return String 返回类型 
	 * @throws
	 */
	@RequestMapping("/toMailSender")
	public String toMailSender(HttpServletRequest request,Model model) {
		model.addAttribute("billType", request.getParameter("billType"));
		return "modules/dc/mail/mail_mailsender_main";
	}
	
	
	/**
	 * 
	 * @Title: dataMailSender 
	 * @author liusheng
	 * @date 2016年9月1日 上午11:30:18 
	 * @param @param billType
	 * @param @return 设定文件 
	 * @return ActionResultModel<MailSenderEntity> 返回类型 
	 * @throws
	 */
	@RequestMapping(value = "/dataMailSender")
	@ResponseBody
	private ActionResultModel<MailSenderEntity> dataMailSender(@RequestParam(value = "billType")String billType){
		ActionResultModel<MailSenderEntity> arm = new ActionResultModel<MailSenderEntity>();
		try {
			List<MailSenderEntity> users =mailSenderService.findByType(billType);
			arm.setRecords(users);
			arm.setSuccess(true);
		} catch (Exception e) {
			arm.setSuccess(false);
			arm.setMsg(e.getMessage());
		}

		return arm;
	}
	
	/**
	 * 保存
	 * @Title: saveMailSender 
	 * @author liusheng
	 * @date 2016年9月1日 上午11:32:49 
	 * @param @param name
	 * @param @param email
	 * @param @param billType
	 * @param @return 设定文件 
	 * @return ActionResultModel<MailSenderEntity> 返回类型 
	 * @throws
	 */
	@RequestMapping(value = "/saveMailSender")
	@ResponseBody
	private ActionResultModel<MailSenderEntity> saveMailSender(@RequestParam(value = "name")String name,
			@RequestParam(value = "email")String email,@RequestParam(value = "billType")String billType){
		return mailSenderService.saveMailSender(name,email,billType);
	}
	
	
	/**
	 * 删除
	 * @Title: delMailSender 
	 * @author liusheng
	 * @date 2016年9月1日 上午11:33:34 
	 * @param @param uuid
	 * @param @return 设定文件 
	 * @return ActionResultModel<MailSenderEntity> 返回类型 
	 * @throws
	 */
	@RequestMapping(value = "/delMailSender")
	@ResponseBody
	private ActionResultModel<MailSenderEntity> delMailSender(@RequestParam(value = "uuid")String uuid){
		return mailSenderService.delMailSender(uuid);
	}
	
	
	/**
	 * 保存默认的
	 * @Title: saveDefaultSender 
	 * @author liusheng
	 * @date 2016年9月1日 下午4:12:08 
	 * @param @param pks
	 * @param @return 设定文件 
	 * @return ActionResultModel<MailSenderEntity> 返回类型 
	 * @throws
	 */
	@RequestMapping(value = "/saveDefaultSender")
	@ResponseBody
	private ActionResultModel<MailSenderEntity> saveDefaultSender(@RequestParam(value = "billType")String billType,@RequestParam(value = "pks")String pks){
		return mailSenderService.saveDefaultSender(pks,billType);
	}
	
}
