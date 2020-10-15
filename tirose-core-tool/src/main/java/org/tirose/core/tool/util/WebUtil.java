package org.tirose.core.tool.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;
import org.tirose.core.tool.exception.SpringInitializeException;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther : 毛一凡
 * @Email : 349676014@qq.com
 * @Date : 2020/7/3
 */
public class WebUtil extends WebUtils {

	/**
	 * 获取HttpServletRequest请求
	 * @return
	 */
	public static HttpServletRequest getHttpServletRequest() {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (requestAttributes == null) {
			throw new SpringInitializeException("获取HttpServletRequest异常");
		}
		HttpServletRequest request = requestAttributes.getRequest();
		return request;
	}
}
