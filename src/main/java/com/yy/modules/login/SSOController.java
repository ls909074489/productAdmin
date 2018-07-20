package com.yy.modules.login;

import java.util.Arrays;
import java.util.Date;

import javax.servlet.ServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yy.common.exception.ServiceException;
import com.yy.common.utils.MD5;
import com.yy.frame.security.ShiroUser;
import com.yy.modules.sys.param.ParameterUtil;
import com.yy.modules.sys.user.UserDAO;
import com.yy.modules.sys.user.UserEntity;

/**
 * 模拟登陆
 */
@Controller
@RequestMapping(value = "/sso")
public class SSOController {

	@Autowired
	private UserDAO userDAO;

	/**
	 * 外系统登录用
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String view(Model model, ServletRequest request) {
		String username = request.getParameter("userName");
		String time = request.getParameter("time");
		String token = request.getParameter("token");
		String randomNum = request.getParameter("randomNum");
		try {
			boolean flag = checkSSO(token, randomNum, time, username);
			if (!flag) {
				model.addAttribute("msg", "授权验证不通过");
				return "redirect:/sso/403.jsp";
			}
		} catch (ServiceException e) {
			model.addAttribute("msg", e.getMessage());
			return "redirect:/sso/403.jsp";
		}

		UserEntity user = ShiroUser.getCurrentUserEntity();
		Subject subject = SecurityUtils.getSubject();
		if (user != null && username.equals(user.getLoginname())) {
			return "redirect:/";
		} else {
			subject.logout();
		}

		try {
			user = userDAO.findByLoginname(username);
			if (user == null || user.getPassword() == null) {
				model.addAttribute("msg", "找不到用户");
				return "redirect:/sso/403.jsp";
			}
			UsernamePasswordToken yytoken = new UsernamePasswordToken(username, user.getPassword());
			yytoken.setRememberMe(true);
			subject.login(yytoken);
			return "redirect:/";
		} catch (Exception e) {
			model.addAttribute("msg", e.getMessage());
			return "redirect:/sso/403.jsp";
		}

	}

	/**
	 * 验证请求合法性
	 */
	private boolean checkSSO(String token, String randomNum, String time, String username) throws ServiceException {
		// 判空
		if (StringUtils.isBlank(username)) {
			throw new ServiceException("用户名不可为空");
		}
		// 判空
		if (StringUtils.isBlank(randomNum) || StringUtils.isBlank(token) || StringUtils.isBlank(time)) {
			throw new ServiceException("参数传递不可为空");
		}
		// 判断时间戳
		Long longTime = new Long(time);
		Date date = new Date();
		if (longTime < (date.getTime() - (1000L * 1000L)) || longTime > (date.getTime() + (1000L * 1000L))) {
			throw new ServiceException("登陆超时");
		}

		String key = ParameterUtil.getParamValue("sso_key");
		if (StringUtils.isBlank(key)) {
			throw new ServiceException("获取不到认证的key");
		}

		key = MD5.MD5Encode(key);
		String[] arr = { time, randomNum, key };
		Arrays.sort(arr);// 排序
		StringBuffer temp = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			temp.append(arr[i]);
		}
		boolean flag = token.equals(MD5.MD5Encode(temp.toString()));
		return flag;
	}

}
