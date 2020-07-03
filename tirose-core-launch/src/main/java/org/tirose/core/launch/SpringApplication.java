package org.tirose.core.launch;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Auther : 毛一凡
 * @Email : 349676014@qq.com
 * @Date : 2020/7/3
 */
public class SpringApplication {
	public static ConfigurableApplicationContext run(String appName, Class source, String... args) {
		SpringApplicationBuilder builder = createSpringApplicationBuilder(appName, source, args);
		return builder.run(args);

	}

	private static SpringApplicationBuilder createSpringApplicationBuilder(String appName, Class source, String... args) {

		return new SpringApplicationBuilder(source);
	}
}
