package com.yy.common.table;

import java.util.ArrayList;
import java.util.List;

public class RowModel {
	public RowModel(){
	}
	public RowModel(List<CellModel> cellList){
		this.cellList = cellList;
	}
	
	private List<CellModel> cellList = new ArrayList<CellModel>();
	private String rowStyle;
	private String rowClass;
	
	public RowModel rowStyle(String rowStyle){
		this.rowStyle=rowStyle;
		return this;
	}
	public RowModel rowClass(String rowClass){
		this.rowClass=rowClass;
		return this;
	}
	public RowModel addCell(CellModel cell){
		this.cellList.add(cell);
		return this;
	}
	public List<CellModel> getCellList() {
		return cellList;
	}
	public void setCellList(List<CellModel> cellList) {
		this.cellList = cellList;
	}
	public String getRowStyle() {
		return rowStyle;
	}
	public void setRowStyle(String rowStyle) {
		this.rowStyle = rowStyle;
	}
	public String getRowClass() {
		return rowClass;
	}
	public void setRowClass(String rowClass) {
		this.rowClass = rowClass;
	}
}
