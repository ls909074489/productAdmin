package com.yy.workflow.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.yy.common.annotation.MetaData;
import com.yy.frame.entity.BaseEntity;

/**
 * 流程用户组
 * 
 * @ClassName: ProcessItemEntity
 * @author liusheng
 * @date 2016年5月10日 下午3:47:20
 */
@MetaData(value = "流程项")
@Entity
@Table(name = "yy_process_usergroup")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProcessUserGroupEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@MetaData(value = "流程项名称")
	@Column(nullable = false, length = 50)
	private String name;

	@MetaData(value = "流程项编码")
	@Column(nullable = false, length = 50, unique = true)
	private String code;

	@MetaData(value = "0选择的用户组 1：云南的5租")
	@Column(nullable = false, length = 2)
	private String grouptype="0";
	
	@MetaData(value = "流程项描述")
	@Column(length = 256)
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGrouptype() {
		return grouptype;
	}

	public void setGrouptype(String grouptype) {
		this.grouptype = grouptype;
	}
	
}
