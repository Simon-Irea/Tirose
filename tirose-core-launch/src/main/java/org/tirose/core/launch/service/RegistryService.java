package org.tirose.core.launch.service;

import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @Auth:Jimmy
 * @Email: 349676014@qq.com
 * @Date: 2020/7/5 20:24
 **/
public interface RegistryService extends LauncherService {

	void registryConfig(SpringApplicationBuilder builder, String appName, String profile, boolean isLocalDev);

}
