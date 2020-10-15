package org.tirose.starter.excel.support;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.tirose.starter.excel.constant.ExcelStyleEnum;
import org.tirose.starter.excel.style.ExcelStyle;

import javax.swing.text.Style;
import java.io.IOException;
import java.io.InputStream;

public class ExcelFactory {


	public ExcelStyle cellStyle(ExcelStyleEnum excelStyleEnum) {
		ExcelStyle excelStyle = null;
		try {
			String enumName = excelStyleEnum.getClass().getName();
			String excelStyleName = enumName.substring(0,1).toUpperCase().concat(enumName.substring(1).toLowerCase()) + "ExcelStyle";
			Class<ExcelStyle> aClass = (Class<ExcelStyle>) Class.forName(excelStyleName);
			excelStyle = aClass.newInstance();
			return excelStyle;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

	}


	public Workbook getWorkbook(MultipartFile file) {
		try {
			Workbook wb = null;
			//文件扩展名
			String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
			InputStream inputStream = file.getInputStream();
			if (suffix.endsWith("xlsx")) {
				wb =  new XSSFWorkbook(inputStream);   // 操作Excel2007的版本，扩展名是.xlsx
			} else if (suffix.endsWith("xls")) {
				wb =  new HSSFWorkbook(inputStream);  // 操作Excel2003以前（包括2003）的版本，扩展名是.xls
			}
			if (wb == null) {
				throw new RuntimeException("文件格式不支持");
			}
			return wb;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("");
		}
	}

}
