package com.yy.modules.info.supplier;

import javax.persistence.Column;
import com.yy.frame.entity.BaseEntity;
import com.yy.common.annotation.MetaData;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 供应商信息
 * @author ls2008
 * @date 2017-11-18 21:06:10
 */
@Entity
@Table(name = "yy_supplier")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SupplierEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@MetaData(value = "名称")
	@Column(length = 250)
	private String name;


	@MetaData(value = "地址")
	@Column(length = 36)
	private String address;

	@MetaData(value = "联系人")
	@Column(length = 36)
	private String contracts;

	@MetaData(value = "电话")
	@Column(length = 30)
	private String phone;

	@MetaData(value = "备注")
	@Column(length = 250)
	private String memo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContracts() {
		return contracts;
	}

	public void setContracts(String contracts) {
		this.contracts = contracts;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}