### 基于百度IdGenerator的分布式id服务，提供公共的id生成服务

### id-producer 提供独立部署的id生成服务

### id-api 提供服务间id获取的通用接口
> 需要分布式id的服务,maven引入该包的依赖,即可,
```
  @Autowire
  IdProvider idProvider;
```