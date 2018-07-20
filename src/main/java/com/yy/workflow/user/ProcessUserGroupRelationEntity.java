package com.yy.workflow.user;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.yy.frame.entity.BaseEntity;
import com.yy.modules.sys.user.UserEntity;

/**
 * 流程组关联用户关联表
 * 
 * @ClassName: ProcessItemUserEntity
 * @author liusheng
 * @date 2016年5月10日 下午5:23:54
 */
@Entity
@Table(name = "yy_process_usergroup_relation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProcessUserGroupRelationEntity extends BaseEntity {

	private static final long serialVersionUID = 652217557473174428L;

	@ManyToOne(targetEntity = UserEntity.class)
	@JoinColumn(name = "user_id", referencedColumnName = "uuid")
	private UserEntity user;

	@ManyToOne(targetEntity = ProcessUserGroupEntity.class)
	@JoinColumn(name = "usergroup_id", referencedColumnName = "uuid")
	private ProcessUserGroupEntity userGroup;

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public ProcessUserGroupEntity getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(ProcessUserGroupEntity userGroup) {
		this.userGroup = userGroup;
	}

}
