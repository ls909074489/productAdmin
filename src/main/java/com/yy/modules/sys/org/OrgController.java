package com.yy.modules.sys.org;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yy.common.utils.Constants;
import com.yy.frame.controller.ActionResultModel;
import com.yy.frame.controller.TreeController;
import com.yy.frame.security.ShiroUser;
import com.yy.modules.sys.user.UserEntity;

@Controller
@RequestMapping(value = "/sys/org")
public class OrgController extends TreeController<OrgEntity> {

	private OrgService getService() {
		return (OrgService) super.baseService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String view() {
		return "modules/sys/org/org_main";
	}

	@RequestMapping(value = "/orgView", method = RequestMethod.GET)
	private String orgView(Model model) {
		// 获取当前用户信息
		// UserEntity userEntity = ShiroUser.getCurrentUserEntity();
		// 获取当前用户的说登录的组织信息
		OrgEntity orgEntity = ShiroUser.getCurrentOrgEntity();
		model.addAttribute("entity", orgEntity);
		return "modules/sys/org/org_view";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/findByWhere")
	@ResponseBody
	public ActionResultModel findByWhere(ServletRequest request, Model model,
			@RequestParam(value = "where", required = false) String where) {
		ActionResultModel arm = new ActionResultModel();
		try {
			List<OrgEntity> list = getService().findByWhere(where);
			arm.setRecords(list);
			arm.setSuccess(true);
		} catch (ServiceException e) {
			e.printStackTrace();
			arm.setSuccess(false);
			arm.setMsg(e.getMessage());
		}
		return arm;
	}

	@RequestMapping(value = "/addOrg")
	@ResponseBody
	public ActionResultModel<OrgEntity> addOrg(MultipartHttpServletRequest request, Model model, @Valid OrgEntity entity) {
		ActionResultModel<OrgEntity> arm = new ActionResultModel<OrgEntity>();
		try {
			List<MultipartFile> files = request.getFiles("attachment[]");
			entity = getService().addOrg(entity,files);
			arm.setRecords(entity);
			arm.setSuccess(true);
		} catch (ServiceException e) {
			e.printStackTrace();
			arm.setSuccess(false);
			arm.setMsg(e.getMessage());
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			arm.setSuccess(false);
			arm.setMsg(Constants.getConstraintMsg(e.getMessage()));
		} catch (Exception e) {
			arm.setSuccess(false);
			arm.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return arm;
	}

	@RequestMapping(value = "/updateOrg")
	@ResponseBody
	public ActionResultModel<OrgEntity> updateOrg(MultipartHttpServletRequest request, Model model,
			@Valid OrgEntity entity) {
		ActionResultModel<OrgEntity> arm = new ActionResultModel<OrgEntity>();
		try {
			List<MultipartFile> files = request.getFiles("attachment[]");
			entity = getService().updateOrg(entity,files);
			arm.setRecords(entity);
			arm.setSuccess(true);
		} catch (ServiceException e) {
			arm.setSuccess(false);
			String msg = e.getMessage();
			if (msg.contains("constraint")) {
				arm.setMsg(Constants.getConstraintMsg(e.getMessage()));
			} else {
				arm.setMsg(e.getMessage());
			}
			e.printStackTrace();
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			arm.setSuccess(false);
			arm.setMsg(Constants.getConstraintMsg(e.getMessage()));
		} catch (Exception e) {
			arm.setSuccess(false);
			arm.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return arm;
	}
	
	/**
	 * 跳到改变当前厂商页面
	 * @Title: toChangeStation 
	 * @author liusheng
	 * @date 2017年11月24日 下午4:07:53 
	 * @param @param model
	 * @param @return 设定文件 
	 * @return String 返回类型 
	 * @throws
	 */
	@RequestMapping("/toChangeStation")
	public String toChangeStation(Model model) {
//		UserEntity user=ShiroUser.getCurrentUserEntity();
//		List<OrgEntity> stationLists = getService().findOwnOrg(user.getUuid());
		
//		model.addAttribute("stationLists", stationLists);
		
		return "modules/info/station/station_change_page";
	}

	
	/**
	 * 改变session当前厂站
	 * @Title: changeSessionStation 
	 * @author liusheng
	 * @date 2017年11月24日 下午4:00:46 
	 * @param @param request
	 * @param @param stationId
	 * @param @return 设定文件 
	 * @return ActionResultModel<OrgEntity> 返回类型 
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/changeSessionStation")
	public ActionResultModel<OrgEntity> changeSessionStation(ServletRequest request,String stationId) {
		ActionResultModel<OrgEntity> arm = new ActionResultModel<OrgEntity>();
		OrgEntity station=getService().getOne(stationId);
		if(station!=null&&!StringUtils.isEmpty(station.getUuid())){
			setCurrentStation(request, station);
			arm.setSuccess(true);
			arm.setRecords(station);
			arm.setMsg(station.getOrg_name()+"("+station.getOrg_code()+")");
		}else{
			arm.setSuccess(false);
			arm.setMsg("查询不到该信息!");
		}
		return arm;
	}

	@ResponseBody
	@RequestMapping(value = "/querySelect2")
	public Map<String,Object> querySelect2(HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> inMap = new HashMap<String, Object>(); 
		UserEntity user=ShiroUser.getCurrentUserEntity();
		List<OrgEntity> list = getService().findOwnOrg(user.getUuid());
		StringBuffer sb = new StringBuffer("<option value='&nbsp;'>&nbsp;</option>");
		for (OrgEntity org : list) {
			sb.append("<option value='").append(org.getUuid()).append("'>").append(org.getOrg_name()).append("</option>");
		}
		inMap.put("details", sb.toString());
		inMap.put("length", list.size());
		return inMap;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/dataCurrentOrg")
	public ActionResultModel<OrgEntity> dataCurrentOrg(ServletRequest request) {
		ActionResultModel<OrgEntity> arm = new ActionResultModel<OrgEntity>();
		OrgEntity station=getCurrentStation(request);
		if(station!=null&&!StringUtils.isEmpty(station.getUuid())){
			arm.setSuccess(true);
			arm.setRecords(station);
			arm.setMsg("suc");
		}else{
			arm.setSuccess(false);
			arm.setMsg("查询不到当前厂站!");
		}
		return arm;
	}
}