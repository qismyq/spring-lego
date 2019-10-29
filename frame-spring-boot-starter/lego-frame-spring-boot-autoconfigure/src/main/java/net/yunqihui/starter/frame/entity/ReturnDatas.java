package net.yunqihui.starter.frame.entity;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

/**
 * 返回对象的封装
 * @author caomei
 *
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("serial")
public class ReturnDatas implements Serializable{
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	public static final String WARNING = "warning";
	public static final String LOGIN = "login";
	public static final String REGISTER = "register";
	private String statusCode="200";
	private String status;
	private String message;
	private Object data;
	private Integer palceCount;
    @SuppressWarnings("rawtypes")
	private Map map;
	private Page page;
	private String sum;
	private Object queryBean;
	
	public ReturnDatas() {
		
	}
	
	public static ReturnDatas getSuccessReturnDatas() {
	return new ReturnDatas(ReturnDatas.SUCCESS);
	}
	public static ReturnDatas getErrorReturnDatas() {
		return new ReturnDatas(ReturnDatas.ERROR);
		}
	public static ReturnDatas getWarningReturnDatas() {
		return new ReturnDatas(ReturnDatas.WARNING);
		}
	
	
	public ReturnDatas(String status) {
		this.status = status;
	}
	
	public ReturnDatas(String status, String message) {
		this.status = status;
		this.message = message;
	}
	
	public Integer getPalceCount() {
		return palceCount;
	}

	public void setPalceCount(Integer palceCount) {
		this.palceCount = palceCount;
	}

	public ReturnDatas(String status, String message, Object data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}
	
}
