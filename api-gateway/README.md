### gateway 集成oauth2 实现逻辑
- 不需要依赖oauth2 与 security的包
- 主要是2个接口/oauth/token  /oauth/user 
    - 第一个接口通过拦截器添加clientId 和 clientSecret
    - 第二个接口对所有的需要授权的接口获取用户信息

