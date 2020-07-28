/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.tirose.core.boot.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.DispatcherServlet;
import org.tirose.core.tool.api.ApiResponse;
import org.tirose.core.tool.exception.SecureException;
import org.tirose.core.tool.exception.ServiceException;

import javax.servlet.Servlet;

/**
 * 未知异常转译和发送，方便监听，对未知异常统一处理。Order 排序优先级低
 *
 * @author Chill
 */
@Slf4j
@Order
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass({ Servlet.class, DispatcherServlet.class })
@RestControllerAdvice
public class UnknownRestExceptionTranslator {

	@ExceptionHandler(ServiceException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiResponse handleError(ServiceException e) {
		log.error("业务异常", e);
		return ApiResponse.fail(e.getResultCode(), e.getMessage());
	}

	@ExceptionHandler(SecureException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ApiResponse handleError(SecureException e) {
		log.error("认证异常", e);
		return ApiResponse.fail(e.getResultCode(), e.getMessage());
	}

}
