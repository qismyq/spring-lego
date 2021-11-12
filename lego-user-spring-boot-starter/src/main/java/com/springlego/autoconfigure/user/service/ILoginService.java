package com.springlego.autoconfigure.user.service;

/**
 * @Description login 业务
 * @Author Michael Wong
 * @Email michael_wong@yunqihui.net
 * @Date 2019/12/2 16:11
 **/
public interface ILoginService {

    /**
     * @desc: 获取前端用户登录账号的角色和jwt
     * @param account 用户账户，可以为手机号，也可为第三方账号的开放id
     * @return: String jwtoken
     * @auther: Michael Wong
     * @email:  michael_wong@yunqihui.net
     * @date:   2019/12/2 16:21
     * @update:
     */
    public String issueFrontJWT(String account)throws Exception;

    /**
     * @desc: 获取后端用户登录账号的角色和jwt
     * @param account 后端用户账户
     * @return: String jwtoken
     * @auther: Michael Wong
     * @email:  michael_wong@yunqihui.net
     * @date:   2019/12/2 16:28
     * @update:
     */
    public String issueSystemJWT(String account) throws Exception;
}
