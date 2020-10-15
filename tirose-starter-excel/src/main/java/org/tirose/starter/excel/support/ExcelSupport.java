package org.tirose.starter.excel.support;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.tirose.starter.excel.bean.ExcelData;
import org.tirose.starter.excel.constant.ExcelStyleEnum;
import org.tirose.starter.excel.style.ExcelStyle;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

@Data
public class ExcelSupport {

	private static ExcelFactory factory = new ExcelFactory();

	/**
	 * 表格样式
	 */
	private ExcelStyleEnum excelStyleEnum = ExcelStyleEnum.GENERAL;

	/**
	 * 自动行高
	 */
	private Boolean autoLineHeight = false;


	/**
	 * 创建excel
	 * @param excelData
	 * @return
	 */
	public Workbook createWorkBook(ExcelData excelData) {
		// 创建excel工作簿
		Workbook wb = new XSSFWorkbook();
		// 创建第一个sheet（页），并命名
		Sheet sheet = wb.createSheet("sheet1");
		// 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
		//sheet.setColumnWidth((short) 1, (short) (35.7 * 150));

		// 创建第一行
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);

		ExcelStyle excelStyle = factory.cellStyle(excelStyleEnum);

		CellStyle headCellStyle = excelStyle.createHeadCellStyle(wb);

		CellStyle cellStyle = excelStyle.createHeadCellStyle(wb);

		//设置列名
		for(int i = 0; i < excelData.getTitles().size(); i++){
			cell = row.createCell(i);
			cell.setCellValue(excelData.getTitles().get(i));
			cell.setCellStyle(headCellStyle);
			// 设置每一行单元格宽度
			sheet.setColumnWidth((short) i, (short) (35.7 * 120));
		}

		//如果有数据的话，填充数据
		if (excelData.getRows() != null) {
			//设置每行每列的值
			for (int i = 0; i < excelData.getRows().size(); i++) {
				// Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
				// 创建一行，在页sheet上
				row = sheet.createRow(i+1);
				// 在row行上创建一个方格
				Map<String, Object> temp = (Map<String, Object>) excelData.getRows().get(i);
				for(int j = 0;j < excelData.getRows().get(i).size();j++){
					cell = row.createCell(j);
					cell.setCellValue(excelData.getRows().get(i).get(j) == null?" ": excelData.getRows().get(i).get(j).toString());
					cell.setCellStyle(cellStyle);
				}
				//map数据清理
				temp.clear();
			}
		}

		return wb;
	}

	/***
	 * 将数据转换成excel表格
	 * @param list
	 * @param cls
	 * @param columnNames
	 * @param fieldsName
	 * @return
	 * @throws Exception
	 */
	public <T> Workbook createWorkBook(List<T> list, Class<T> cls, String columnNames[], String[] fieldsName) throws Exception {

		Workbook wb = new XSSFWorkbook();           // 创建excel工作簿
		Sheet sheet = wb.createSheet("sheet1");    // 创建第一个sheet（页），并命名
		Row row = sheet.createRow(0);                // 创建第一行
		Cell cell = row.createCell(0);

		// 创建两种单元格格式
		ExcelStyle excelStyle = factory.cellStyle(excelStyleEnum);

		CellStyle headCellStyle = excelStyle.createHeadCellStyle(wb);

		CellStyle cellStyle = excelStyle.createHeadCellStyle(wb);

		//设置列名
		for (int i = 0; i < columnNames.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(columnNames[i]);
			cell.setCellStyle(headCellStyle);
			// 设置每一行单元格宽度
			sheet.setColumnWidth((short) i, (short) (35.7 * 120));
		}

		Map<String, Field> fieldMap = new HashMap<>() ;
		while (cls != null) {//当父类为null的时候说明到达了最上层的父类(Object类).
			Field[] declaredFields = cls.getDeclaredFields();
			for (Field declaredField : declaredFields) {
				fieldMap.put(declaredField.getName(),declaredField);
			}
			cls = (Class) cls.getSuperclass(); //得到父类,然后赋给自己
		}

		//设置每行每列的值
		for (int i = 0; i < list.size(); i++) {
			// Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
			// 创建一行，在页sheet上
			row = sheet.createRow(i+1);
			// 在row行上创建一个方格
			Map<String, Object> temp = (Map<String, Object>) list.get(i);
			for (int j = 0; j < fieldsName.length; j++) {
				cell = row.createCell(j);
				String value = temp.get(fieldsName[j]) == null ? " " : temp.get(fieldsName[j]).toString();
				cell.setCellValue(value);
				cell.setCellStyle(cellStyle);
			}
			//map数据清理
			temp.clear();
		}
		// list数据清理
		list.clear();
		// list置空
		list = null;
		return wb;
	}







}
