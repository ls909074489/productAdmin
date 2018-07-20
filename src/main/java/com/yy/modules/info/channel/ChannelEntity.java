package com.yy.modules.info.channel;

import javax.persistence.CascadeType;
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
import com.yy.frame.entity.BaseEntity;
import com.yy.modules.sys.org.OrgEntity;

/**
 * 厂站通道信息
 * @author ls2008
 * @date 2017-11-18 20:44:23
 */
@Entity
@Table(name = "yy_channel")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ChannelEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@MetaData(value = "名称")
	@Column(length = 250)
	private String name;

	@MetaData(value = "所属厂站")
	@ManyToOne(cascade=CascadeType.REFRESH,optional = true)
	@JoinColumn(name = "station_id",nullable=true)
	private OrgEntity station;
	
	@MetaData(value = "通道类型")
	@Column(length = 36)
	private String channelType;

	@MetaData(value = "通道参数")
	@Column(length = 36)
	private String argument;

	@MetaData(value = "备注")
	@Column(length = 250)
	private String memo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getArgument() {
		return argument;
	}

	public void setArgument(String argument) {
		this.argument = argument;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public OrgEntity getStation() {
		return station;
	}

	public void setStation(OrgEntity station) {
		this.station = station;
	}
	
}