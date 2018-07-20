package com.yy.modules.sys.user;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yy.frame.controller.ActionResultModel;
import com.yy.frame.controller.BaseController;
import com.yy.frame.security.ShiroUser;
import com.yy.modules.sys.role.RoleEntity;
import com.yy.modules.sys.role.RoleService;
@Controller
@RequestMapping(value = "/userProfile")
public class UserProfileController extends BaseController<UserEntity> {
	
	
	@Autowired
	private RoleService roleService;//角色service
	
	@RequestMapping(method = RequestMethod.GET)
	public String view() {
		return "frame/user_profile";
	}
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/current")
	@ResponseBody
	public ActionResultModel currentUser() {
		ActionResultModel arm = new ActionResultModel();
		try{
		UserEntity  entity = ShiroUser.getCurrentUserEntity();
		//ActionResultModel<UserEntity> pageData = doQuery(request);
		// return new PageResult<UserEntity>(draw, pageData.getRecordsTotal(),
		// pageData.getRecordsFiltered(), pageData.getRecords());
		List<RoleEntity> objList=roleService.findSelRole(entity.getUuid());
		String roleNames="";
		if(objList!=null&&objList.size()>0){
			for(RoleEntity role:objList){
				roleNames+=role.getName()+"、";
			}
		}
		if(roleNames.lastIndexOf("、")>0){
			roleNames=roleNames.substring(0, roleNames.lastIndexOf("、"));
		}
		entity.setUserrole(roleNames);
		arm.setRecords(entity);
		arm.setSuccess(true);
	} catch (ServiceException e) {
		// e.printStackTrace();
		arm.setSuccess(false);
		arm.setMsg(e.getMessage());
	}
	return arm;
	
	}
	

	/**
	 * 更新
	 * 
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/update")
	@ResponseBody
	public ActionResultModel update(ServletRequest request, Model model,
			@Valid @ModelAttribute("preloadEntity") UserEntity entity) {
		ActionResultModel arm = new ActionResultModel();
		try {
//			OrgRef org=entity.getCorp();
//			DepartmentEntity dept=entity.getDepartment();
//			PersonEntity person=entity.getPerson();
					
			entity = baseService.save(entity);
			//需要重新赋值
//			entity.setCorp(org);
//			entity.setDepartment(dept);
//			entity.setPerson(person);
			UserEntity user=ShiroUser.getCurrentUserEntity();
			entity.setOrgname(user.getOrgname());
			entity.setDeptname(user.getDeptname());
			entity.setPersonname(user.getPersonname());
			entity.setOrgid(user.getOrgid());
			entity.setDeptid(user.getPersonid());
			entity.setPersonid(user.getPersonid());
			arm.setRecords(entity);
			arm.setSuccess(true);
			ShiroUser.updateCurrentUserEntity(entity);
		} catch (ServiceException e) {
			arm.setSuccess(false);
			arm.setMsg(e.getMessage());
		} catch (Exception e) {
			arm.setSuccess(false);
			arm.setMsg(e.getMessage());
		}
		return arm;
	}

}
