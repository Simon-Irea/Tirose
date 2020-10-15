package org.tirose.starter.excel.support;

import org.apache.poi.ss.usermodel.Sheet;

@FunctionalInterface
public interface ExcelFunction<T,R> {

	void apply(T t, R r);
}
