package com.springlego.autoconfigure.common.enums;

import com.springlego.autoconfigure.frame.errorhandler.ICode;

/**
 * @Description 用户模块错误码
 * <p>0,1为成功失败总类；<br/>
 * 错误码结构为A-BC-DEF <br/>
 * A：4为客户端错误，5为服务端错误 <br/>
 * BC:模块分类，比如基础类为00，公共为01，依次叠加，本模块为01 <br/>
 * DEF:模块下具体错误码，例如参数缺失为000，参数无效为001，依次叠加<br/>
 * </p>
 * @Author Michael Wong
 * @Email michael_wang90@163.com
 * @Date 2019/11/4 17:50
 **/
public enum CommonErrorCodeEnum implements ICode {

    /**
     * <br> 重复数据错误
     */
    VALUE_DUPLICATE_ERROR(401000,"存在重复数据"),
    /**
     * <br> 记录不存在错误
     */
    DATA_NOT_EXISTS_ERROR(401001,"当前记录不存在"),
    /**
     * <br> 状态未启用错误
     */
    STATUS_NOT_ENABLED_ERROR(401002,"状态未启用"),
    /**
     * <br> 该字典类型还有字典数据
     */
    DICT_TYPE_HAS_CHILDREN(401003,"该字典类型还有字典数据"),
    /**
     * <br> 已经存在该名字的字典类型
     */
    DICT_TYPE_NAME_DUPLICATE(401004,"已经存在该名字的字典类型"),
    /**
     * <br> 已经存在该类型的字典类型
     */
    DICT_TYPE_TYPE_DUPLICATE(401005,"已经存在该类型的字典类型"),
    /**
     * <br> 当前字典类型不存在
     */
    DICT_TYPE_NOT_EXISTS(401006,"当前字典类型不存在"),


    ;

    Integer errorCode ;
    String errorMessage ;


    @Override
    public Integer getCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    CommonErrorCodeEnum(Integer errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "COMMON_ERROR:errorCode:"+getCode()+";errorMessage:"+getMessage();
    }

}
