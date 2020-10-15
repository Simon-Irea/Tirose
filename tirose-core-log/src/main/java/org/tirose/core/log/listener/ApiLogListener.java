//package org.tirose.core.log.listener;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.event.EventListener;
//import org.springframework.core.annotation.Order;
//import org.springframework.scheduling.annotation.Async;
//import org.tirose.core.log.event.ApiLogEvent;
//
//import java.util.Map;
//
///**
// * @Auther : 毛一凡
// * @Email : 349676014@qq.com
// * @Date : 2020/7/2
// */
//@Configuration
//@Slf4j
//public class ApiLogListener {
//
//	@Async
//	@Order
//	@EventListener(ApiLogEvent.class)
//	public void saveApiLog(ApiLogEvent event) {
//		Map<String, Object> source = (Map)event.getSource();
//		LogApi logApi = (LogApi)source.get("log");
//		LogAbstractUtil.addOtherInfoToLog(logApi, this.bladeProperties, this.serverInfo);
//		this.logService.saveApiLog(logApi);
//	}
//}
