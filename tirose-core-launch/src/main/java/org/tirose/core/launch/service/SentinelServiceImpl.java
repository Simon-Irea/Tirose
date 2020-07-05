package org.tirose.core.launch.service;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.tirose.core.auto.annotation.AutoService;
import org.tirose.core.launch.constant.NacosConstant;
import org.tirose.core.launch.constant.SentinelConstant;
import org.tirose.core.launch.service.LauncherService;

import java.util.Properties;

/**
 * @Auth:Jimmy
 * @Email: 349676014@qq.com
 * @Date: 2020/7/5 16:49
 **/
public class SentinelServiceImpl implements DisasterService {
	@Override
	public void DisasterConfig(SpringApplicationBuilder builder, String appName, String profile, boolean isLocalDev) {
		Properties props = System.getProperties();
		// sentinel配置文件项目
		props.setProperty("spring.cloud.sentinel.transport.dashboard", SentinelConstant.SENTINEL_ADDR);
	}

	@Override
	public void launcher(SpringApplicationBuilder builder, String appName, String profile, boolean isLocalDev) {

	}
}
