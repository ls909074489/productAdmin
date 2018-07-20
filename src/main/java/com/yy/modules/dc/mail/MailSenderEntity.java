package com.yy.modules.dc.mail;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.yy.common.annotation.MetaData;
import com.yy.frame.entity.BaseEntity;

@MetaData(value = "MailTemplate信息")
@Entity
@Table(name = "yy_mail_sender")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MailSenderEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@MetaData(value = "单据类型")
	@Column(length = 36)
	private String billType;

	@MetaData(value = "姓名")
	@Column(length = 100)
	private String name;

	@MetaData(value = "邮箱")
	@Column(length = 100)
	private String email;
	
	@MetaData(value = "是否默认的")
	@Column(length = 2)
	private String isDefault="0";
	

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	
}
