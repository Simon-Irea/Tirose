package org.tirose.core.tool.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.tirose.core.boot.exception.SpringInitializeException;

/**
 * @Auther : 毛一凡
 * @Email : 349676014@qq.com
 * @Date : 2020/7/3
 */
@Slf4j
public class SpringUtil {

	public static void publishEvent(ApplicationEvent event) {
		try {
			ApplicationContextUtil.getApplicationContext().publishEvent(event);
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
	}

}
