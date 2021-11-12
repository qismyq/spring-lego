package com.springlego.autoconfigure.shiro.errorhandler;

import com.springlego.autoconfigure.frame.errorhandler.IErrorCode;

/**
 * @Description 用户模块错误码
 * <p>0,1为成功失败总类；<br/>
 * 错误码结构为A-BC-DE <br/>
 * A：4为客户端错误，5为服务端错误 <br/>
 * BC:模块分类，比如基础类为00，用户类为01，依次叠加 <br/>
 * DE:模块下具体错误码，例如参数缺失为00，参数无效为01，依次叠加<br/>
 * </p>
 * 此模块BC为02
 * @Author Michael Wong
 * @Email michael_wong@yunqihui.net
 * @Date 2019/11/4 17:50
 **/
public enum ShiroErrorCodeEnum implements IErrorCode {

    /**
     * <br> 无效请求
     */
    E_40200("40200","无效请求")

    /**
     * <br> 用户密码认证失败
     */
    ,E_40201("40201","用户密码认证失败")

    /**
     * <br> 无访问权限
     */
    ,E_40203("40203","无访问权限")

    /**
     * <br> 认证失效，请重新登录
     */
    ,E_40204("40204","认证失效，请重新登录")

    /**
     * <br> 认证错误，token不匹配
     */
    ,E_40205("40205","认证错误")

    /**
     * <br> 非法访问，未携带token
     */
    ,E_40206("40206","非法访问")

    /**
     * <br> 多个用户账户
     */
    ,E_50200("50200","系统认证失败")

    ;

    String errorCode ;
    String errorMessage ;


    @Override
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    ShiroErrorCodeEnum(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "userErrorCode-errorCode:"+getErrorCode()+";errorMessage:"+getErrorMessage();
    }

}
