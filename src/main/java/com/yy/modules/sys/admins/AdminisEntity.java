package com.yy.modules.sys.admins;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yy.common.annotation.MetaData;
import com.yy.frame.entity.TreeEntity;

/**
 * 行政区域
 * 
 * @ClassName: AdminisEntity
 * @author liusheng
 * @date 2016年2月3日 上午11:05:05
 */
@MetaData(value = "行政区域")
@Entity
@Table(name = "bd_administrative")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdminisEntity extends TreeEntity {

	private static final long serialVersionUID = 1L;

	@MetaData(value = "区域名称")
	@Column(nullable = false, length = 100)
	private String admin_name;

	@MetaData(value = "区域编码")
	@Column(nullable = false, length = 100)
	private String admin_code;

	// @MetaData(value = "上级id")
	// @Column(length = 100)
	// private String parentid;
	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "parentid")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private AdminisEntity parent;

	@MetaData(value = "区域拼音")
	@Column(length = 100)
	private String pinyin;

	@MetaData(value = "备注")
	@Column(length = 250)
	private String memo;

	// @MetaData(value = "是否末级节点")
	// private Boolean islast;

	// @MetaData(value = "父code")
	// @Column(length = 250)
	// private String parentcode;

	public String getAdmin_name() {
		return admin_name;
	}

	public void setAdmin_name(String admin_name) {
		this.admin_name = admin_name;
	}

	public String getAdmin_code() {
		return admin_code;
	}

	public void setAdmin_code(String admin_code) {
		this.admin_code = admin_code;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public AdminisEntity getParent() {
		return parent;
	}

	public void setParent(AdminisEntity parent) {
		this.parent = parent;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Override
	public String getParentId() {
		if (parent != null && parent.getUuid() != null) {
			return this.parent.getUuid();
		} else {
			return "";
		}
	}

	@Override
	public String getCode() {
		return this.getAdmin_code();
	}

	@Override
	public String getName() {
		return this.getAdmin_name();
	}

	// public String getParentcode() {
	// return parentcode;
	// }
	//
	// public void setParentcode(String parentcode) {
	// this.parentcode = parentcode;
	// }

}
