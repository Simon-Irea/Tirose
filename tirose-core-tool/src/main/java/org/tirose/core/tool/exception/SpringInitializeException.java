package org.tirose.core.tool.exception;

import lombok.Getter;
import org.tirose.core.tool.api.IResultCode;
import org.tirose.core.tool.api.ResultCode;

/**
 * @Auther : 毛一凡
 * @Email : 349676014@qq.com
 * @Date : 2020/7/3
 */
public class SpringInitializeException extends RuntimeException {

	@Getter
	private final IResultCode resultCode;

	public SpringInitializeException(String message) {
		super(message);
		this.resultCode = ResultCode.FAILURE;
	}

	public SpringInitializeException(IResultCode resultCode) {
		super(resultCode.getMessage());
		this.resultCode = resultCode;
	}

	public SpringInitializeException(IResultCode resultCode, Throwable cause) {
		super(cause);
		this.resultCode = resultCode;
	}
}
