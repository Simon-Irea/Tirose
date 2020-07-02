package org.tirose.core.log.event;

import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * @Auther : 毛一凡
 * @Email : 349676014@qq.com
 * @Date : 2020/7/2
 */
public class ApiLogEvent extends ApplicationEvent {

	/**
	 * Create a new {@code ApplicationEvent}.
	 *
	 * @param source the object on which the event initially occurred or with
	 *               which the event is associated (never {@code null})
	 */
	public ApiLogEvent(Map<String, Object> source) {
		super(source);
	}
}
