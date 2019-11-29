package net.yunqihui.autoconfigure.shiro.provider;


import net.yunqihui.autoconfigure.shiro.entity.Account;

/* *
 * @Author tomsun28
 * @Description  数据库用户密码账户提供
 * @Date 16:35 2018/2/11
 */
public interface AccountProvider {

    Account loadAccount(String account)throws Exception ;

    String loadAccountRole(String appId) throws Exception;
}
