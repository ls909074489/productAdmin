package com.yy.common.table;

import java.util.List;

public class TableModel {

	private List<RowModel> rowList;
	private String tableName;

	public List<RowModel> getRowList() {
		return rowList;
	}

	public void setRowList(List<RowModel> rowList) {
		this.rowList = rowList;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
