# FRIGATE 项目结构

```
|-- frigate
    |-- business-service        // 业务服务
    |   |-- cn.cici.frigate.logistics-service       // 统计
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
        |-- eureka-server[9000]         // 注册中心
        |-- config-server[9001]         // 配置中心
        |-- api-gateway[9002]           // 网关
        |-- monitor                 // admin监控
        |-- zipkin                  // zipkin链路跟踪
```

[参考项目](https://github.com/rench/scio)



> docker run -p 3306:3306 --privileged=true -v /usr/local/docker/mysql/logs:/logs -v /data/docker/mysql/data:/var/lib/mysql -v /data/docker/mysql/conf/my.cnf:/etc/mysql/mysql.conf.d/mysqld.cnf -e MYSQL_ROOT_PASSWORD=123456 -d mysql

> docker外部连接mysql8: https://blog.csdn.net/qq_26462567/article/details/86713638

> docker run -d -p 80:80 --name nginx -v $PWD/www:/usr/share/nginx/html -v $PWD/conf/nginx.conf:/etc/nginx/nginx.conf -v $PWD/logs:/var/log/nginx nginx
