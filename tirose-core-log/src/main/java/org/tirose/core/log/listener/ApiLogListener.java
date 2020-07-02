package org.tirose.core.log.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

/**
 * @Auther : 毛一凡
 * @Email : 349676014@qq.com
 * @Date : 2020/7/2
 */
@Configuration
@Slf4j
public class ApiLogListener {

	@Async
	@Order
	public void saveApiLog() {

	}
}
