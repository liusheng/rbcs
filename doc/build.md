# 服务构建

## 1. 构建
前提条件：
- JDK-17
- Docker-27.2.0

命令：
```shell
mvn clean package
```

## 2. 容器镜像构建
命令：
```shell
docker build . -t rbcs
```