package com.yy.workflow.item;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.yy.common.annotation.MetaData;
import com.yy.frame.entity.BaseEntity;
import com.yy.workflow.group.ProcessGroupEntity;
import com.yy.workflow.user.ProcessUserGroupEntity;

/**
 * 流程项
 * 
 * @ClassName: ProcessItemEntity
 * @author liusheng
 * @date 2016年5月10日 下午3:47:20
 */
@MetaData(value = "流程项")
@Entity
@Table(name = "yy_process_item")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProcessItemEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@MetaData(value = "流程编码")
	@Column(nullable = false, length = 100)
	private String code;

	@MetaData(value = "流程名称")
	@Column(nullable = false, length = 200)
	private String name;

	@MetaData(value = "流程描述")
	@Column(length = 256)
	private String description;

	@MetaData(value = "流程项组")
	@ManyToOne(optional = false)
	@JoinColumn(name = "process_group_id")
	private ProcessGroupEntity processgroup;

	@MetaData(value = "操作")
	@Column
	private String flowaction;

	@MetaData(value = "流程类型,1代表审批流，2代表业务流，")
	@Column
	private String flowtype;

	@MetaData(value = "审批节点,0结束节点，1开始节点，2审批节点，")
	@Column
	private String flownode;

	@MetaData(value = "下一流程编码")
	@Column
	private String nextflowcode;

	@MetaData(value = "流程用户组")
	@ManyToOne(optional = true)
	@JoinColumn(name = "process_usergroup_id", nullable = true)
	private ProcessUserGroupEntity usergroup;

	@MetaData(value = "发送类型,1发送全部,2本级机构,3上级机构")
	@Column
	private String flowsend;

	@MetaData(value = "url")
	@Column
	private String url;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ProcessGroupEntity getProcessgroup() {
		return processgroup;
	}

	public void setProcessgroup(ProcessGroupEntity processgroup) {
		this.processgroup = processgroup;
	}

	public String getFlowaction() {
		return flowaction;
	}

	public void setFlowaction(String flowaction) {
		this.flowaction = flowaction;
	}

	public String getFlowtype() {
		return flowtype;
	}

	public void setFlowtype(String flowtype) {
		this.flowtype = flowtype;
	}

	public String getFlownode() {
		return flownode;
	}

	public void setFlownode(String flownode) {
		this.flownode = flownode;
	}

	public String getNextflowcode() {
		return nextflowcode;
	}

	public void setNextflowcode(String nextflowcode) {
		this.nextflowcode = nextflowcode;
	}

	public ProcessUserGroupEntity getUsergroup() {
		return usergroup;
	}

	public void setUsergroup(ProcessUserGroupEntity usergroup) {
		this.usergroup = usergroup;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFlowsend() {
		return flowsend;
	}

	public void setFlowsend(String flowsend) {
		this.flowsend = flowsend;
	}

}
