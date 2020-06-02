package org.tirose.core.exception.exception;

import org.tirose.core.tool.api.IResultCode;
import org.tirose.core.tool.api.ResultCode;
import lombok.Getter;

/**
 * @Author : 毛一凡
 * @Date : Create in 11:34 2019/11/18
 */
public class ApiException extends RuntimeException {
	private static final long serialVersionUID = 2359767895161832954L;

	@Getter
	private final IResultCode resultCode;

	public ApiException(String message) {
		super(message);
		this.resultCode = ResultCode.UN_AUTHORIZED;
	}

	public ApiException(IResultCode resultCode) {
		super(resultCode.getMessage());
		this.resultCode = resultCode;
	}

	public ApiException(IResultCode resultCode, Throwable cause) {
		super(cause);
		this.resultCode = resultCode;
	}

	@Override
	public Throwable fillInStackTrace() {
		return this;
	}
}
