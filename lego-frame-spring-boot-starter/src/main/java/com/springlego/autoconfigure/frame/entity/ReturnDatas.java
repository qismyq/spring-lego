package com.springlego.autoconfigure.frame.entity;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springlego.autoconfigure.frame.errorhandler.FrameErrorCodeEnum;
import com.springlego.autoconfigure.frame.errorhandler.IErrorCode;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

/**
 * 返回对象的封装
 * @author caomei
 * @update michael_wong
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("serial")
public class ReturnDatas<T> implements Serializable{
	private static final long serialVersionUID = 1L;

	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	public static final String WARNING = "warning";
	public static final String LOGIN = "login";
	public static final String REGISTER = "register";

	/**
	 * http请求状态码
	 */
	private Integer statusCode=200;
	/**
	 * http请求状态
	 */
	private String status = ReturnDatas.SUCCESS;
	/**
	 * http请求结果信息
	 */
	private String message = "成功";
	/**
	 * 处理结果code
	 */
	private Integer errorCode ;
	/**
	 * 处理结果错误提示信息
	 */
	private String errorMessage ;
	/**
	 * 处理返回结果
	 */
	private T data;
	private Integer palceCount;
    @SuppressWarnings("rawtypes")
	private Map map;
	private Page page;
	private String sum;
	private Object queryParam;

	public ReturnDatas setData(T data){
		if (data instanceof Page) {
			this.data = (T) ((Page) data).getRecords();
		}else {
			this.data = data ;
		}
		return this;
	}
	//  constructor -- start
	public ReturnDatas() {
	}
	@Deprecated
	public ReturnDatas(Integer errorCode) {
		this.errorCode = errorCode;
	}
	@Deprecated
	public ReturnDatas(Integer errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	@Deprecated
	public ReturnDatas(Integer errorCode, String errorMessage, T data) {
		this.errorCode = errorCode;
		this.message = errorMessage;
		this.data = data;
	}

	//  constructor -- end

	public static ReturnDatas getSuccessReturnDatas() {
		return new ReturnDatas(FrameErrorCodeEnum.E_0.getErrorCode(),FrameErrorCodeEnum.E_0.getErrorMessage());
	}
	public static ReturnDatas getErrorReturnDatas(IErrorCode errorCode) {
		return  new ReturnDatas(errorCode.getErrorCode(), errorCode.getErrorMessage());
	}
}
