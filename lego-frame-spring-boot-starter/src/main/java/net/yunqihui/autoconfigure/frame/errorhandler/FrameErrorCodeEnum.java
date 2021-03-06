package net.yunqihui.autoconfigure.frame.errorhandler;

/**
 * @Description 基础错误码
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
public enum FrameErrorCodeEnum implements IErrorCode{
    /**
     * @Description 成功
     */
    E_0(0,"成功")

    /**
     * @Description 失败
     */
    ,E_1(1,"失败")


    /**
    * @Description 参数缺失
    */
    ,E_40000(40000,"参数缺失")

    /**
     * @Description 系统繁忙，请稍后再试
     */
    ,E_50000(50000,"系统繁忙，请稍后再试")

    ;

    private Integer errorCode ;
    private String errorMessage ;


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

    FrameErrorCodeEnum(Integer errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "frameErrorCode-errorCode:"+getErrorCode()+";errorMessage:"+getErrorMessage();
    }

}
