# spring-lego



&emsp;&emsp;现在微服务火的一塌糊涂，但凡出来面个试，好像你不会微服务就跟你什么都不会一样。但是像我们这种做外包的小公司，上微服务就不太现实，首先技术支撑不够，其次开发速度无法满足快速开发、快速交付的要求，各种服务治理、服务熔断和降级、链路追踪等等，简直能把甲方的规划进度拖死，而且根据我们的经验来说，找我们的甲方70%的都是没有自己的技术团队，这样交付后即使让甲方自己组建技术团队，对他们来说也是一笔更高的成本。  
&emsp;&emsp;所以在这种背景下，我们选择依托springboot的starter特性，把业务逻辑进行封装，使不同的甲方，重复的项目需求能够达到即插即拔，达到快速构建、快速开发，节省成本的目的。由此定义名字为spring-lego:像乐高积木一样，随意组合，快速成型。==此项目持续集成中==，以下便是在进行starter封装过程中遇到的问题以及注意的细节。    

---------------------------------------------


### starter结构

starter名称 | 业务范围
---|---
lego-frame-spring-boot-starter | 基础starter,其它所有starter包括主项目直接依赖此starter即可，主工程无需在引入springboot-starter依赖 
lego-common-spring-boot-starter| 公共工具类starter
lego-user-spring-boot-starter | 用户业务starter（業務需改造）
lego-sms-spring-boot-starter   | 短信業務starter（暫時只實現阿里雲短信，其他通道集成中）
lego-shiro-spring-boot-starter | 权限控制starter（業務需改造）
lego-wechat-spring-boot-starter| 微信相关业务starter（業務需改造）


---------------------------------------------
