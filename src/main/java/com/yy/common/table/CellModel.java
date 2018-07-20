package com.yy.common.table;

import java.text.SimpleDateFormat;

import jxl.write.WritableCellFormat;

public class CellModel {
	public enum CellStyle{
		title("title"),
		tag("tag"),
		head("head"),
		normal("normal"),
		foot("foot"),
		tag_center("tag_center"),
		tag_right("tag_right"),
		;
		private final String styleType;
		private CellStyle(String styleType) {
			this.styleType = styleType;
		}
	}
	public CellModel(){
		
	}
	public CellModel(Object cellValue){
		this.cellValue = cellValue;
		this.cellStyle = CellStyle.normal;
	}
	public CellModel(Object cellValue,Integer colspan,Integer rowspan,CellStyle cellStyle){
		this.cellValue = cellValue;
		this.colspan = colspan;
		this.rowspan = rowspan;
		this.cellStyle = cellStyle;
	}
	//单元格的值
	private Object cellValue;
	
	//单元格所占列数
	private Integer colspan;
	
	//单元格所占行数
	private Integer rowspan;
	
	//列宽
	private Integer width;
	
	//样式
	private String cellClass="";
	
	//单元格样式 html
	//title,tag,head,normal,foot
	private CellStyle cellStyle;
	
	private WritableCellFormat jxlFormat;
	
	public CellModel colspan( Integer colspan){
		this.colspan = colspan;
		return this;
	}
	public CellModel cellClass( String cellClass){
		this.cellClass = this.cellClass+cellClass;
		return this;
	}
	public CellModel rowspan( Integer rowspan){
		this.rowspan = rowspan;
		return this;
	}
	public CellModel width( Integer width){
		this.width = width;
		return this;
	}
	public CellModel cellValue( Object cellValue){
		this.cellValue = cellValue;
		return this;
	}
	public CellModel cellStyle( CellStyle cellStyle){
		this.cellStyle = cellStyle;
		return this;
	}
	
	public Object getCellValue() {
		return cellValue;
	}
	public void setCellValue(Object cellValue) {
		this.cellValue = cellValue;
	}
	public Integer getColspan() {
		return colspan;
	}
	public void setColspan(Integer colspan) {
		this.colspan = colspan;
	}
	public Integer getRowspan() {
		return rowspan;
	}
	public void setRowspan(Integer rowspan) {
		this.rowspan = rowspan;
	}
	public CellStyle getCellStyle() {
		return cellStyle;
	}
	public void setCellStyle(CellStyle cellStyle) {
		this.cellStyle = cellStyle;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	
	public WritableCellFormat getJxlFormat() {
		return jxlFormat;
	}
	public void setJxlFormat(WritableCellFormat jxlFormat) {
		this.jxlFormat = jxlFormat;
	}
	
	public String getCellClass() {
		return cellClass;
	}
	public void setCellClass(String cellClass) {
		this.cellClass = cellClass;
	}
	@Override
	public String toString() {
		if(cellValue==null){
			return "";
		}else{
			if("java.util.Date".equals(cellValue.getClass().getName())){
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String dateString = formatter.format(cellValue);
				return dateString;
			}
			return cellValue.toString();
		}
	}
}
