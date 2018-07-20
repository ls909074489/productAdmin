package com.yy.workflow.user;

import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yy.frame.controller.ActionResultModel;
import com.yy.frame.controller.BaseController;

@Controller
@RequestMapping(value = "/process/usergrouprelation")
public class ProcessUserGroupRelationController  extends BaseController<ProcessUserGroupRelationEntity> {

	
	private ProcessUserGroupRelationService getService() {
		return (ProcessUserGroupRelationService) super.baseService;
	}

	
	/**
	 * 选入流程项用户
	 * @Title: selecItemUser 
	 * @author liusheng
	 * @date 2016年5月10日 下午6:46:37 
	 * @param @param groupId
	 * @param @param userId
	 * @param @return 设定文件 
	 * @return ActionResultModel<ProcessUserGroupRelationEntity> 返回类型 
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/selecUser")
	public ActionResultModel<ProcessUserGroupRelationEntity> selecUser(String groupId, String userId){
		ActionResultModel<ProcessUserGroupRelationEntity> arm = new ActionResultModel<ProcessUserGroupRelationEntity>();
		try {
			arm=getService().selecGroupUser(groupId,userId);
		} catch (ServiceException e) {
			e.printStackTrace();
			arm.setSuccess(false);
			arm.setMsg("操作失败");
		}
		return arm;
	}
	
	
	
	/**
	 * 流程项用户
	 * @Title: delItemUser 
	 * @author liusheng
	 * @date 2016年5月10日 下午6:46:57 
	 * @param @param groupId
	 * @param @param userId
	 * @param @return 设定文件 
	 * @return ActionResultModel<ProcessUserGroupRelationEntity> 返回类型 
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/delUser")
	public ActionResultModel<ProcessUserGroupRelationEntity> delUser(String groupId, String userId){
		ActionResultModel<ProcessUserGroupRelationEntity> arm = new ActionResultModel<ProcessUserGroupRelationEntity>();
		try {
			arm=getService().delGroupUser(groupId,userId);
		} catch (ServiceException e) {
			arm.setSuccess(false);
			arm.setMsg("操作失败");
		}
		return arm;
	}
	
}
