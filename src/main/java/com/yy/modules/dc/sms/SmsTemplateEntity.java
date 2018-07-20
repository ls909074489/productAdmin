package com.yy.modules.dc.sms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.StringEscapeUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.yy.common.annotation.MetaData;
import com.yy.frame.entity.BaseEntity;

@MetaData(value = "Smstemplate信息")
@Entity
@Table(name = "yy_sms_template")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SmsTemplateEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@MetaData(value = "相关单据")
	@Column(length = 2)
	private String relevant_bill;

	@MetaData(value = "模版名称")
	@Column(length = 100)
	private String name;

	@MetaData(value = "模版编码")
	@Column(length = 100)
	private String code;

	@MetaData(value = "模版内容")
	@Column(length = 2000)
	private String content;

	@MetaData(value = "备注")
	@Column(length = 2000)
	private String remark;

	@MetaData(value = "发送触发动作")
	@Column(length = 2)
	private String send_action;

	public String getRelevant_bill() {
		return relevant_bill;
	}

	public void setRelevant_bill(String relevant_bill) {
		this.relevant_bill = relevant_bill;
	}

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

	public String getContent() {
		return content;
		// return StringEscapeUtils.unescapeHtml(content);
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSend_action() {
		return send_action;
	}

	public void setSend_action(String send_action) {
		this.send_action = send_action;
	}
}
