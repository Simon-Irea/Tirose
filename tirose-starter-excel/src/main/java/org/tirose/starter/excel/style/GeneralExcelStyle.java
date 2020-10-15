package org.tirose.starter.excel.style;

import org.apache.poi.ss.usermodel.*;

public class GeneralExcelStyle implements ExcelStyle {
	@Override
	public CellStyle createHeadCellStyle(Workbook workbook) {
		// 创建两种单元格格式
		CellStyle cs = workbook.createCellStyle();
		// 创建两种字体
		Font font = workbook.createFont();

		// 创建第一种字体样式（用于列名）
		font.setFontHeightInPoints((short) 10);
		font.setColor(IndexedColors.BLACK.getIndex());
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);

		// 设置第一种单元格的样式（用于列名）
		cs.setFont(font);
		cs.setBorderLeft(CellStyle.BORDER_THIN);
		cs.setBorderRight(CellStyle.BORDER_THIN);
		cs.setBorderTop(CellStyle.BORDER_THIN);
		cs.setBorderBottom(CellStyle.BORDER_THIN);
		// 设置水平居中样式
		cs.setAlignment(CellStyle.ALIGN_CENTER);
		// 设置垂直居中样式
		cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		return cs;
	}

	@Override
	public CellStyle createCellStyle(Workbook workbook)  {
		// 创建两种单元格格式
		CellStyle cs = workbook.createCellStyle();
		// 创建两种字体
		Font font = workbook.createFont();

		// 创建第一种字体样式（用于列名）
		font.setFontHeightInPoints((short) 10);
		font.setColor(IndexedColors.BLACK.getIndex());

		// 设置第一种单元格的样式（用于列名）
		cs.setFont(font);
		cs.setBorderLeft(CellStyle.BORDER_THIN);
		cs.setBorderRight(CellStyle.BORDER_THIN);
		cs.setBorderTop(CellStyle.BORDER_THIN);
		cs.setBorderBottom(CellStyle.BORDER_THIN);
		// 设置水平居中样式
		cs.setAlignment(CellStyle.ALIGN_CENTER);

		return cs;

	}


}
