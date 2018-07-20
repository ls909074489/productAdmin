package com.yy.modules.sys.rolefuncaction;

import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yy.common.bean.JsonBaseBean;
import com.yy.common.bean.RoleFuncActionBean;
import com.yy.frame.controller.ActionResultModel;
import com.yy.frame.controller.BaseController;
import com.yy.frame.security.ShiroUser;
import com.yy.modules.sys.user.UserEntity;

@Controller
@RequestMapping(value = "/sys/rolefuncaction")
public class RoleFuncActionController extends BaseController<RoleFuncActionEntity> {

	private RoleFuncActionService getService() {
		return (RoleFuncActionService) super.baseService;
	}
	@RequestMapping(value = "/selectNoPermisionAction")
	public String viewChangePwd(String oldpassword, String newpassword) {
		return "modules/sys/role/role_funcaction_select";
	}
//	@SuppressWarnings("rawtypes")
//	@RequestMapping(value = "/listNoPermisionAction")
//	@ResponseBody
//	public ActionResultModel listNoPermisionAction(@RequestParam String roleId,
//			@RequestParam String funcId) {
//		ActionResultModel arm = new ActionResultModel();
//		try {
//			UserEntity loginUser = ShiroUser.getCurrentUserEntity();
//			String userid = loginUser.getUuid();
//			List<FuncActionEntity> actions = getService().listNoPermisionAction(roleId,funcId);
//			arm.setRecords(actions);
//			arm.setSuccess(true);
//		} catch (ServiceException e) {
//			arm.setSuccess(false);
//			arm.setMsg(e.getMessage());
//		}
//
//		return arm;
//	}
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/addFuncActions")
	@ResponseBody
	public ActionResultModel addFuncActions(@RequestParam String roleId,
			@RequestParam String funcId,@RequestParam String[] funcActionIds) {
		ActionResultModel arm = new ActionResultModel();
		try {
			UserEntity loginUser = ShiroUser.getCurrentUserEntity();
			String userid = loginUser.getUuid();
			getService().addFuncActions(roleId,funcId,funcActionIds);
			arm.setSuccess(true);
		} catch (ServiceException e) {
			arm.setSuccess(false);
			arm.setMsg(e.getMessage());
		}

		return arm;
	}
	
	
	/**
	 * 获取角色下的菜单按钮信息
	 * @Title: getRoleFuncActions 
	 * @author liusheng
	 * @date 2015年12月25日 下午5:42:51 
	 * @param @param roleId
	 * @param @param funcId
	 * @param @return 设定文件 
	 * @return List<RoleFuncActionBean> 返回类型 
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/getRoleFuncActions")
	public List<RoleFuncActionBean> getRoleFuncActions(@RequestParam String roleId,@RequestParam String funcId) {
		return getService().getRoleFuncActions(roleId, funcId);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/saveFuncActions")
	public JsonBaseBean saveFuncActions(@RequestParam String roleId,
			@RequestParam String funcId,String[] funcActionIds) {
		JsonBaseBean json=new JsonBaseBean();
		try {
			getService().addFuncActions(roleId,funcId,funcActionIds);
			json.setFlag(1);
			json.setMsg("操作成功");
		} catch (ServiceException e) {
			json.setFlag(0);
			json.setMsg("操作失败");
			e.printStackTrace();
		}
		return json;
	}
	
}
