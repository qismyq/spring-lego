package net.yunqihui.autoconfigure.shiro.provider.impl;

import net.yunqihui.autoconfigure.shiro.provider.ShiroFilterRulesProvider;
import net.yunqihui.autoconfigure.shiro.rule.RolePermRule;
import net.yunqihui.starter.user.service.IRoleMenuService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/* *
 * @Author tomsun28
 * @Description 
 * @Date 16:46 2018/3/7
 */
@Service("ShiroFilterRulesProvider")
public class ShiroFilterRulesProviderImpl implements ShiroFilterRulesProvider {

    @Autowired
    private IRoleMenuService roleMenuService;

    @Override
    public List<RolePermRule> loadRolePermRules() {
        List<HashMap<String, String>> roleMenuMap = roleMenuService.selectRoleMenus();
        if (CollectionUtils.isEmpty(roleMenuMap)) {
            return null;
        }
        List<RolePermRule> rules = roleMenuMap.stream()
                .map(map -> new RolePermRule(map.get("url"), map.get("needRoles")))
                .collect(Collectors.toList());

        return rules;
    }

}
