package org.tirose.core.log.publisher;

import org.tirose.core.log.annotation.ApiLog;
import org.tirose.core.log.event.ApiLogEvent;
import org.tirose.core.tool.util.SpringUtil;
import org.tirose.core.tool.util.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 推送日志事件
 *
 * @see org.tirose.core.log.aspect.ApiLogAspect
 * @Auther : 毛一凡
 * @Email : 349676014@qq.com
 * @Date : 2020/7/3
 */
public class ApiLogPublisher {
	public static void publishEvent(String methodName, String className, ApiLog apiLog, long time) {
//		HttpServletRequest request = WebUtil.getHttpServletRequest();
//		//封装持久化数据
//		LogApi logApi = new LogApi();
//		logApi.setType("1");
//		logApi.setTitle(apiLog.value());
//		logApi.setTime(String.valueOf(time));
//		logApi.setMethodClass(methodClass);
//		logApi.setMethodName(methodName);
//		LogAbstractUtil.addRequestInfoToLog(request, logApi);
//		//封装事件
//		Map<String, Object> event = new HashMap(16);
//		event.put("log", logApi);
//		//推送事件
//		SpringUtil.publishEvent(new ApiLogEvent(event));
	}
}
