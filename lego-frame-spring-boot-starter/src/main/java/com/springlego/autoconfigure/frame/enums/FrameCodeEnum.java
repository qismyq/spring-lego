package com.springlego.autoconfigure.frame.enums;

import com.springlego.autoconfigure.frame.errorhandler.ICode;

/**
 * @Description 基础错误码
 * <p>0,1为成功失败总类；<br/>
 * 错误码结构为A-BC-DEF <br/>
 * A：4为客户端错误，5为服务端错误 <br/>
 * BC:模块分类，比如基础类为00，公共为01，依次叠加，本模块为00 <br/>
 * DEF:模块下具体错误码，例如参数缺失为000，参数无效为001，依次叠加<br/>
 * </p>
 * @Author Michael Wong
 * @Email michael_wang90@163.com
 * @Date 2019/11/4 17:50
 **/
public enum FrameCodeEnum implements ICode {
    /**
     * @Description 成功
     */
    SUCCESS(200,"成功")

    /**
     * @Description 失败
     */
    ,ERROR(500,"失败")


    /**
    * @Description 参数缺失
    */
    ,PARAMETER_MISS(400000,"参数缺失")

    /**
     * @Description 操作频繁，请稍后再试
     */
    ,OPERATE_FREQUENT(400001,"操作频繁，请稍后再试")

    /**
     * @Description 操作类型不存在
     */
    ,E_40002(400002,"操作类型不存在")

    /**
     * @Description 系统繁忙，请稍后再试
     */
    ,UNKNOWN_ERROR(500000,"系统繁忙，请稍后再试")

    /**
     * @Description 配置文件配置项缺失
     */
    ,CONFIG_ERROR(500001,"配置文件配置项缺失")

    ;

    private Integer code ;
    private String message ;


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

    FrameCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return "frameErrorCode-errorCode:"+getCode()+";errorMessage:"+getMessage();
    }

}
