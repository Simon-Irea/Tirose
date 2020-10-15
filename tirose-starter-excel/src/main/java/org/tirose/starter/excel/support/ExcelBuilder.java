package org.tirose.starter.excel.support;

import org.tirose.starter.excel.constant.ExcelStyleEnum;

public class ExcelBuilder {
	/**
	 * 表格样式
	 */
	private ExcelStyleEnum excelStyleEnum = ExcelStyleEnum.GENERAL;

	/**
	 * 自动行高
	 */
	private Boolean autoLineHeight = false;

	/**
	 * 设置样式
	 * @param excelStyleEnum
	 * @return
	 */
	public ExcelBuilder style(ExcelStyleEnum excelStyleEnum) {
		this.excelStyleEnum = excelStyleEnum;
		return this;
	}

	/**
	 * 自动行高
	 * @return
	 */
	public ExcelBuilder autoLineHeight() {
		this.autoLineHeight = true;
		return this;
	}

	public  ExcelSupport builder() {
		ExcelSupport excelSupport = new ExcelSupport();
		excelSupport.setExcelStyleEnum(excelStyleEnum);
		excelSupport.setAutoLineHeight(autoLineHeight);
		return new ExcelSupport();
	}
}
