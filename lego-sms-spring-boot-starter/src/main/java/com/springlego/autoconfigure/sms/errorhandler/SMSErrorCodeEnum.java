package net.yunqihui.autoconfigure.sms.errorhandler;

import net.yunqihui.autoconfigure.frame.errorhandler.IErrorCode;

/**
 * @Description 短信模块错误码
 * <p>0,1为成功失败总类；<br/>
 * 错误码结构为A-BC-DE <br/>
 * A：4为客户端错误，5为服务端错误 <br/>
 * BC:模块分类，比如基础类为00，用户类为01，依次叠加。本模块使用代码02<br/>
 * DE:模块下具体错误码，例如参数缺失为00，参数无效为01，依次叠加<br/>
 * </p>
 * @Author Michael Wong
 * @Email michael_wong@yunqihui.net
 * @Date 2019/11/4 17:50
 **/
public enum SMSErrorCodeEnum implements IErrorCode {

    /**
     * <br> 用户不存在
     */
    E_40200(40200,"用户不存在")

    /**
     * <br> 密码错误
     */
    ,E_40201(40201,"密码错误")

    /**
     * <br> 阿里云服务商错误
     */
    ,E_50200(50200,"阿里云服务商错误")
    /**
     * <br> 请配置阿里云短信服务相关参数
     */
    ,E_50201(50201,"请配置阿里云短信服务相关参数")

    ;

    Integer errorCode ;
    String errorMessage ;


    @Override
    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    SMSErrorCodeEnum(Integer errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "userErrorCode-errorCode:"+getErrorCode()+";errorMessage:"+getErrorMessage();
    }

}
