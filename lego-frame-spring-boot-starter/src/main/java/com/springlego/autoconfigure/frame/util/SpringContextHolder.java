package com.springlego.autoconfigure.frame.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Spring 工具类
 *
 * @author 9iuorg@gmail.com weicms.net
 * @date 2011-10-13
 */

@Component
@Lazy(false)
public class SpringContextHolder implements ApplicationContextAware {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private static ApplicationContext applicationContext;

    @SuppressWarnings("static-access")
    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        SpringContextHolder.applicationContext = context;
    }

    /**
     * 根据beanName 获取 spring bean
     *
     * @param beanName
     * @return Object
     */
    public static Object getBean(String beanName) throws BeansException {
        if (applicationContext.containsBean(beanName)) {
            return applicationContext.getBean(beanName);
        }
        return null;
    }

    /**
     * 根据bean type 获取springBean
     *
     * @param clazz
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> T getBeanByType(Class<T> clazz) {
        return (T) applicationContext.getBean(clazz);
    }

    /**
     * 根据bean type 获取所有的springBean
     *
     * @param clazz
     * @return
     */
    public static <T> Map<String, T> getBeansByType(Class<T> clazz)throws BeansException{
       return applicationContext.getBeansOfType(clazz);
    }
    /**
     * 获取 Spring applicationContext
     *
     * @return
     */
    public static ApplicationContext getContext() {
        return applicationContext;
    }

    /**
     * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
     * @param name beanname
     * @return 是否存在
     */
    public static Boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

}
