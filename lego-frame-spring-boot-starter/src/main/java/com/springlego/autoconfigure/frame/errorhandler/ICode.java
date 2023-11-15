package com.springlego.autoconfigure.frame.errorhandler;

/**
 * @Description 错误码接口，所有模块下的自定义错误码枚举类型必须实现此接口
 * @Author Michael Wong
 * @Email michael_wang90@163.com
 * @Date 2019/11/5 10:52
 **/
public interface ICode {

    public Integer getCode();

    public String getMessage();
}
