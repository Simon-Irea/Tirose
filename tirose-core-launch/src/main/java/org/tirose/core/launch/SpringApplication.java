package org.tirose.core.launch;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.*;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.tirose.core.launch.constant.AppConstant;
import org.tirose.core.launch.constant.NacosConstant;
import org.tirose.core.launch.service.DisasterService;
import org.tirose.core.launch.service.LauncherService;
import org.tirose.core.launch.service.RegistryService;
import org.tirose.core.tool.util.ApplicationContextUtil;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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

		Assert.hasText(appName, "[appName]服务名不能为空");

		// 读取环境变量，使用spring boot的规则
		ConfigurableEnvironment environment = new StandardEnvironment();

		// 获取属性源
		MutablePropertySources propertySources = environment.getPropertySources();

		propertySources.addFirst(new SimpleCommandLinePropertySource(args));
		propertySources.addLast(new MapPropertySource(StandardEnvironment.SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME, environment.getSystemProperties()));
		propertySources.addLast(new SystemEnvironmentPropertySource(StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME, environment.getSystemEnvironment()));
		// 获取配置的环境变量
		String[] activeProfiles = environment.getActiveProfiles();
		// 判断环境:dev、test、prod
		List<String> profiles = Arrays.asList(activeProfiles);
		// 当前使用
		List<String> activeProfileList = new ArrayList<>(profiles);

		// 实现一个函数式接口
		Function<Object[], String> joinFun = StringUtils::arrayToCommaDelimitedString;

		// 建造者模式
		SpringApplicationBuilder builder = new SpringApplicationBuilder(source);
		String profile;
		if (activeProfileList.isEmpty()) {
			// 默认dev开发
			profile = AppConstant.DEV_CODE;
			activeProfileList.add(profile);
			builder.profiles(profile);
		} else if (activeProfileList.size() == 1) {
			profile = activeProfileList.get(0);
		} else {
			// 同时存在dev、test、prod环境时
			throw new RuntimeException("同时存在环境变量:[" + StringUtils.arrayToCommaDelimitedString(activeProfiles) + "]");
		}
		String startJarPath = SpringApplication.class.getResource("/").getPath().split("!")[0];
		String activePros = joinFun.apply(activeProfileList.toArray());
		System.out.println(String.format("----启动中，读取到的环境变量:[%s]，jar地址:[%s]----", activePros, startJarPath));

		Properties props = System.getProperties();
		// 服务名称
		props.setProperty("spring.application.name", appName);
		// 设置环境
		props.setProperty("spring.profiles.active", profile);
		// 版本信息
		props.setProperty("info.version", AppConstant.APPLICATION_VERSION);
		// 描述信息
		props.setProperty("info.desc", appName);
		// 开发环境
		props.setProperty("blade.env", profile);
		props.setProperty("blade.name", appName);

		// 判断是否为本地环境
		props.setProperty("blade.is-local", String.valueOf(isLocalDev()));

		props.setProperty("blade.dev-mode", profile.equals(AppConstant.PROD_CODE) ? "false" : "true");
		// 服务版本信息
		props.setProperty("blade.service.version", AppConstant.APPLICATION_VERSION);

		// 允许同名的bean覆盖
		props.setProperty("spring.main.allow-bean-definition-overriding", "true");

		ApplicationContext context = ApplicationContextUtil.getApplicationContext();

		RegistryService registryService = context.getBean("registryService", RegistryService.class);

		//配置默认的注册中心参数
		registryService.registryConfig(builder, appName, profile, isLocalDev());

		DisasterService disasterService = context.getBean("disasterService", DisasterService.class);

		//配置默认的容灾中间件参数
		disasterService.DisasterConfig(builder, appName, profile, isLocalDev());

		// 加载自定义组件
		List<LauncherService> launcherList = new ArrayList<>();
		ServiceLoader.load(LauncherService.class).forEach(launcherList::add);
		launcherList.stream().sorted(Comparator.comparing(LauncherService::getOrder)).collect(Collectors.toList())
			.forEach(launcherService -> launcherService.launcher(builder, appName, profile, isLocalDev()));
		return builder;
	}

	/**
	 * 判断是否为本地开发环境
	 *
	 * @return boolean
	 */
	public static boolean isLocalDev() {
		String osName = System.getProperty("os.name");
		return StringUtils.hasText(osName) && !(AppConstant.OS_NAME_LINUX.equals(osName.toUpperCase()));
	}
}
