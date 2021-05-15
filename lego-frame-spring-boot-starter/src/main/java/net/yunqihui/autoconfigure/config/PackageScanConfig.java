package net.yunqihui.autoconfigure.config;

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @Classname PackageScanConfig
 * @Description 包路径扫描
 * @Date 2021/5/15 下午 03:38
 * @Created by H2018452
 */
@Configuration
public class PackageScanConfig implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    //资源加载器
    private ResourceLoader resourceLoader;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        LegoScanner customerScanner = new LegoScanner(registry);
        customerScanner.doScan("net.yunqihui");
    }


    class LegoScanner extends ClassPathBeanDefinitionScanner {

        public LegoScanner(BeanDefinitionRegistry registry) {
            super(registry, false);
        }

        @Override
        public Set<BeanDefinitionHolder> doScan(String... basePackages) {
            addIncludeFilter(new AnnotationTypeFilter(Component.class));
            // todo mapper扫描
//            addIncludeFilter(new AnnotationTypeFilter(Mapper.class));
            addExcludeFilter(new AnnotationTypeFilter(Configuration.class));
            return super.doScan(basePackages);
        }

    }
}
