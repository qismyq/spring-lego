package net.yunqihui.starter.frame.util;


import net.yunqihui.starter.frame.entity.BaseEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.net.URI;

/**
 * Spring 工具类
 *
 * @author 9iuorg@gmail.com weicms.net
 * @date 2011-10-13
 */

@Component("springUtils")
public class SpringUtils implements ApplicationContextAware {
    @Autowired
    private Environment env;

    private Logger logger = LoggerFactory.getLogger(getClass());
    private static ApplicationContext applicationContext;

    public SpringUtils() {

    }

    @SuppressWarnings("static-access")
    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.applicationContext = context;
        String serverType = env.getProperty("server.type");
        try {
            if("0".equals(serverType)){
                initEntityInfo();
            }

            // 初始化添加自定义的Lucene词语
            // LuceneUtils.addDictWord(words);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        System.out.println("----------------------base frame started----------------------");

    }

    /**
     * 根据beanName 获取 spring bean
     *
     * @param beanName
     * @return Object
     */
    public static Object getBean(String beanName) {
        if (StringUtils.isEmpty(beanName)) {
            return null;
        }
        return applicationContext.getBean(beanName);
    }

    /**
     * 根据bean type 获取springBean
     *
     * @param clazz
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Object getBeanByType(Class clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * 获取 Spring applicationContext
     *
     * @return
     */
    public static ApplicationContext getContext() {
        return applicationContext;
    }

    private void initEntityInfo() throws Exception {

        String basePathName = BaseEntity.class.getName();
        String[] basePaths = basePathName.split("\\.");

        String classPath = "**/*.class";

        if (basePaths == null || basePaths.length < 2) {
            return;
        }

        String packagePath = basePaths[0] + "/" + basePaths[1] + "/";
        classPath = packagePath + classPath;

        PathMatchingResourcePatternResolver pmrpr = new PathMatchingResourcePatternResolver();
        Resource[] resources = pmrpr
                .getResources(PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + classPath);

        for (Resource resource : resources) {

            URI uri = resource.getURI();
            String entityClassName = uri.toString();

            entityClassName = entityClassName.substring(entityClassName.lastIndexOf(packagePath),
                    entityClassName.lastIndexOf(".class"));
            entityClassName = entityClassName.replaceAll("/", ".");
            // System.out.println(entityClassName);
            Class<?> clazz = Class.forName(entityClassName);

//            if (clazz.isAnnotationPresent(Table.class) || clazz.isAnnotationPresent(LuceneSearch.class)
//                    || clazz.isAnnotationPresent(NotLog.class)) {// 如果有Table注解或者LuceneSearch注解,缓存实体类
//                ClassUtils.getEntityInfoByClass(clazz);
//            }

        }

    }

}
