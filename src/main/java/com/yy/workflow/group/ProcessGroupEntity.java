package com.yy.workflow.group;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.yy.common.annotation.MetaData;
import com.yy.frame.entity.TreeEntity;

/**
 * 流程组
 * 
 * @ClassName: ProcessGroupEntity
 * @author liusheng
 * @date 2016年5月10日 下午2:02:04
 */
@Entity
@Table(name = "yy_process_group")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProcessGroupEntity extends TreeEntity {

	private static final long serialVersionUID = 1L;

	@MetaData(value = "流程组编码")
	@Column(nullable = false, length = 20)
	private String group_code;

	@MetaData(value = "流程组名称")
	@Column(nullable = false, length = 50)
	private String group_name;

	@Column(length = 2, columnDefinition = "varchar(2) default '1'")
	@MetaData(value = "是否启用")
	private String isuse = "1";

	@MetaData(value = "url")
	@Column(length = 100)
	private String link;

	@MetaData(value = "描述")
	private String description;

	@MetaData(value = "父节点id")
	private String parentid;

	public String getGroup_code() {
		return group_code;
	}

	public void setGroup_code(String group_code) {
		this.group_code = group_code;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	@Override
	public String getParentId() {
		return this.getParentid();
	}

	@Override
	public String getCode() {
		return this.getGroup_code();
	}

	@Override
	public String getName() {
		return this.getGroup_name();
	}

	public String getIsuse() {
		return isuse;
	}

	public void setIsuse(String isuse) {
		this.isuse = isuse;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
