package com.springlego.autoconfigure.security.exception;


import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * 自定义异常
 *
 * @author michael wong
 */
public class LegoOAuth2Exception extends OAuth2Exception {
	private String msg;

	public LegoOAuth2Exception(String msg) {
		super(msg);
		this.msg = msg;
	}

	public LegoOAuth2Exception(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
