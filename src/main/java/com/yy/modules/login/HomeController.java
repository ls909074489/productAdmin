package com.yy.modules.login;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yy.common.utils.Constants;
import com.yy.frame.security.ShiroUser;
import com.yy.frame.tree.TreeNode;
import com.yy.frame.tree.TreeUtils;
import com.yy.modules.sys.func.FuncEntity;
import com.yy.modules.sys.func.FuncService;
import com.yy.modules.sys.org.OrgEntity;
import com.yy.modules.sys.org.OrgService;
import com.yy.modules.sys.param.ParameterUtil;
import com.yy.modules.sys.user.UserEntity;
import com.yy.modules.sys.user.UserService;

/**
 * LoginController负责打开登录页面(GET请求)和登录出错页面(POST请求)，
 * 
 * 真正登录的POST请求由Filter完成,
 * 
 */
@Controller
public class HomeController {

	private static final String USER_FUNCS = "USERFUNCS";

	@Autowired
	FuncService funcService;

	@Autowired
	UserService userService;
	@Autowired
	private OrgService orgService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String view(Model model,HttpServletRequest request) {
		UserEntity user = ShiroUser.getCurrentUserEntity();
		if (user != null) {
			String sysId = "";
			if ("".equals(sysId)) {
				sysId = "2c90e4d74917c191014917c3cf1d0000";
			}

			List<TreeNode> tree = null;

			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession(); 
			tree = (List<TreeNode>) session.getAttribute(USER_FUNCS);
			if (tree == null || tree.size() == 0) {
				List functreeList = userService.getUserFunc(user.getUuid());
				tree = TreeUtils.getTreeNodes(functreeList);
				subject.getSession().setAttribute(USER_FUNCS, tree);
				subject.getSession().setAttribute("userid", user.getUuid());
			}

			FuncEntity funcEntity = funcService.getOne(sysId);
			model.addAttribute("functreeList", tree);
			// model.addAttribute("countModule", getCount(tree));
			model.addAttribute("user", user);
			model.addAttribute("yy_logo_imge", ParameterUtil.getParamValue("yy_logo_imge", ""));
			model.addAttribute("yy_logo_title", ParameterUtil.getParamValue("yy_logo_title", ""));
			model.addAttribute("yy_footer_title", ParameterUtil.getParamValue("yy_footer_title", "2015 © 版权所有."));
			model.addAttribute("systitle", funcEntity.getFunc_name());

			List<OrgEntity> stationLists = orgService.findOwnOrg(user.getUuid());
			model.addAttribute("stationLists", stationLists);
			
			HttpSession httpSession = request.getSession(true);
			OrgEntity station=(OrgEntity) httpSession.getAttribute(Constants.CURRENTSTATION);
			String hasSelStation="0";//是否选择了当前的厂商
			if(station!=null&&!StringUtils.isEmpty(station.getUuid())){
				hasSelStation="1";
				model.addAttribute("currentStation", station);
			}
			model.addAttribute("hasSelStation", hasSelStation);
			
			return "frame/yy_main";
		}
		return "frame/yy_main";
	}

	private String getCount(List<TreeNode> tree) {
		int i = 0;
		for (TreeNode n : tree) {
			FuncEntity fun = (FuncEntity) n.getNodeData();
			if ("module".equals(fun.getFunc_type())) {
				i++;
			}
		}
		return String.valueOf(i);
	}
}
