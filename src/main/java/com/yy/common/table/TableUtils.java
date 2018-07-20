package com.yy.common.table;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yy.common.table.CellModel.CellStyle;


public class TableUtils {
	private static final int DEFAULT_EXCEL_COL_WIDTH = 20;
	private static Logger log = LoggerFactory.getLogger(TableUtils.class);
	/**
	 * 生成报表Excel文件
	 * @author wzw 2015年1月4日
	 * @param reportModel
	 * @return
	 */
	public static WritableWorkbook getExcelFromTableModel(TableModel tableModel,File file){
		WritableWorkbook wb = null;
		WritableSheet sheet = null;
		try {
			wb = Workbook.createWorkbook(file);
		} catch (IOException e) {
			log.error("创建表格时出现IO异常："+e.getMessage());
			e.printStackTrace();
		}
		//写sheet
		String title = tableModel.getTableName();
		String sheetName = title==null?"sheet1":title.toString();
		sheet=wb.createSheet(sheetName, 0);
		
		writeSheet(sheet,tableModel);
		
		return wb;
	}
	public static WritableWorkbook getExcelFromTableList(List<TableModel> tableList,File file){
		WritableWorkbook wb = null;
		
		try {
			wb = Workbook.createWorkbook(file);
		} catch (IOException e) {
			log.error("创建表格时出现IO异常："+e.getMessage());
			e.printStackTrace();
		}
		for(int n=0;n<tableList.size();n++){
			TableModel tableModel =  tableList.get(n);
			//写sheet
			String title = tableModel.getTableName();
			String sheetName = title==null?"sheet1":title.toString();
			WritableSheet sheet =wb.createSheet(sheetName, n);
			
			writeSheet(sheet,tableModel);
			
		}
		return wb;
	}
	public static void writeSheet(WritableSheet sheet,TableModel tableModel){
		Set<String> toMergeCellPositions = new HashSet<String>();
		int rowNum = 0;
		List<RowModel> rowList = tableModel.getRowList();
		rowNum = writeRowList(rowList,toMergeCellPositions,sheet,rowNum);
		int colNum = sheet.getColumns();
		for(int i=0;i<colNum;i++){
			sheet.setColumnView(i, DEFAULT_EXCEL_COL_WIDTH);
		}
	}
	public static int writeRowList(List<RowModel> rowList,
			Set<String> toMergeCellPositions, WritableSheet sheet,int rowNum) {
		if(rowList!=null){
			for(int i=0;i<rowList.size();i++){
				writeRow(rowList.get(i),toMergeCellPositions,sheet,rowNum);
				rowNum++;
			}
		}
		return rowNum;
		
	}

	public static void writeRow(RowModel rowModel,Set<String> toMergeCellPositions, WritableSheet sheet,int beginNum) {
		List<CellModel> cellList = rowModel.getCellList();
		int colPosition = 0;
		for(int i=0;i<cellList.size();i++){
			colPosition = writeCell(cellList.get(i),toMergeCellPositions,sheet, beginNum, colPosition);
		}
		setRowStyle(sheet,rowModel);
	}

	public static int writeCell(CellModel cellModel, Set<String> toMergeCellPositions,
			WritableSheet sheet,int rowNum,int colPosition) {
		try {
			WritableCellFormat format=cellModel.getJxlFormat();
			if(format==null){
				format = getCellFormat(cellModel.getCellStyle());
			}
			WritableCell cell = null;
			//如果遇到存在的单元格则跳过，不覆盖。（考虑的是合并单元格会插入空的单元格）
			while(toMergeCellPositions.contains("col:"+colPosition+"row:"+rowNum)){
				colPosition++;
			}
			cell = new Label(colPosition,rowNum,cellModel.toString(),format);
			sheet.addCell(cell);
			
			//合并单元格
			int colspan = 1;
			if(cellModel.getColspan()!=null&&cellModel.getColspan()>1){
				colspan = cellModel.getColspan();
			}
			int rowspan = 1;
			if(cellModel.getRowspan()!=null&&cellModel.getRowspan()>1){
				rowspan = cellModel.getRowspan();
			}
			if(colspan>1||rowspan>1){
				addNullMergeCell(sheet,colPosition,rowNum, rowspan,colspan,format,toMergeCellPositions);
				sheet.mergeCells(colPosition, rowNum, colPosition+colspan-1, rowNum+rowspan-1);
				
			}
			return colPosition+colspan;
		} catch (RowsExceededException e) {
			log.error("jxl绘制单元格时出错");
			e.printStackTrace();
		} catch (WriteException e) {
			log.error("jxl绘制单元格时出错");
			e.printStackTrace();
		}
		return colPosition;
	}
	/**
	 * 添加空的单元格被合并
	 * @author wzw 2015年1月6日
	 * @param sheet
	 * @param colPosition
	 * @param rowNum
	 * @param rowspan
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	private static void addNullMergeCell(WritableSheet sheet ,int colPosition, int rowNum
			, int rowspan,int colspan,WritableCellFormat format,Set<String> toMergeCellPositions) throws RowsExceededException, WriteException {
		for(int i=0;i<rowspan;i++){
			for(int j=0;j<colspan;j++){
				if(i==0&&j==0){
					continue;
				}
				toMergeCellPositions.add("col:"+(colPosition+j)+"row:"+(rowNum+i));
			}
		}
	}

	public static WritableCellFormat getCellFormat(CellStyle cellStyle ) {
		if(cellStyle==null){
			return getDefaultColumnFormat();
		}
		switch (cellStyle) {
		case title :
			return getTitleColumnFormat();
		case tag :	
			return getTagColumnFormat();
		case head :
			return getDefaultHeadFormat();
		case normal :
			return getDefaultColumnFormat();
		case foot :
			return getFootColumnFormat();
		case tag_center :	
			return getTagCenterColumnFormat();
		case tag_right :	
			return getTagRightColumnFormat();
		}
		return getDefaultColumnFormat();
	}

	public static WritableCellFormat getTagRightColumnFormat() {
		WritableFont font= new WritableFont(WritableFont.ARIAL);
		WritableCellFormat fomat=new WritableCellFormat();
		try {
			fomat.setAlignment(Alignment.RIGHT);
			fomat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			fomat.setFont(font);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return fomat;
	}
	public static WritableCellFormat getTagCenterColumnFormat() {
		WritableFont font= new WritableFont(WritableFont.ARIAL);
		WritableCellFormat fomat=new WritableCellFormat();
		try {
			fomat.setAlignment(Alignment.CENTRE);
			fomat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			fomat.setFont(font);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return fomat;
	}
	public static void setRowStyle(WritableSheet sheet, RowModel rowModel) {
		
	}

	/**
	 * 表头的默认样式
	 * @return
	 */
	public static WritableCellFormat getDefaultHeadFormat(){
		WritableFont font= new WritableFont(WritableFont.TIMES);
		WritableCellFormat fomat=new WritableCellFormat();
		try {
			fomat.setAlignment(Alignment.CENTRE);
			fomat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			fomat.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN,Colour.BLACK);
			font.setBoldStyle(WritableFont.BOLD);
			font.setPointSize(12);
			fomat.setFont(font);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return fomat;
	}
	
