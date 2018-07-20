package com.yy.modules.sys.department;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import com.yy.frame.dao.IBaseDAO;

public interface DepartmentDAO extends IBaseDAO<DepartmentEntity, String> {

	@Query("from DepartmentEntity a where ( a.parentid is null or a.parentid='') and a.status = 1 order by code")
	List<DepartmentEntity> getFirstLevel();

	@Query("from DepartmentEntity a where a.parentid = ?1 and a.status = 1 order by code")
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value = "true") })
	List<DepartmentEntity> findByParentId(String parentId);
	
	/**
	 * 根据业务单元获取部门
	 * @Title: getDeptByOrgId 
	 * @author liusheng
	 * @date 2016年1月19日 下午7:03:49 
	 * @param @param orgId
	 * @param @return 设定文件 
	 * @return List<DepartmentEntity> 返回类型 
	 * @throws
	 */
	@Query("from DepartmentEntity a where  a.status = 1  and a.corp.uuid=?1 order by a.code")
	List<DepartmentEntity> getDeptByOrgId(String orgId);
	
	/**
	 * 
	 * @param orgId
	 * @return
	 */
	@Query("from DepartmentEntity a where  a.status = 1  and a.code=?1 order by a.code")
	List<DepartmentEntity> getByCode(String code);


}