package org.tirose.core.file.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Auth:Jimmy
 * @Email: 349676014@qq.com
 * @Date: 2020/7/5 21:16
 **/
@ConfigurationProperties("fdfs")
public class FdfsProperties {

	public String reqHost;

	//分布式文件系统端口
	public String reqPort;
}
