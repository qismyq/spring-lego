package net.yunqihui.starter.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class User2Controller {

    Logger logger = LoggerFactory.getLogger(User2Controller.class);

//    @Autowired
//    private UserMapper userMapper;

    @RequestMapping(value = "/frame/json")
    public String getUsers()throws Exception{
        logger.info("这是一个info");
        return "1";
    }



}
