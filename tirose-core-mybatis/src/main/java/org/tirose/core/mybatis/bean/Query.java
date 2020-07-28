package org.tirose.core.mybatis.bean;

public class Query {
	/**
	 * 页号
	 */
	private Integer page;
	/**
	 * 每页的大小
	 */
	private Integer pageSize;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
