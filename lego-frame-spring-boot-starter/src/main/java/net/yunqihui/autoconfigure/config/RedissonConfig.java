//package net.yunqihui.autoconfigure.config;
//
//import io.netty.channel.nio.NioEventLoopGroup;
//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.redisson.client.codec.Codec;
//import org.redisson.config.Config;
//import org.redisson.spring.starter.RedissonAutoConfiguration;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.AutoConfigureBefore;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.util.ClassUtils;
//
///**
// * @Description redisson 配置
// * @Author Michael Wong
// * @Email michael_wong@yunqihui.net
// * @Date 2020/8/25 9:37
// **/
//@ConditionalOnProperty(
//        name = {"spring.redisson.enabled"},
//        havingValue = "true",
//        matchIfMissing = false
//)
//@AutoConfigureBefore({RedissonAutoConfiguration.class})
//@EnableConfigurationProperties({RedissonProperties.class})
//@Configuration
//public class RedissonConfig {
//
//    @Autowired
//    private RedissonProperties redissonProperties;
//
//    @Bean(destroyMethod = "shutdown")
//    @ConditionalOnMissingBean(RedissonClient.class)
//    public RedissonClient redisson() throws Exception {
//        Config config = new Config();
//        config.useSingleServer().setAddress(redissonProperties.getAddress())
//                .setConnectionMinimumIdleSize(redissonProperties.getConnectionMinimumIdleSize())
//                .setConnectionPoolSize(redissonProperties.getConnectionPoolSize())
//                .setDatabase(redissonProperties.getDatabase())
////                .setDnsMonitoring(false)
//                .setDnsMonitoringInterval(redissonProperties.getDnsMonitoringInterval())
//                .setSubscriptionConnectionMinimumIdleSize(redissonProperties.getSubscriptionConnectionMinimumIdleSize())
//                .setSubscriptionConnectionPoolSize(redissonProperties.getSubscriptionConnectionPoolSize())
//                .setSubscriptionsPerConnection(redissonProperties.getSubscriptionsPerConnection())
//                .setClientName(redissonProperties.getClientName())
////                .setFailedAttempts(redissonProperties.getFailedAttempts())
//                .setRetryAttempts(redissonProperties.getRetryAttempts())
//                .setRetryInterval(redissonProperties.getRetryInterval())
////                .setReconnectionTimeout(redissonProperties.getReconnectionTimeout())
//                .setTimeout(redissonProperties.getTimeout())
//                .setConnectTimeout(redissonProperties.getConnectTimeout())
//                .setIdleConnectionTimeout(redissonProperties.getIdleConnectionTimeout())
////                .setPingTimeout(pingTimeout)
//                .setPassword(redissonProperties.getPassword());
//        Codec codec=(Codec) ClassUtils.forName(redissonProperties.getCodec(),ClassUtils.getDefaultClassLoader()).newInstance();
//        config.setCodec(codec);
//        config.setThreads(redissonProperties.getThread());
//        config.setEventLoopGroup(new NioEventLoopGroup());
////        config.setUseLinuxNativeEpoll(false);
//        return Redisson.create(config);
//    }
//}
