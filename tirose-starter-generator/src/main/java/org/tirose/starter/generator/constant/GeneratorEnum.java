package org.tirose.starter.generator.constant;

/**
 * 枚举类
 */
public enum GeneratorEnum {
	MYBATIS("mybatis"), MYBATIS_PLUS("mybatisplus");
	GeneratorEnum(String name) {
		this.name = name;
	}
	private String name;

	public String getName() {
		return name;
	}
}
