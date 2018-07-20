package com.yy.modules.dc.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yy.frame.controller.BaseController;

/**
 * 短信模板controller
 * @ClassName: SmstemplateController
 * @author  
 * @date 2016年26月15日 10:10:56
 */
@Controller
@RequestMapping(value = "/dc/sms_template")
public class SmstemplateController extends BaseController<SmsTemplateEntity> {

	@Autowired
	private SmstemplateService smstemplateService;

	/**
	 * 
	 * @Title: 短信模板
	 * @author 
	 * @date 2016年26月15日 10:10:56
	 * @param @return 设定文件 
	 * @return String 返回类型 
	 * @throws
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String view() {
		return "modules/dc/sms/sms_template_main";
	}


	
}
