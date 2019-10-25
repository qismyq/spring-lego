package net.yunqihui.starter.social.controller;

import net.yunqihui.starter.social.entity.User;
import net.yunqihui.starter.social.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);
//    @Autowired
//    private UserMapper userMapper;

    @Autowired
    private IUserService userService ;

    @RequestMapping(value = "/getUsers/json")
    public List<User> getUsers()throws Exception{
        logger.info("这是一个info");
        logger.debug("这是一个debug");
        logger.error("这是一个error");
        logger.warn("这是一个warn");
        List<User> all = userService.list();
        return all;
    }



}
