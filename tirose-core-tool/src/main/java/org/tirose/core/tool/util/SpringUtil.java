package org.tirose.core.tool.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.tirose.core.exception.exception.SpringInitializeException;

import javax.xml.ws.Service;

/**
 * @Auther : 毛一凡
 * @Email : 349676014@qq.com
 * @Date : 2020/7/3
 */
@Slf4j
public class SpringUtil implements ApplicationContextAware {

	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}

	public static void publishEvent(ApplicationEvent event) {
		if (context == null) {
			throw new SpringInitializeException("SpringUtil容器注入失败");
		}
		try {
			context.publishEvent(event);
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
	}

}