	/**
	 * 列名的默认样式
	 * @return
	 */
	public static WritableCellFormat getDefaultColumnFormat(){
		WritableFont font= new WritableFont(WritableFont.ARIAL);
		WritableCellFormat fomat=new WritableCellFormat();
		try {
			fomat.setAlignment(Alignment.CENTRE);
			fomat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			fomat.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN,Colour.BLACK);
			fomat.setFont(font);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return fomat;
	}
	/**
	 * 列名的必填项样式
	 * @return
	 */
	public static WritableCellFormat getRequiredColumnFormat(){
		WritableFont font= new WritableFont(WritableFont.ARIAL);
		WritableCellFormat fomat=new WritableCellFormat();
		try {
			fomat.setAlignment(Alignment.CENTRE);
			fomat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			fomat.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN,Colour.RED);
			font.setColour(Colour.RED);
			fomat.setFont(font);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return fomat;
	}
	/**
	 * 列名的默认样式
	 * @return
	 */
	public static WritableCellFormat getDefaultRowFormat(){
		WritableCellFormat fomat=new WritableCellFormat();
		try {
			fomat.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN,Colour.BLACK);
			fomat.setAlignment(Alignment.LEFT);
			fomat.setVerticalAlignment(VerticalAlignment.CENTRE);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return fomat;
	}
	private static WritableCellFormat getFootColumnFormat() {
		return getTagColumnFormat();
	}

	private static WritableCellFormat getTagColumnFormat() {
		WritableFont font= new WritableFont(WritableFont.ARIAL);
		WritableCellFormat fomat=new WritableCellFormat();
		try {
			fomat.setAlignment(Alignment.LEFT);
			fomat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			fomat.setFont(font);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return fomat;
	}

	private static WritableCellFormat getTitleColumnFormat() {
		WritableFont font= new WritableFont(WritableFont.TIMES);
		WritableCellFormat fomat=new WritableCellFormat();
		try {
			fomat.setAlignment(Alignment.CENTRE);
			fomat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			font.setBoldStyle(WritableFont.BOLD);
			font.setPointSize(16);
			fomat.setFont(font);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return fomat;
	}
	/**
	 * 根据数据生成tableModel
	 * 说明：data格式：tdData/@/tdData/@/tdData
	 * tdData:cellValue/#/colspan/#/rowspan/#/cellStyle
	 * @author wzw 2015年1月15日
	 * @param data table的一行。
	 * @return
	 */
	public static TableModel getTableByData(String[] data) {
		TableModel table  = new TableModel();
		List<RowModel> rows = new ArrayList<RowModel>();
		for(String rowData : data){
			RowModel row = new RowModel();
			List<CellModel> cellList = new ArrayList<CellModel>();
			
			String[] cells = rowData.split("/@/");
			if(cells!=null&&cells.length>0){
				for(String cellData : cells){
					CellModel cellModel = new CellModel();
					String[] cellDatas = cellData.split("/#/");
					cellModel.setCellValue(cellDatas[0]);
					cellModel.setColspan(Integer.parseInt(cellDatas[1]));
					cellModel.setRowspan(Integer.parseInt(cellDatas[2]));
					cellModel.setCellStyle(CellStyle.valueOf(cellDatas[3]));
					cellList.add(cellModel);
				}
			}
			row.setCellList(cellList);
			rows.add(row);
		}
		table.setRowList(rows);
		return table;
	}
}
