package com.yy.workflow.item;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;

import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yy.frame.controller.ActionResultModel;
import com.yy.frame.controller.BaseController;
import com.yy.workflow.user.ProcessUserGroupEntity;

/**
 * 角色管理controller
 * @ClassName: RoleController
 * @author liusheng 
 * @date 2015年12月17日 上午11:02:40
 */
@Controller
@RequestMapping(value = "/process/item")
public class ProcessItemController extends BaseController<ProcessItemEntity> {

	private ProcessItemService getService() {
		return (ProcessItemService) super.baseService;
	}
	
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String view() {
		return "workflow/item/process_item_main";
	}

	

	@Override
	public ActionResultModel<ProcessItemEntity> update(ServletRequest request, Model model, ProcessItemEntity entity) {
		ProcessUserGroupEntity usergroup=null;
		if(entity.getUsergroup()!=null&&!StringUtils.isEmpty(entity.getUsergroup().getUuid())){
			usergroup=new ProcessUserGroupEntity();
			usergroup.setUuid(entity.getUsergroup().getUuid());
		}
		entity.setUsergroup(usergroup);
		return super.update(request, model, entity);
	}

	


	@Override
	public ActionResultModel<ProcessItemEntity> add(ServletRequest request, Model model, ProcessItemEntity entity) {
		ProcessUserGroupEntity usergroup=null;
		if(entity.getUsergroup()!=null&&!StringUtils.isEmpty(entity.getUsergroup().getUuid())){
			usergroup=new ProcessUserGroupEntity();
			usergroup.setUuid(entity.getUsergroup().getUuid());
		}
		entity.setUsergroup(usergroup);
		return super.add(request, model, entity);
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
	protected ActionResultModel<ProcessItemEntity> dataByGroupId(String groupId,ServletRequest request) {
		ActionResultModel<ProcessItemEntity> arm = new ActionResultModel<ProcessItemEntity>();
		try {
			// 查询条件
			List<ProcessItemEntity> list = new ArrayList<ProcessItemEntity>();
			ActionResultModel<ProcessItemEntity>  result=doQuery(request);
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
	
	

//	/**
//	 * 跳转到选择用户功能页面
//	 * @Title: selectUser 
//	 * @author liusheng
//	 * @date 2015年12月24日 下午4:30:40 
//	 * @param @return 设定文件 
//	 * @return String 返回类型 
//	 * @throws
//	 */
//	@RequestMapping(value = "/selectUser")
//	public String selectUser(String itemId,Model model) {
//		model.addAttribute("itemId", itemId);
//		return "workflow/item/process_item_user_select";
//	}
//	
//	
//	/**
//	 * 根据项获取用户
//	 * @Title: findUserByItemId 
//	 * @author liusheng
//	 * @date 2016年5月10日 下午5:12:53 
//	 * @param @param itemId
//	 * @param @return 设定文件 
//	 * @return ActionResultModel<UserEntity> 返回类型 
//	 * @throws
//	 */
//	@RequestMapping(value = "/findUserByItemId")
//	@ResponseBody
//	private ActionResultModel<UserEntity> findUserByItemId(@RequestParam(value = "itemId")String itemId){
//		ActionResultModel<UserEntity> arm = new ActionResultModel<UserEntity>();
//		try {
//			List<UserEntity> users = getService().findUserByItemId(itemId);
//			arm.setRecords(users);
//			arm.setSuccess(true);
//		} catch (ServiceException e) {
//			arm.setSuccess(false);
//			arm.setMsg(e.getMessage());
//		}
//
//		return arm;
//	}
//	
//	/**
//	 * 获取可选中的用户列表
//	 * @Title: findAllUserByRoleId 
//	 * @author liusheng
//	 * @date 2016年1月13日 下午3:52:44 
//	 * @param @param roleId
//	 * @param @param user
//	 * @param @return 设定文件 
//	 * @return ActionResultModel<UserEntity> 返回类型 
//	 * @throws
//	 */
//	@RequestMapping(value = "/findAllUserByItemId")
//	@ResponseBody
//	private ActionResultModel<UserEntity> findAllUserByItemId(@RequestParam(value = "itemId")String itemId){
//		ActionResultModel<UserEntity> arm = new ActionResultModel<UserEntity>();
//		try {
//			List<UserEntity> users = getService().findAllUserByItemId(itemId);
//			arm.setRecords(users);
//			arm.setSuccess(true);
//		} catch (ServiceException e) {
//			arm.setSuccess(false);
//			arm.setMsg(e.getMessage());
//		}
//
//		return arm;
//	}

	
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
	@RequestMapping(value = "/delItem")
	public ActionResultModel<ProcessItemEntity> delItem(@RequestParam(value = "pks")String pks) {
		ActionResultModel<ProcessItemEntity> arm = new ActionResultModel<ProcessItemEntity>();
		try {
			getService().delItem(pks);
			arm.setSuccess(true);
		} catch (ServiceException e) {
			arm.setSuccess(false);
			arm.setMsg(e.getMessage());
		}
		return arm;
	}
	
	
}
