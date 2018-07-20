package com.yy.workflow.group;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.yy.frame.dao.IBaseDAO;

/**
 * 流程组dao
 * 
 * @ClassName: ProcessGroupDAO
 * @author liusheng
 * @date 2016年5月10日 下午2:04:11
 */
public interface ProcessGroupDAO extends IBaseDAO<ProcessGroupEntity, String> {

	@Query("from ProcessGroupEntity a where a.parentid is null order by group_code")
	List<ProcessGroupEntity> getFirstLevel();

	@Query("from ProcessGroupEntity a where a.parentid = ?1 order by group_code")
	List<ProcessGroupEntity> findByParentId(String parentId);

	@Query("from ProcessGroupEntity a where a.status=1 and a.isuse='1' and a.group_code=?1")
	public List<ProcessGroupEntity> getGroupByCode(String code);

}
