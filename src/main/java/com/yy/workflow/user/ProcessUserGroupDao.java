package com.yy.workflow.user;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.yy.frame.dao.IBaseDAO;
/**
 * 流程用户组dao
 * @ClassName: RoleDao
 * @author liusheng 
 * @date 2015年12月17日 上午11:00:14
 */
public interface ProcessUserGroupDao extends IBaseDAO<ProcessUserGroupEntity,String>{

	@Query("from ProcessUserGroupEntity where status=1 and code=?1")
	List<ProcessUserGroupEntity> findByCode(String code);

}
