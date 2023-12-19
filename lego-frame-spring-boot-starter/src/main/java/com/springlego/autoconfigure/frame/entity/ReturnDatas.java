package com.springlego.autoconfigure.frame.entity;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springlego.autoconfigure.frame.errorhandler.FrameCodeEnum;
import com.springlego.autoconfigure.frame.errorhandler.ICode;
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
	private Integer code=200;
	/**
	 * http请求状态
	 */
	private String status = ReturnDatas.SUCCESS;
	/**
	 * 处理结果提示信息
	 */
	private String message ;
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
	public ReturnDatas(Integer code) {
        if (!this.code.equals(code)){
            this.status = ERROR;
        }
		this.code = code;
	}
	@Deprecated
	public ReturnDatas(Integer code, String message) {
		if (!this.code.equals(code)){
            this.status = ERROR;
        }
        this.code = code;
		this.message = message;
	}
	@Deprecated
	public ReturnDatas(Integer code, String message, T data) {
        if (!this.code.equals(code)){
            this.status = ERROR;
        }
		this.code = code;
		this.message = message;
		this.data = data;
	}

	//  constructor -- end

	public static ReturnDatas getSuccessReturnDatas() {
		return new ReturnDatas(FrameCodeEnum.SUCCESS.getCode(), FrameCodeEnum.SUCCESS.getMessage());
	}
	public static ReturnDatas getErrorReturnDatas(ICode code) {
		return  new ReturnDatas(code.getCode(), code.getMessage());
	}
}
