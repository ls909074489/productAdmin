package com.yy.frame.aop;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yy.frame.security.ShiroUser;
import com.yy.modules.sys.log.LogEntity;
import com.yy.modules.sys.log.LogService;
import com.yy.modules.sys.user.UserEntity;

public class DbLogInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private LogService logService;

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// String requestRri = request.getRequestURI();// request.getRequestURL().toString()
		// if (!(requestRri.indexOf("login") > 0) && !(requestRri.indexOf("data") > 0) && !(requestRri.indexOf(".") >
		// 0)) {
		// Map<String, String[]> map = request.getParameterMap();
		// StringBuilder builder = new StringBuilder();
		// if (map != null && !map.isEmpty()) {
		// Iterator<String> it = map.keySet().iterator();
		// while (it.hasNext()) {
		// String key = it.next().toString();
		// if (!StringUtils.isEmpty(key) && StringUtils.contains(key, "columns")) {
		// continue;
		// }
		// String value = request.getParameter(key);
		// builder.append(key).append("=").append(value).append("; ");
		// }
		// }
		//
		// LogEntity log = new LogEntity();
		// UserEntity user = ShiroUser.getCurrentUserEntity();
		// if (user != null) {
		// log.setUserid(user.getUuid());
		// log.setUsername(user.getLoginname());
		// } else {
		// log.setUsername("未登录");
		// }
		//
		// log.setIp(getRemoteHost(request));// 设置ip地址
		// log.setUrl(request.getRequestURI());
		//
		// log.setMethod(request.getMethod());
		//
		// // 服务器分页的参数会太长
		// if (builder.length() > 1000) {
		// log.setParams(StringUtils.substring(builder.toString(), 0, 1000));
		// } else {
		// log.setParams(builder.toString());
		// }
		// if (ex != null) {
		// // 设置异常
		// if (builder.length() > 1000) {
		// log.setExdetail(StringUtils.substring(ex.getMessage(), 0, 1000));
		// } else {
		// log.setExdetail(ex.getMessage());
		// }
		// }
		//
		// log.setUseragent(request.getHeader("user-agent"));
		// logService.save(log);
		// }

	}

	/**
	 * 获取ip
	 * 
	 * @param request
	 * @return
	 */
	private String getRemoteHost(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
	}

}
