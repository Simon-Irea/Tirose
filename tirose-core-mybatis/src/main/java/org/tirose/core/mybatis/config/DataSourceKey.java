package org.tirose.core.mybatis.config;

/**
 * @author pjy
 * @date 2020-05-05
 * 注意: 新增数据源需要新增属性
 */
public enum DataSourceKey {
    DB_FIRST("first"),
    DB_SECOND("second"),
    ;
    private String key;
    DataSourceKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
