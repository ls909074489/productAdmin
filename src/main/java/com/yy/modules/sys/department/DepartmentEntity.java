package com.yy.modules.sys.department;

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
import com.yy.frame.entity.TreeEntity;
import com.yy.modules.sys.org.OrgEntity;

@MetaData(value = "业务部门信息")
@Entity
@Table(name = "yy_department")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DepartmentEntity extends TreeEntity {

	private static final long serialVersionUID = 1L;

	@MetaData(value = "部门代码")
	@Column(nullable = false, length = 20, unique = true)
	private String code;

	@MetaData(value = "部门名称")
	@Column(nullable = false, length = 100)
	private String name;

	@MetaData(value = "上级部门")
	@Column(length = 100)
	private String parentid;

	@MetaData(value = "创建人")
	@Column(length = 50)
	private String creater;

	@MetaData(value = "创建时间")
	private String createdDate;

	@MetaData(value = "审核人")
	@Column(length = 50)
	private String verifier;

	@MetaData(value = "审核时间")
	private String verifiedDate;

	// @MetaData(value = "是否启用")
	// private String active;

	@MetaData(value = "是否末级节点")
	private Boolean islast;

	@MetaData(value = "是否直属 部门")
	private String isDirect = "0";// 是否直属 1:上级为单位 0：上级为部门

	@MetaData(value = "备注")
	@Column(length = 2000)
	private String memo;// 备注

	@MetaData(value = "所属业务单元")
	@ManyToOne(optional = false)
	@JoinColumn(name = "pk_corp", nullable = true)
	private OrgEntity corp;

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getVerifier() {
		return verifier;
	}

	public void setVerifier(String verifier) {
		this.verifier = verifier;
	}

	public String getVerifiedDate() {
		return verifiedDate;
	}

	public void setVerifiedDate(String verifiedDate) {
		this.verifiedDate = verifiedDate;
	}

	// public String getActive() {
	// return active;
	// }
	//
	// public void setActive(String active) {
	// this.active = active;
	// }

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getParentId() {
		return this.parentid;
	}

	public Boolean getIslast() {
		return islast;
	}

	public void setIslast(Boolean islast) {
		this.islast = islast;
	}

	public String getIsDirect() {
		return isDirect;
	}

	public void setIsDirect(String isDirect) {
		this.isDirect = isDirect;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public OrgEntity getCorp() {
		return corp;
	}

	public void setCorp(OrgEntity corp) {
		this.corp = corp;
	}

}