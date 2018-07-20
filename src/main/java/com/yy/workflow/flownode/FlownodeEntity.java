package com.yy.workflow.flownode;

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
 * 审批节点
 *
 */
@Entity
@Table(name = "yy_flownode")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FlownodeEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@MetaData(value = "流程组id")
	@Column(nullable = false, length = 36)
	private String flowgroupid;

	@MetaData(value = "单据类型")
	@Column(nullable = false, length = 50)
	private String billtype;

	@Column(nullable = false, length = 100)
	@MetaData(value = "单据ID")
	private String billid;

	@Column(nullable = false)
	@MetaData(value = "最新流程id")
	private String flowid;

	@Column(length = 100)
	@MetaData(value = "流程编码")
	private String flowcode;

	public String getBilltype() {
		return billtype;
	}

	public void setBilltype(String billtype) {
		this.billtype = billtype;
	}

	public String getBillid() {
		return billid;
	}

	public void setBillid(String billid) {
		this.billid = billid;
	}

	public String getFlowid() {
		return flowid;
	}

	public void setFlowid(String flowid) {
		this.flowid = flowid;
	}

	public String getFlowcode() {
		return flowcode;
	}

	public void setFlowcode(String flowcode) {
		this.flowcode = flowcode;
	}

	public String getFlowgroupid() {
		return flowgroupid;
	}

	public void setFlowgroupid(String flowgroupid) {
		this.flowgroupid = flowgroupid;
	}

}