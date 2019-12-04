package net.yunqihui.autoconfigure.user.service.impl;

import io.jsonwebtoken.SignatureAlgorithm;
import net.yunqihui.autoconfigure.shiro.entity.ShiroStatic;
import net.yunqihui.autoconfigure.shiro.provider.AccountProvider;
import net.yunqihui.autoconfigure.shiro.util.JsonWebTokenUtil;
import net.yunqihui.autoconfigure.user.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Description 登录业务
 * @Author Michael Wong
 * @Email michael_wong@yunqihui.net
 * @Date 2019/12/2 16:11
 **/
@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private AccountProvider accountProvider;

    @Override
    public String issueFrontJWT(String account)throws Exception {

        // 根据appId获取其对应所拥有的角色(这里设计为角色对应资源，没有权限对应资源)
        String roles = "role_anon,role_front";
        String accountJWT = getAccountJWT(account, roles);
        return accountJWT;
    }


    @Override
    public String issueSystemJWT(String account) throws Exception {

        // 根据appId获取其对应所拥有的角色(这里设计为角色对应资源，没有权限对应资源)
        String roles = accountProvider.loadAccountRole(account);

        String accountJWT = getAccountJWT(account, roles);

        return accountJWT;
    }



    private String getAccountJWT(String account,String roles) {

        // 时间以秒计算,token有效刷新时间是token有效过期时间的2倍
        long refreshPeriodTime = 36000L;
        String jwt = JsonWebTokenUtil.issueJWT(UUID.randomUUID().toString(), account,
                ShiroStatic.JWT_ISSUER, refreshPeriodTime >> 2, roles, null, SignatureAlgorithm.HS512);
        // 将签发的JWT存储到Redis： {JWT-SESSION-{appID} , jwt}
        redisTemplate.opsForValue().set(ShiroStatic.JWT_SESSION + account, jwt, refreshPeriodTime, TimeUnit.SECONDS);
        return jwt;
    }
}
