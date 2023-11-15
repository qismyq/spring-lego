package com.springlego.autoconfigure.frame.config;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Description mybatisPlus 配置
 * @Author Michael Wong
 * @Email michael_wang90@163.com
 * @Date 2019/9/27 18:36
 **/
@EnableTransactionManagement
@Configuration
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInnerInterceptor paginationInnerInterceptor() {
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        // paginationInterceptor.setLimit(你的最大单页限制数量，默认 500 条，小于 0 如 -1 不受限制);
        paginationInnerInterceptor.setMaxLimit(-1L);
        return paginationInnerInterceptor;
    }
}
