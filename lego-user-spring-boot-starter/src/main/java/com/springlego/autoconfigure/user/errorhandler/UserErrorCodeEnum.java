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
     * <br> 多个岗位名称
     */
    ,MULTIPLE_POST_NAME(40102,"已经存在该名字的岗位")
    /**
     * <br> 多个岗位编码
     */
    ,MULTIPLE_POST_CODE(40103,"已经存在该标识的岗位")
    /**
     * <br> 当前岗位不存在
     */
    ,POST_NOT_FOUND(40104,"当前岗位不存在")
    /**
     * <br> 已经存在该名字的部门
     */
    ,MULTIPLE_DEPT_NAME(40105,"已经存在该名字的部门")
    /**
     * <br> 已经存在该标识的部门
     */
    ,MULTIPLE_DEPT_CODE(40106,"已经存在该标识的部门")
    /**
     * <br> 当前部门不存在
     */
    ,DEPT_NOT_FOUND(40107,"当前部门不存在")
    /**
     * <br> 不能设置自己的子部门为父部门
     */
    ,DEPT_PARENT_IS_CHILD(40108,"不能设置自己的子部门为父部门")
    /**
     * <br> 父级部门不存在
     */
    ,DEPT_PARENT_NOT_EXITS(40109,"父级部门不存在")
    /**
     * <br> 不能设置自己为父部门
     */
    ,DEPT_PARENT_ERROR(40110,"不能设置自己为父部门")
    /**
     * <br> 编码【{}】不能使用
     */
    ,ROLE_ADMIN_CODE_ERROR(40111,"编码【{}】不能使用")
    /**
     * <br> 已经存在名为【{}】的角色
     */
    ,ROLE_NAME_DUPLICATE(40112,"已经存在名为【{}】的角色")
    /**
     * <br> 已经存在编码为【{}】的角色
     */
    ,ROLE_CODE_DUPLICATE(40113,"已经存在编码为【{}】的角色")
    /**
     * <br> 不能操作类型为系统内置的角色
     */
    ,ROLE_CAN_NOT_UPDATE_SYSTEM_TYPE_ROLE(40114,"不能操作类型为系统内置的角色")
    /**
     * <br> 角色不存在
     */
    ,ROLE_NOT_EXISTS(40115,"角色不存在")
    /**
     * <br> 菜单不存在
     */
    ,MENU_NOT_EXISTS(40116,"菜单不存在")
    /**
     * <br> 不能设置自己为父菜单
     */
    ,MENU_PARENT_ERROR(40117,"不能设置自己为父菜单")
    /**
     * <br> 不能设置自己为父菜单
     */
    ,MENU_PARENT_NOT_EXISTS(40118,"父菜单不存在")
    /**
     * <br> 已经存在该名字的菜单
     */
    ,MENU_NAME_DUPLICATE(40119,"已经存在该名字的菜单")






    /**
     * <br> 多个用户账户
     */
    ,MULTIPLE_ACCOUNT(50100,"多个用户账户")
    /**
     * <br> 岗位禁用
     */
    ,POST_NOT_ENABLE(50101,"岗位不处于开启状态，不允许选择")
    /**
     * <br> 部门禁用
     */
    ,DEPT_NOT_ENABLE(50102,"部门不处于开启状态，不允许选择")
    /**
     * <br> 存在子部门，无法删除
     */
    ,DEPT_EXITS_CHILDREN(50103,"存在子部门，无法删除")
    /**
     * <br> 名字为【{}】的角色已被禁用
     */
    ,ROLE_IS_DISABLE(50105,"名字为【{}】的角色已被禁用")
    /**
     * <br> 存在子菜单，无法删除
     */
    ,MENU_EXISTS_CHILDREN(50106,"存在子菜单，无法删除")
    /**
     * <br> 父菜单的类型必须是目录或者菜单
     */
    ,MENU_PARENT_NOT_DIR_OR_MENU(50107,"父菜单的类型必须是目录或者菜单")



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
