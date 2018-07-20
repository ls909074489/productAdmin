package com.yy.modules.sys.department;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.yy.common.annotation.MetaData;

@MetaData(value = "业务部门信息")
@Entity
@Table(name = "yy_department")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DepartmentRef{
	@Id
	@Column
	private String uuid;
	
	@MetaData(value = "部门代码")
	@Column(nullable = false, length = 20, unique = true)
	private String code;

	@MetaData(value = "部门名称")
	@Column(nullable = false, length = 100)
	private String name;

	
	
	public DepartmentRef() {
	}

	
	public DepartmentRef(String uuid) {
		this.uuid = uuid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

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
}