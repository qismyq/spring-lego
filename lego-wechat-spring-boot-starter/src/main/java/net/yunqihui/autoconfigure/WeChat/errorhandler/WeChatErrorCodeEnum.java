package net.yunqihui.autoconfigure.wechat.errorhandler;

import net.yunqihui.autoconfigure.frame.errorhandler.IErrorCode;

/**
 * @Description 用户模块错误码
 * <p>0,1为成功失败总类；<br/>
 * 错误码结构为A-BC-DE <br/>
 * A：4为客户端错误，5为服务端错误 <br/>
 * BC:模块分类，比如基础类为00，用户类为01，依次叠加 <br/>
 * DE:模块下具体错误码，例如参数缺失为00，参数无效为01，依次叠加<br/>
 * </p>
 * @Author Michael Wong
 * @Email michael_wong@yunqihui.net
 * @Date 2019/11/4 17:50
 **/
public enum WeChatErrorCodeEnum implements IErrorCode {


    /**
     * @Description 缺少授权码
     */
    E_40300("40300","缺少授权码")

    /**
     * @Description 缺少授权码
     */
    ,E_40301("40301","已存在授权信息")

    /**
     * @Description 微信配置缺失
     */
    ,E_50300("50300","微信配置缺失")

    /**
     * @Description 获取微信token失败
     */
    ,E_50301("50301","获取微信token失败")

    /**
     *  获取微信token失败
     */
    ,E_50302("50302","获取预授权码失败")

    /**
     *  获取授权信息失败
     */
    ,E_50303("50303","获取授权信息失败")
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

    WeChatErrorCodeEnum(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "userErrorCode-errorCode:"+getErrorCode()+";errorMessage:"+getErrorMessage();
    }

}
