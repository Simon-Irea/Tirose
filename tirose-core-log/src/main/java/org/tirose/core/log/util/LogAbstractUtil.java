//package org.tirose.core.log.util;
//
//import com.sun.jndi.toolkit.url.UrlUtil;
//import org.tirose.core.tool.util.ObjectUtil;
//import org.tirose.core.tool.util.WebUtil;
//
//import javax.servlet.http.HttpServletRequest;
//
//public class LogAbstractUtil {
//	public LogAbstractUtil() {
//	}
//
//	public static void addRequestInfoToLog(HttpServletRequest request, LogAbstract logAbstract) {
//		if (ObjectUtil.isNotEmpty(request)) {
//			logAbstract.setRemoteIp(WebUtil.getIP(request));
//			logAbstract.setUserAgent(request.getHeader("user-agent"));
//			logAbstract.setRequestUri(UrlUtil.getPath(request.getRequestURI()));
//			logAbstract.setMethod(request.getMethod());
//			logAbstract.setParams(WebUtil.getRequestContent(request));
//			logAbstract.setCreateBy(AuthUtil.getUserAccount(request));
//		}
//
//	}
//
//	public static void addOtherInfoToLog(LogAbstract logAbstract, BladeProperties bladeProperties, ServerInfo serverInfo) {
//		logAbstract.setServiceId(bladeProperties.getName());
//		logAbstract.setServerHost(serverInfo.getHostName());
//		logAbstract.setServerIp(serverInfo.getIpWithPort());
//		logAbstract.setEnv(bladeProperties.getEnv());
//		logAbstract.setCreateTime(DateUtil.now());
//		if (logAbstract.getParams() == null) {
//			logAbstract.setParams("");
//		}
//
//	}
//}
