package com.springlego.autoconfigure.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.mapper.ClassPathMapperScanner;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @Classname PackageScanConfig
 * @Description 包路径扫描
 * @Date 2021/5/15 下午 03:38
 * @Created by H2018452
 * @author Michael Wong
 */
@Configuration
public class LegoFramePackageScanConfig implements ImportBeanDefinitionRegistrar {


    private static final String BASE_PACKAGE = "net.yunqihui";
    private static final String BASE_MAPPER_PACKAGE = "net.yunqihui.**.mapper";

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 扫描Component组件
        LegoScanner legoScanner = new LegoScanner(registry);
        legoScanner.doScan(BASE_PACKAGE);
        // 扫描Mapper组件
        LegoMapperScanner legoMapperScanner = new LegoMapperScanner(registry);
        legoMapperScanner.doScan(BASE_MAPPER_PACKAGE);
    }


    /**
     * Component组件自动扫描
     */
    class LegoScanner extends ClassPathBeanDefinitionScanner {

        public LegoScanner(BeanDefinitionRegistry registry) {
            super(registry, false);
        }

        @Override
        public Set<BeanDefinitionHolder> doScan(String... basePackages) {
            // 扫描类型为Component组件，因为Controller、Service等注解的子注解都是Component
            addIncludeFilter(new AnnotationTypeFilter(Component.class));
            // 去除Configuration重复扫描，遵守spring.factories配置文件指定的规约
            addExcludeFilter(new AnnotationTypeFilter(Configuration.class));
            return super.doScan(basePackages);
        }

    }

    /**
     * Mapper组件自动扫描
     */
    class LegoMapperScanner extends ClassPathMapperScanner {

        public LegoMapperScanner(BeanDefinitionRegistry registry) {
            super(registry);
        }

        @Override
        public Set<BeanDefinitionHolder> doScan(String... basePackages) {
            addIncludeFilter(new AnnotationTypeFilter(Mapper.class));
            return super.doScan(basePackages);
        }
    }

}
