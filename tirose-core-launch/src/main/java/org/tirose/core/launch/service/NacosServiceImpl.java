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
 * @Date: 2020/7/5 16:17
 **/
public class NacosServiceImpl implements RegistryService {


	@Override
	public void registryConfig(SpringApplicationBuilder builder, String appName, String profile, boolean isLocalDev) {
		Properties props = System.getProperties();
		// nacos配置文件项目
		props.setProperty("spring.cloud.nacos.config.prefix", NacosConstant.NACOS_CONFIG_PREFIX);
		props.setProperty("spring.cloud.nacos.config.file-extension", NacosConstant.NACOS_CONFIG_FORMAT);
		props.setProperty("spring.cloud.alibaba.seata.tx-service-group", appName.concat(NacosConstant.NACOS_GROUP_SUFFIX));
		props.setProperty("spring.cloud.nacos.discovery.server-addr", NacosConstant.NACOS_ADDR);
		props.setProperty("spring.cloud.nacos.config.server-addr", NacosConstant.NACOS_ADDR);
		props.setProperty("spring.cloud.sentinel.transport.dashboard", SentinelConstant.SENTINEL_ADDR);
	}

	@Override
	public void launcher(SpringApplicationBuilder builder, String appName, String profile, boolean isLocalDev) {

	}
}
