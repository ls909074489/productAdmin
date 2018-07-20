package com.yy.modules.sys.admins;

import com.yy.common.annotation.MetaData;

/**
 * 行政区域
 * @ClassName: AdministrativeEntity 
 * @author liusheng
 * @date 2016年2月3日 上午11:05:05
 */
@MetaData(value = "行政区域")
public class AdminisBean {
	
	private String uuid;
	
	private String admin_name;
	
	private String parentId;
	
	public AdminisBean() {
	}
	public AdminisBean(String uuid) {
		this.uuid = uuid;
	}

	public AdminisBean(String uuid, String admin_name, String parentId) {
		this.uuid = uuid;
		this.admin_name = admin_name;
		this.parentId = parentId;
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getAdmin_name() {
		return admin_name;
	}

	public void setAdmin_name(String admin_name) {
		this.admin_name = admin_name;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
}
