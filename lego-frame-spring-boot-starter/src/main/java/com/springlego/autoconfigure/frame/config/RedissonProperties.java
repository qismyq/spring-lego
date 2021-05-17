//package net.yunqihui.autoconfigure.config;
//
//import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//
///**
// * @Description redisson 配置项
// * @Author Michael Wong
// * @Email michael_wong@yunqihui.net
// * @Date 2020/8/25 9:46
// **/
//@Data
//@Getter
//@Setter
//@ConfigurationProperties(prefix = "spring.redisson")
//public class RedissonProperties {
//
//    private String address;
//    private int connectionMinimumIdleSize = 10;
//    private int idleConnectionTimeout=10000;
//    private int pingTimeout=1000;
//    private int connectTimeout=10000;
//    private int timeout=3000;
//    private int retryAttempts=3;
//    private int retryInterval=1500;
//    private int reconnectionTimeout=3000;
//    private int failedAttempts=3;
//    private String password = null;
//    private int subscriptionsPerConnection=5;
//    private String clientName=null;
//    private int subscriptionConnectionMinimumIdleSize = 1;
//    private int subscriptionConnectionPoolSize = 50;
//    private int connectionPoolSize = 64;
//    private int database = 0;
//    private boolean dnsMonitoring = false;
//    private int dnsMonitoringInterval = 5000;
//
//    private int thread; //当前处理核数量 * 2
//
//    private String codec="org.redisson.codec.JsonJacksonCodec";
//
//
//
//
//}
