package com.yy.modules.sys.department;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yy.common.bean.TreeBaseBean;
import com.yy.common.utils.Constants;
import com.yy.frame.controller.ActionResultModel;
import com.yy.frame.controller.TreeController;
import com.yy.modules.sys.org.OrgEntity;
import com.yy.modules.sys.org.OrgService;

/**
 * 业务部门
 * 
 * @ClassName: DepartmentController
 * @author liusheng
 * @date 2016年1月16日 上午11:24:47
 */
@Controller
@RequestMapping(value = "/sys/department")
public class DepartmentController extends TreeController<DepartmentEntity> {
	private DepartmentService getService() {
		return (DepartmentService) super.baseService;
	}

	@Autowired
	private OrgService orgService;// 业务单位

	@RequestMapping(method = RequestMethod.GET)
	public String view(Model model) {
		model.addAttribute("orgId", Constants.DEFAULT_ORG_ID);
		return "modules/sys/department/department_main";
	}

	@RequestMapping(value = "/selectDepartment")
	public String selectDepartment() {
		return "modules/sys/department/department_select";
	}

	/**
	 * 获取业务单位 树 @Title: getTreeNodes @author liusheng @date 2016年1月16日 下午12:02:07 @param @param request @param @param
	 * pid @param @return 设定文件 @return List<TreeBaseBean> 返回类型 @throws
	 */
	@RequestMapping(value = "/getTreeNodes")
	@ResponseBody
	private List<TreeBaseBean> getTreeNodes(ServletRequest request,
			@RequestParam(value = "node", defaultValue = "") String pid) {
		List<OrgEntity> orgList = (List<OrgEntity>) orgService.findAll(new Sort(Sort.Direction.ASC, "nodepath"));// 获取业务单位
		TreeBaseBean treeBean = null;
		List<TreeBaseBean> treeList = new ArrayList<TreeBaseBean>();
		if (orgList != null && orgList.size() > 0) {
			for (OrgEntity o : orgList) {
				treeBean = new TreeBaseBean(o.getUuid(), o.getName(), o.getParentId(), false, false, "iconSkinOrg",
						o.getName(), "1", o);// 单位type=1作为判断
				treeList.add(treeBean);
			}
		}
		List<DepartmentEntity> deptList = (List<DepartmentEntity>) getService().findAll();// 业务部门
		if (deptList != null & deptList.size() > 0) {
			for (DepartmentEntity dept : deptList) {
				treeBean = new TreeBaseBean(dept.getUuid(), dept.getName(), dept.getParentId(), false, false,
						"iconSkinDept", dept.getName(), "2", dept);// 部门type=2作为判断
				treeList.add(treeBean);
			}
		}
		return treeList;
	}

	/**
	 * 根据业务单元获取部门 @Title: getDeptByOrgId @author liusheng @date 2016年1月19日 下午6:59:32 @param @param request @param @param
	 * orgId @param @return 设定文件 @return List<TreeBaseBean> 返回类型 @throws
	 */
	@RequestMapping(value = "/getDeptByOrgId")
	@ResponseBody
	private List<TreeBaseBean> getDeptByOrgId(ServletRequest request, String orgId) {
		List<TreeBaseBean> treeList = new ArrayList<TreeBaseBean>();
		if (!StringUtils.isEmpty(orgId)) {
			TreeBaseBean bean = null;
			OrgEntity org = orgService.getOne(orgId);
			List<DepartmentEntity> deptList = getService().getDeptByOrgId(orgId);
			if (org != null) {
				bean = new TreeBaseBean(org.getUuid(), org.getName(), org.getParentId(), false, false, org);
				bean.setType("1");
				treeList.add(bean);
			}
			if (deptList != null && deptList.size() > 0) {
				for (DepartmentEntity d : deptList) {
					bean = new TreeBaseBean(d.getUuid(), d.getName(), d.getParentId(), false, false, d);
					bean.setType("2");
					treeList.add(bean);
				}
			}
		}
		return treeList;
	}

	@Override
	public ActionResultModel<DepartmentEntity> add(ServletRequest request, Model model, DepartmentEntity entity) {
		List<DepartmentEntity> result = (List<DepartmentEntity>) getService().findByParentId(entity.getParentid());
		if(result!=null&&result.size()>0){
			for (DepartmentEntity a : result) {
				if(a.getName().equals(entity.getName())){
					ActionResultModel<DepartmentEntity> arm=new ActionResultModel<DepartmentEntity>();
					arm.setSuccess(false);
					arm.setMsg("同级下已添加"+entity.getName());
					return arm;
				}
			}
		}
		return super.add(request, model, entity);
	}

	@Override
	public ActionResultModel<DepartmentEntity> update(ServletRequest request, Model model, DepartmentEntity entity) {
		List<DepartmentEntity> result = (List<DepartmentEntity>) getService()
				.findByParentId(entity.getParentid());
		if(result!=null&&result.size()>0){
			for (DepartmentEntity a : result) {
				if(a.getName().equals(entity.getName())&&!a.getUuid().equals(entity.getUuid())){
					ActionResultModel<DepartmentEntity> arm=new ActionResultModel<DepartmentEntity>();
					arm.setSuccess(false);
					arm.setMsg("同级下已添加"+entity.getName());
					return arm;
				}
			}
		}
		return super.update(request, model, entity);
	}
}