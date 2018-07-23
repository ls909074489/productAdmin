package com.yy.modules.info.interval;

import java.util.Date;

import javax.persistence.Column;
import com.yy.frame.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yy.common.annotation.MetaData;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 间隔信息
 * @author ls2008
 * @date 2017-11-18 21:54:30
 */
@Entity
@Table(name = "yy_interval")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class IntervalEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@MetaData(value = "名称")
	@Column(length = 250)
	private String name;
	
	@Column
	private Double price;

	@MetaData(value = "备注")
	@Column(length = 250)
	private String memo;
	
	@MetaData(value = "审批状态", comments = "枚举AuditStatus")
	@Column(precision = 2, scale = 0)
	protected Integer billstatus = 0;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@MetaData(value = "工厂提交时间")
	@Column()
	private Date factoryConfrimTime;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getBillstatus() {
		return billstatus;
	}

	public void setBillstatus(Integer billstatus) {
		this.billstatus = billstatus;
	}

	public Date getFactoryConfrimTime() {
		return factoryConfrimTime;
	}

	public void setFactoryConfrimTime(Date factoryConfrimTime) {
		this.factoryConfrimTime = factoryConfrimTime;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}