package com.springlego.autoconfigure.user.errorhandler;

import com.springlego.autoconfigure.frame.errorhandler.ICode;

/**
 * @Description 用户模块错误码
 * <p>0,1为成功失败总类；<br/>
 * 错误码结构为A-BC-DE <br/>
 * A：4为客户端错误，5为服务端错误 <br/>
 * BC:模块分类，比如基础类为00，用户类为01，依次叠加 <br/>
 * DE:模块下具体错误码，例如参数缺失为00，参数无效为01，依次叠加<br/>
 * </p>
 * @Author Michael Wong
 * @Email michael_wang90@163.com
 * @Date 2019/11/4 17:50
 **/
public enum UserErrorCodeEnum implements ICode {

    /**
     * <br> 用户不存在
     */
    USER_NOT_EXIST(40100,"用户不存在")

    /**
     * <br> 密码错误
     */
    ,PASSWORD_ERROR(40101,"密码错误")

    /**
     * <br> 多个用户账户
     */
    ,E_MULTIPLE_ACCOUNT(50100,"多个用户账户")

    ;

    Integer code ;
    String message ;


    @Override
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    UserErrorCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return "userErrorCode-errorCode:"+getCode()+";errorMessage:"+getMessage();
    }

}
