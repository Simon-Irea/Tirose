package org.tirose.core.mybatis.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author pjy
 * @date 2020-05-05
 * 1. 注意:  mybatis为lazy加载,当第一次访问数据时底层
 * 会重复访问AbstractRoutingDataSource的determineTargetDataSource方法,调试阶段不要介意
 * 2. 新增数据源需要新增数据源bean对象以及整合sqlsession
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        // 从自定义的位置获取数据源标识
        return DynamicDataSourceHolder.getDataSource();
    }
}
