package com.yy.workflow.item;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.yy.frame.dao.IBaseDAO;
import com.yy.workflow.group.ProcessGroupEntity;

/**
 * 角色dao
 * 
 * @ClassName: RoleDao
 * @author liusheng
 * @date 2015年12月17日 上午11:00:14
 */
public interface ProcessItemDao extends IBaseDAO<ProcessItemEntity, String> {

	List<ProcessItemEntity> findByProcessgroup(ProcessGroupEntity processgroup);

	
	@Query("from ProcessItemEntity where processgroup.uuid=?1 and status=1 order by code")
	List<ProcessItemEntity> findItemByProcessgroupId(String processgroupId);

}
