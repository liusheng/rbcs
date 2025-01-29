# 服务构建

## 0. 构建
前提条件：
- JDK-17
- Docker-27.2.0

命令：
```shell
mvn clean package
```

## 1. 容器镜像构建
命令：
```shell
docker build . -t crpi-u4kl0nx0bi63pklq.cn-hangzhou.personal.cr.aliyuncs.com/seanlau2019/rbcs:0.1
```
## 2. 推送容器镜像到阿里云的镜像仓库
登录阿里云镜像仓库：
```shell
sudo docker login --username=seanlau2019 crpi-u4kl0nx0bi63pklq.cn-hangzhou.personal.cr.aliyuncs.com
```
推送镜像
```shell
docker push crpi-u4kl0nx0bi63pklq.cn-hangzhou.personal.cr.aliyuncs.com/seanlau2019/rbcs:0.1
```