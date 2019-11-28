package net.yunqihui.autoconfigure.shiro.provider.impl;


import net.yunqihui.autoconfigure.shiro.entity.vo.Account;
import net.yunqihui.autoconfigure.shiro.provider.AccountProvider;
import net.yunqihui.starter.user.entity.User;
import net.yunqihui.starter.user.service.IUserRoleService;
import net.yunqihui.starter.user.service.IUserService;
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


    public Account loadAccount(String account) throws Exception {
        User user = userService.getLoginUser(account, "是");
        if (user == null) {
            return null;
        }
        // todo 补充MD5的salt
        return new Account(user.getAccount(), user.getPassword(), "79sz6j");
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
