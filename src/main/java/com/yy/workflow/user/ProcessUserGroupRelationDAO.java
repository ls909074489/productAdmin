package com.yy.workflow.user;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.yy.frame.dao.IBaseDAO;

/**
 * 流程项用户关联表
 * 
 * @ClassName: ProcessItemUserDAO
 * @author liusheng
 * @date 2016年5月10日 下午6:03:05
 */
public interface ProcessUserGroupRelationDAO extends IBaseDAO<ProcessUserGroupRelationEntity, String> {
	@Modifying
	@Query("delete from ProcessUserGroupRelationEntity ur where ur.userGroup.uuid = ?1")
	public void delUserGroup(String groupId);

	@Modifying
	@Query("delete from ProcessUserGroupRelationEntity ur where ur.user.uuid = ?1 and ur.userGroup.uuid=?2")
	public void deleteByUserIdAndByGroupId(String userId, String groupId);

	@Query("from ProcessUserGroupRelationEntity ur where ur.status='1' and ur.user.uuid = ?1 and ur.userGroup.uuid=?2")
	public ProcessUserGroupRelationEntity findByUserIdAndByGroupId(String userId, String groupId);

	@Query("from ProcessUserGroupRelationEntity ur where ur.status='1' and ur.userGroup.uuid=?1")
	public List<ProcessUserGroupRelationEntity> findByItemId(String itemId);

	@Query("from ProcessUserGroupRelationEntity ur where ur.status='1' and ur.userGroup.code=?1")
	public List<ProcessUserGroupRelationEntity> findByGroupCode(String code);

}
