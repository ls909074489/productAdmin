package com.yy.modules.dc.mail;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.yy.common.annotation.MetaData;
import com.yy.frame.entity.BaseEntity;

@MetaData(value = "MailTemplate信息")
@Entity
@Table(name = "yy_mail_template")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MailTemplateEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@MetaData(value = "相关单据")
	@Column(length = 36)
	private String relevant_bill;

	@MetaData(value = "模版编码")
	@Column(length = 100,unique=true)
	private String code;

	@MetaData(value = "模版名称")
	@Column(length = 100)
	private String name;

	@MetaData(value = "模版内容")
	@Lob
	private String content;

	@MetaData(value = "备注")
	@Column(length = 2000)
	private String remark;
	
	
	@MetaData(value = "收件人")
	@Column(length = 2000)
	private String receiver;
	
	@MetaData(value = "标题")
	@Column(length = 250)
	private String title;

	@MetaData(value = "临时保存收件人")
	@Transient
	private String tReceiver;
	@MetaData(value = "临时保存发件内容")
	@Transient
	private String tContent;
	
	
	
	@MetaData(value = "抄送")
	@Column(length = 2000)
	private String copyTo;
	
	
	
	public String getRelevant_bill() {
		return relevant_bill;
	}

	public void setRelevant_bill(String relevant_bill) {
		this.relevant_bill = relevant_bill;
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

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String gettReceiver() {
		return tReceiver;
	}

	public void settReceiver(String tReceiver) {
		this.tReceiver = tReceiver;
	}

	public String gettContent() {
		return tContent;
	}

	public void settContent(String tContent) {
		this.tContent = tContent;
	}

	public String getCopyTo() {
		return copyTo;
	}

	public void setCopyTo(String copyTo) {
		this.copyTo = copyTo;
	}

}
