
### 第一步: 获得nginx.conf文件 (如果已经有了可以省略)
- 通过docker启动nginx
    - docker run -p --name nginx 80:80 -d nginx
- 打开页面访问测试一下,确认能打开
- 创建nginx宿主主机的目录
    - /data/docker/nginx/www
    - /data/docker/nginx/conf
    - /data/docker/nginx/logs
- 将docker中的nginx.conf复制到宿主主机
    - docker cp nginx:/etc/nginx/nginx.conf /data/docker/nginx/conf
- 移除该服务
    - docker stop nginx
    - docker rm nginx

### 第二步: 重新启动nginx
- 重新启动nginx,将路径挂在到宿主目录下
    - docker run -d -p 80:80 --name nginx -v /data/docker/nginx/www:/usr/share/nginx/html -v /data/docker/nginx/conf/nginx.conf:/etc/nginx/nginx.conf -v /data/docker/nginx/logs:/var/log/nginx -d nginx

### 第三步: 部署静态页面
- 将vue打包的dist的内容上传到 /data/docker/nginx/www 下面