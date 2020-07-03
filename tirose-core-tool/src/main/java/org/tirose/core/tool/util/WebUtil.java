package org.tirose.core.tool.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

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

		}
		HttpServletRequest request = requestAttributes.getRequest();
		return request;
	}
}
