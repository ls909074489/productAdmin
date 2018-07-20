package com.yy.modules.session;

import org.apache.shiro.web.session.mgt.ServletContainerSessionManager;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yy.frame.controller.ActionResultModel;

@Controller
@RequestMapping(value = "/sys/session")
public class SessionController {

	/**
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String view() {
		return "frame/user_session";
	}

	@RequestMapping(value = "/getOnlineUsers")
	@ResponseBody
	private ActionResultModel getOnlineUsers() {
		ActionResultModel arm = new ActionResultModel();
		try {
			// SecurityUtils.getSecurityManager().getSession(key);
			// sessionManager.get
			arm.setRecords(null);
			arm.setSuccess(true);
		} catch (ServiceException e) {
			// e.printStackTrace();
			arm.setSuccess(false);
			arm.setMsg(e.getMessage());
		}

		return arm;
	}

}
