package com.yy.common.enums;

public enum AuditStatus {
	NO(0, "未审核"),//相对于订购来说是撤审
	SUCCESS(1, "通过"),
	FAIL(2, "失败");//相对于出入库来说是撤审
	
	
	private int status;
	private String name;
	
	
	private AuditStatus(int status,String name) {
		this.status = status;
		this.name = name;
	}

	public int toStatusValue() {
		return this.status;
	}
	
	public String toStatusNameValue(){
		return this.name;
	}
	
	
}
