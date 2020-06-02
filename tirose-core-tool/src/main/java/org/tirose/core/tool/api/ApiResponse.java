package org.tirose.core.tool.api;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author : 毛一凡
 * @Date : Create in 14:47 2019/10/26
 */
@Data
public class ApiResponse<T> implements Serializable {

	private Integer code;
	private String msg;
	private T data;

	private ApiResponse(IResultCode resultCode) {
		this.code = resultCode.getCode();
		this.msg = resultCode.getMessage();
		this.data = null;
	}

	private ApiResponse(IResultCode resultCode, String msg) {
		this.code = resultCode.getCode();
		this.msg = msg;
		this.data = null;
	}
	private ApiResponse(IResultCode resultCode, T data) {
		this.code = resultCode.getCode();
		this.msg = resultCode.getMessage();
		this.data = data;
	}

	private ApiResponse(Integer code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public static <T> ApiResponse<T> success(){
		return new ApiResponse(ResultCode.SUCCESS);
	}

	public static <T> ApiResponse<T> success(String msg){
		return new ApiResponse(ResultCode.SUCCESS, msg);
	}

	public static <T> ApiResponse<T> success(IResultCode resultCode){
		return new ApiResponse(resultCode);
	}

	public static <T> ApiResponse<T> success(Integer code, String msg, T data){
		return new ApiResponse(code, msg, data);
	}

	/**
	 * 传递值
	 * @param data
	 * @param <T>
	 * @return
	 */
	public static <T> ApiResponse<T> data(T data){
		return new ApiResponse(ResultCode.SUCCESS, data);
	}

	/**
	 * 传递值
	 * @param data
	 * @param <T>
	 * @return
	 */
	public static <T> ApiResponse<T> data(T data, String msg){
		return new ApiResponse(ResultCode.SUCCESS.getCode(), msg, data);
	}

	/**
	 * 传递值
	 * @param data
	 * @param <T>
	 * @return
	 */
	public static <T> ApiResponse<T> data(Integer code, T data, String msg){
		return new ApiResponse(code, msg, data);
	}



	public static <T> ApiResponse<T> fail(IResultCode resultCode) {
		return new ApiResponse(resultCode);
	}

	public static <T> ApiResponse<T> fail(IResultCode resultCode, String message) {
		return new ApiResponse(resultCode, message);
	}

	public static <T> ApiResponse<T> fail(String message) {
		return new ApiResponse(ResultCode.INTERNAL_SERVER_ERROR, message);
	}

	public static <T> ApiResponse<T> status(boolean flag) {
		if (flag) {
			return new ApiResponse(ResultCode.SUCCESS);
		} else {
			return new ApiResponse(ResultCode.INTERNAL_SERVER_ERROR);
		}
	}
}
