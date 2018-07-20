package com.yy.modules.sys.instructions;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.yy.common.annotation.MetaData;
import com.yy.frame.entity.BaseEntity;

@MetaData(value = "使用说明")
@Entity
@Table(name = "yy_instructions")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InstructionsEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@MetaData(value = "菜单id")
	@Column(name="func_id",length = 36,unique=true)
	private String funcId;

	@MetaData(value = "标题")
	@Column(length = 200)
	private String title;

	@Lob
	@MetaData(value = "内容")
	private String content;

	@MetaData(value = "备注")
	@Column(length = 200)
	private String remark;

	public String getFuncId() {
		return funcId;
	}

	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
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

}
