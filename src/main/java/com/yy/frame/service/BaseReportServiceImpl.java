package com.yy.frame.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.hibernate.service.spi.ServiceException;

import com.yy.common.dao.DbUtilsDAO;
import com.yy.common.exception.DAOException;
import com.yy.common.table.CellModel;
import com.yy.common.table.RowModel;
import com.yy.common.table.TableModel;
import com.yy.common.table.TableUtils;
import com.yy.common.utils.FileUtils;
import com.yy.common.utils.Reflections;
import com.yy.frame.dao.IBaseDAO;
import com.yy.frame.entity.BaseReportModel;

/**
 * 报表service-基类
 * @author xuechen
 * @date 2016年8月23日
 */

public abstract class BaseReportServiceImpl<T extends BaseReportModel>{
	private Class<T> persistentClass;//泛型类class
	protected abstract DbUtilsDAO getDAO();//获取dao
	public abstract String[] getCellTitles();//列标题
	public abstract String[] getCellValues();//列字段名
	public abstract String getTitle();//标题
	public abstract String getSql(Map<String, String[]> searchMap);//获取查询sql
	public BaseReportServiceImpl(){
		persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}
	public int getMaxTotal(){
		return 5000;
	}
	//获取数据
	public List<T> getReportDate(Map<String, String[]> searchMap){
		List<T> list = null;
		try {
			list = getDAO().find(persistentClass,getSql(searchMap));
			afterGetReportDate(list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("报表查询错误!");
		}
		return list; 
	}
	//超过5000调数据获取前5000条
	public List<T> getReportPageDate(Map<String, String[]> searchMap){
		List<T> list = null;
		try {
			list = getDAO().findPage(persistentClass, getSql(searchMap), 0, getMaxTotal(), null);
			afterGetReportDate(list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("报表查询错误!");
		}
		return list; 
	}
	//返回数据之后
	public void afterGetReportDate(List<T> list) throws Exception{
		
	}
	//获取总数
	public int getTotal(Map<String, String[]> searchMap){
		int total=0;
		String sql = "select count(1) from(" + getSql(searchMap) + ") a";
		try {
			Object o = getDAO().findBy(sql, 1);
			total = o == null ? 0 : Integer.parseInt(o.toString());
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException("报表查询错误!");
		}
		return total;
	}
	public void doExport(HttpServletRequest request,
			HttpServletResponse response, List<T> list) {
		TableModel table = drawTable(list);
		if(table!=null){
			try {
				File tempFile = FileUtils.createTempFile();
				WritableWorkbook workbook = TableUtils.getExcelFromTableModel(table, tempFile);
				workbook.write();
				workbook.close();
				FileUtils.writeFileToResponse(tempFile, response,table.getTableName()+".xlsx",request);
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
		}
		
	}
	//画表格
	public TableModel drawTable(List<T> list) {
		TableModel table  = new TableModel();
		List<RowModel> rows = new ArrayList<RowModel>();
		RowModel topRow = null;
		WritableCellFormat cellFormat = new WritableCellFormat();
		WritableFont font = new WritableFont(WritableFont.ARIAL,12,WritableFont.BOLD);  
		List<CellModel> topCellList = null;
		CellModel cellTitle = null;
		
		try {
			cellFormat.setFont(font);
			cellFormat.setAlignment(Alignment.CENTRE);  
			cellFormat.setBackground(Colour.PALE_BLUE);
			
			String[] cellTitles = getCellTitles();
			String[] cellValues = getCellValues();
			
			//表名
			topRow = new RowModel();
			topCellList = new ArrayList<CellModel>();
			cellTitle = new CellModel();
			cellTitle.setColspan(getCellTitles().length);
			cellTitle.setCellValue(getTitle());
			cellTitle.setJxlFormat(cellFormat);
			topCellList.add(cellTitle);
			topRow.setCellList(topCellList);
			rows.add(topRow);
			
			//标题
			topRow = new RowModel();
			topCellList = new ArrayList<CellModel>();
			for (int i = 0; i < cellTitles.length; i++) {
				cellTitle =new CellModel();
				cellTitle.setCellValue(cellTitles[i]);
				cellTitle.setJxlFormat(cellFormat);
				topCellList.add(cellTitle);
			}
			topRow.setCellList(topCellList);
			rows.add(topRow);
			if(list!=null && list.size()>0){
				RowModel row = null;
				List<CellModel> cellList = null;
				CellModel cellRow = null;
				for (T o : list) {
					row = new RowModel();
					cellList = new ArrayList<CellModel>();
					for (int i = 0; i < cellValues.length; i++) {
						cellRow = new CellModel();
						cellRow.setCellValue(Reflections.invokeGetter(o, cellValues[i]));
						cellList.add(cellRow);
					}
					row.setCellList(cellList);
					rows.add(row);
				}
			}
			table.setRowList(rows);

			String title = getTitle();
			table.setTableName(title);
		} catch (WriteException e) {
			e.printStackTrace();
			throw new ServiceException("导出错误！");
		}
		
		return table;
	}
	
}
