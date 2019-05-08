# FRIGATE 项目结构

```
|-- frigate
    |-- business-service        // 业务服务
    |   |-- logistics-service       // 统计
    |   |-- user-center             // 用户中心
    |-- common-service          // 公共服务
    |   |-- auth-service            // 统一鉴权服务
    |   |-- logging-service         // 日志服务
    |   |-- mail-service            // 邮件服务
    |   |-- message-service         // 消息服务
    |   |-- sso-service             // 单点登录
    |   |-- job-service             // 任务调度服务
    |   |-- oss-service             // 存储服务
    |-- component               // 公共模块
    |   |-- component-core          // 工具类
    |   |-- component-starters      // 自动配置jar
    |   |   |-- swagger-starter         // 支持swagger的jar
    |-- system-framework        // 基础服务
        |-- config-server           // 配置中心
        |-- eureka-server           // 注册中心
        |-- monitor                 // admin监控
        |-- zipkin                  // zipkin链路跟踪
```

