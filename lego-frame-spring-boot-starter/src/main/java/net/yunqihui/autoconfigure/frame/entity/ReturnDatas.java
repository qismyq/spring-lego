package net.yunqihui.autoconfigure.frame.entity;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.experimental.Accessors;
import net.yunqihui.autoconfigure.frame.errorhandler.FrameErrorCodeEnum;
import net.yunqihui.autoconfigure.frame.errorhandler.IErrorCode;

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
public class ReturnDatas implements Serializable{
	private static final long serialVersionUID = 1L;

	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	public static final String WARNING = "warning";
	public static final String LOGIN = "login";
	public static final String REGISTER = "register";

	/**
	 * http请求状态码
	 */
	private String statusCode="200";
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
	private String errorCode ;
	/**
	 * 处理结果错误提示信息
	 */
	private String errorMessage ;
	/**
	 * 处理返回结果
	 */
	private Object data;
	private Integer palceCount;
    @SuppressWarnings("rawtypes")
	private Map map;
	private Page page;
	private String sum;
	private Object queryBean;

	public ReturnDatas setData(Object data){
		if (data instanceof Page) {
			this.data = ((Page) data).getRecords() ;
		}else {
			this.data = data ;
		}
		return this;
	}
	//  constructor -- start
	public ReturnDatas() {
	}
	@Deprecated
	public ReturnDatas(String errorCode) {
		this.errorCode = errorCode;
	}
	@Deprecated
	public ReturnDatas(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	@Deprecated
	public ReturnDatas(String errorCode, String errorMessage, Object data) {
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
