package com.yy.modules.sys.department;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yy.common.utils.Constants;
import com.yy.frame.dao.IBaseDAO;
import com.yy.frame.service.TreeServiceImpl;
import com.yy.modules.sys.org.OrgEntity;

@Component
@Transactional
public class DepartmentService extends TreeServiceImpl<DepartmentEntity, String> {
	
	@Autowired
	private DepartmentDAO dao;
	
	@Override
	protected IBaseDAO<DepartmentEntity, String> getDAO() {
		return dao;
	}

	/**
	 * 获取第一级节点
	 * 
	 * @return
	 */
	public List<DepartmentEntity> getFirstLevel() {
		return dao.getFirstLevel();
	}

	/**
	 * 根据父节点ID获取子节点，如果传入null或者空字符串，则返回第一级节点
	 * 
	 * @param parentId
	 * @return
	 */
	public List<DepartmentEntity> findByParentId(String parentId) {
		if (StringUtils.isBlank(parentId)) {
			return getFirstLevel();
		}
		return dao.findByParentId(parentId);
	}


	/**
	 * 根据业务单元获取部门
	 * @Title: getDeptByOrgId 
	 * @author liusheng
	 * @date 2016年1月19日 下午7:01:33 
	 * @param @param orgId
	 * @param @return 设定文件 
	 * @return List<DepartmentEntity> 返回类型 
	 * @throws
	 */
	public List<DepartmentEntity> getDeptByOrgId(String orgId) {
		if (StringUtils.isBlank(orgId)) {
			return null;
		}
		return dao.getDeptByOrgId(orgId);
	}
	
	public DepartmentEntity saveAdminDept(OrgEntity org){
		//创建网点管理人员和业务管理人员需要创建管理组部门
		DepartmentEntity dept=new DepartmentEntity();
		List<DepartmentEntity> list=dao.getByCode(org.getCode());
		if(list!=null&&list.size()>0){
			dept=list.get(0);
		}else{
			dept.setCorp(org);
			dept.setParentid(org.getUuid());
			dept.setName(Constants.DEFAULT_ADMIN_DEPT_NAME);
			dept.setCode(org.getCode());
			save(dept);	
		}
		return dept;
	}
	
}