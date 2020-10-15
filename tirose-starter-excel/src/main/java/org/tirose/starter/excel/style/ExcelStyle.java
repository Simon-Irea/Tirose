package org.tirose.starter.excel.style;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

public interface ExcelStyle {

	/**
	 * 创建表头单元格格式
	 * @param workbook
	 * @return
	 */
	CellStyle createHeadCellStyle(Workbook workbook);

	/**
	 * 创建单元格格式
	 * @param workbook
	 * @return
	 */
	CellStyle createCellStyle(Workbook workbook);
}
