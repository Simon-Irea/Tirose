package org.tirose.starter.excel.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.tirose.starter.excel.bean.ExcelData;
import org.tirose.starter.excel.style.ExcelStyle;
import org.tirose.starter.excel.support.ExcelBuilder;
import org.tirose.starter.excel.support.ExcelFactory;
import org.tirose.starter.excel.support.ExcelFunction;
import org.tirose.starter.excel.support.ExcelSupport;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExcelUtil {

	private static ExcelSupport excelSupport = new ExcelBuilder().builder();

	/**
	 * 将excel转化为数据
	 * @param file
	 * @param fields
	 * @param cls
	 * @param excelFunction
	 * @param <T>
	 * @return
	 */
	private <T> List<T> doParseData(MultipartFile file, String[] fields, Class<T> cls, ExcelFunction excelFunction ) {
		ExcelFactory factory = new ExcelFactory();

		Workbook wb = factory.getWorkbook(file);

		List<T> datas = new ArrayList<T>();     //先保存到临时表里面，再处理到正式表，最后清空临时表

		Sheet sheet = wb.getSheetAt(0);
		int lastRowNum = sheet.getLastRowNum();
		int count = sheet.getLastRowNum() + 1;

		//策略模式，对不同的对象具有不同的验证策略
		excelFunction.apply(sheet, fields);

		for (int i = 1; i < count; i++) {
			Row row = sheet.getRow(i);
			// 不再判断每一行单元格格式，默认已第一行为准
            /*int lastCellNum = row.getLastCellNum();
            int filedLength = fields.length;

            if (lastCellNum != filedLength) {
                throw new Exception("表格格式不正确")；
            }*/
			if(row != null) {
				try {
					T data = (T) cls.newInstance();
					for (int j = 0; j < fields.length; j++) {
						String filedName = fields[j];
						if (null == filedName || filedName.equals("")) {
							continue;
						}
						Field filed = cls.getDeclaredField(filedName);      //获取字段名
						filed.setAccessible(true);
						Cell cell = row.getCell(j);                 //获取单元格

						String fieldTypeName = filed.getType().getSimpleName();
						if(cell != null) {
							if (fieldTypeName.equals("Date")) {
								filed.set(data, cell.getDateCellValue());
							} else {
								cell.setCellType(Cell.CELL_TYPE_STRING);
								// 避免空值时转换异常，填写非法不处理
								String value = StringUtils.isBlank(cell.getStringCellValue()) ? "0" : cell.getStringCellValue();
								if (fieldTypeName.equals("Double")) {
									filed.set(data, Double.valueOf(value));
								} else if (fieldTypeName.equals("Integer")) {
									filed.set(data, Integer.valueOf(value));
								} else if (fieldTypeName.equals("Short")) {
									filed.set(data, Short.valueOf(value));
								} else if (fieldTypeName.equals("Long")) {
									filed.set(data, Long.valueOf(value));
								} else if (fieldTypeName.equals("Float")) {
									filed.set(data, Float.valueOf(value));
								} else if (fieldTypeName.equals("Byte")) {
									filed.set(data, Byte.valueOf(value));
								} else if (fieldTypeName.equals("BigDecimal")) {
									filed.set(data, new BigDecimal(value));
								} else {
									filed.set(data, cell.getStringCellValue());
								}
							}
						}

					}
					datas.add(data);
				} catch (Exception e) {
					throw new RuntimeException();
				}
			}

		}
		return datas;
	}


	/**
	 * 创建excel
	 * @param excelData
	 * @return
	 */
	public static Workbook createWorkBook(ExcelData excelData) {

		return excelSupport.createWorkBook(excelData);

	}

}
