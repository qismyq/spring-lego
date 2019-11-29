package net.yunqihui.autoconfigure.shiro.provider.impl;


import net.yunqihui.autoconfigure.shiro.entity.Account;
import net.yunqihui.autoconfigure.shiro.entity.ShiroStatic;
import net.yunqihui.autoconfigure.shiro.provider.AccountProvider;
import net.yunqihui.autoconfigure.user.entity.User;
import net.yunqihui.autoconfigure.user.service.IFrontUserService;
import net.yunqihui.autoconfigure.user.service.IUserRoleService;
import net.yunqihui.autoconfigure.user.service.IUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Set;

/* *
 * @Author tomsun28
 * @Description 
 * @Date 9:22 2018/2/13
 */
@Service("AccountProvider")
public class AccountProviderImpl implements AccountProvider {

    @Autowired
    private IUserService userService;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private IFrontUserService frontUserService;

    @Override
    public Account loadAccount(String account) throws Exception {
        User user = userService.getLoginUser(account, "是");
        if (user == null) {
            return null;
        }
        return new Account(user.getAccount(), user.getPassword(), ShiroStatic.PASSWORD_MD5_SALT);
    }

    public Account loadFrontAccount(String account)throws Exception {
//        frontUserService.getOne(new QueryWrapper<FrontUser>()
//                .eq("account",account)
//                .or()
//                .eq("wechatId",account)
//                .or()
//                .eq("wechatId",account));
        return null;
    }

    @Override
    public String loadAccountRole(String account) throws Exception {

        Set<String> roleCodeSet = userRoleService.getRoleCodeAsStringByAccount(account);
        if (CollectionUtils.isNotEmpty(roleCodeSet)) {
            Iterator<String> iterator = roleCodeSet.iterator();
            StringBuffer roles = new StringBuffer();
            while (iterator.hasNext()) {
                roles.append(iterator.next()).append(",");
            }
            return roles.toString();
        }

        return null;
    }

}
