package com.yy.workflow.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;

import org.hibernate.service.spi.ServiceException;
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
 * 流程用户组controller
 * @ClassName: RoleController
 * @author liusheng 
 * @date 2015年12月17日 上午11:02:40
 */
@Controller
@RequestMapping(value = "/process/usergroup")
public class ProcessUserGroupController extends BaseController<ProcessUserGroupEntity> {

	private ProcessUserGroupService getService() {
		return (ProcessUserGroupService) super.baseService;
	}
	
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String view() {
		return "workflow/usergroup/process_usergroup_main";
	}

	

	/**
	 * 获取角色组下面的角色
	 * @Title: search 
	 * @author liusheng
	 * @date 2015年12月22日 下午4:22:31 
	 * @param @param request
	 * @param @return 设定文件 
	 * @return ActionResultModel<RoleEntity> 返回类型 
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/dataByGroupId")
	protected ActionResultModel<ProcessUserGroupEntity> dataByGroupId(String groupId,ServletRequest request) {
		ActionResultModel<ProcessUserGroupEntity> arm = new ActionResultModel<ProcessUserGroupEntity>();
		try {
			// 查询条件
			List<ProcessUserGroupEntity> list = new ArrayList<ProcessUserGroupEntity>();
			ActionResultModel<ProcessUserGroupEntity>  result=doQuery(request);
			if(result!=null&&result.getRecords()!=null){
				list=result.getRecords();
			}
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
	 * 跳转到选择用户功能页面
	 * @Title: selectUser 
	 * @author liusheng
	 * @date 2015年12月24日 下午4:30:40 
	 * @param @return 设定文件 
	 * @return String 返回类型 
	 * @throws
	 */
	@RequestMapping(value = "/selectUser")
	public String selectUser(String groupId,Model model) {
		model.addAttribute("groupId", groupId);
		return "workflow/usergroup/process_usergroup_user_select";
	}
	
	
	/**
	 * 根据项获取用户
	 * @Title: findByProcessUserGroupId 
	 * @author liusheng
	 * @date 2016年5月10日 下午5:12:53 
	 * @param @param itemId
	 * @param @return 设定文件 
	 * @return ActionResultModel<UserEntity> 返回类型 
	 * @throws
	 */
	@RequestMapping(value = "/findByProcessUserGroupId")
	@ResponseBody
	private ActionResultModel<UserEntity> findByProcessUserGroupId(@RequestParam(value = "groupId")String groupId){
		ActionResultModel<UserEntity> arm = new ActionResultModel<UserEntity>();
		try {
			List<UserEntity> users = getService().findByProcessUserGroupId(groupId);
			arm.setRecords(users);
			arm.setSuccess(true);
		} catch (ServiceException e) {
			arm.setSuccess(false);
			arm.setMsg(e.getMessage());
		}

		return arm;
	}
	
	/**
	 * 获取可选中的用户列表
	 * @Title: findAllUserByRoleId 
	 * @author liusheng
	 * @date 2016年1月13日 下午3:52:44 
	 * @param @param roleId
	 * @param @param user
	 * @param @return 设定文件 
	 * @return ActionResultModel<UserEntity> 返回类型 
	 * @throws
	 */
	@RequestMapping(value = "/findAllProcessUserGroupId")
	@ResponseBody
	private ActionResultModel<UserEntity> findAllProcessUserGroupId(@RequestParam(value = "groupId")String groupId){
		ActionResultModel<UserEntity> arm = new ActionResultModel<UserEntity>();
		try {
			List<UserEntity> users = getService().findAllProcessUserGroupId(groupId);
			arm.setRecords(users);
			arm.setSuccess(true);
		} catch (ServiceException e) {
			arm.setSuccess(false);
			arm.setMsg(e.getMessage());
		}

		return arm;
	}

	
	/**
	 * 删除角色
	 * @Title: delRole 
	 * @author liusheng
	 * @date 2015年12月29日 上午9:30:41 
	 * @param @param roleId
	 * @param @return 设定文件 
	 * @return ActionResultModel<RoleEntity> 返回类型 
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/delUserGroup")
	public ActionResultModel<ProcessUserGroupEntity> delUserGroup(@RequestParam(value = "pks")String pks) {
		ActionResultModel<ProcessUserGroupEntity> arm = new ActionResultModel<ProcessUserGroupEntity>();
		try {
			getService().delUserGroup(pks);
			arm.setSuccess(true);
		} catch (ServiceException e) {
			arm.setSuccess(false);
			arm.setMsg(e.getMessage());
		}
		return arm;
	}
	
	
}
