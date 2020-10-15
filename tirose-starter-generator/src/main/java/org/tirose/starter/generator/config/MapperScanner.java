package org.tirose.starter.generator.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
@MapperScan("org.tirose.starter.generator.mapper")
public class MapperScanner {
}
