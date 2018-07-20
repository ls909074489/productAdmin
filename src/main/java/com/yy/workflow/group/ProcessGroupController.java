package com.yy.workflow.group;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yy.common.bean.TreeBaseBean;
import com.yy.frame.controller.ActionResultModel;
import com.yy.frame.controller.TreeController;
@Controller
@RequestMapping(value = "/process/group")
public class ProcessGroupController extends TreeController<ProcessGroupEntity> {
	

	@Autowired
	protected ProcessGroupService processGroupService;

	@RequestMapping(method = RequestMethod.GET)
	public String view() {
		return "workflow/group/process_group_main";
	}


	@RequestMapping(value = "/dataGroup")
	@ResponseBody
	private List<TreeBaseBean> dataGroup() {
		 List<ProcessGroupEntity> list=(List<ProcessGroupEntity>) processGroupService.findAll(new Sort(Sort.Direction.ASC, "nodepath"));// (ITreeEntity)
		 List<TreeBaseBean> treeList=new ArrayList<TreeBaseBean>();
		TreeBaseBean bean=null;
		if(list!=null&&list.size()>0){
			for(ProcessGroupEntity d:list){
				bean=new TreeBaseBean(d.getUuid(), d.getGroup_name(), d.getParentId(), false, false,d);
				treeList.add(bean);
			}
		}
		return treeList;
	}
	
	/**
	 * 删除角色组
	 * @Title: delete 
	 * @author liusheng
	 * @date 2016年1月13日 下午6:04:31 
	 * @param @param request
	 * @param @param model
	 * @param @param pks
	 * @param @return 设定文件 
	 * @return ActionResultModel 返回类型 
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/delRoleGroup")
	@ResponseBody
	public ActionResultModel delete(ServletRequest request, Model model, String pks) {
		ActionResultModel arm = new ActionResultModel();
		try {
			arm=processGroupService.deleteRoleGroup(pks);
		} catch (ServiceException e) {
			arm.setSuccess(false);
			arm.setMsg(e.getMessage());
		}
		return arm;
	}
	
}
