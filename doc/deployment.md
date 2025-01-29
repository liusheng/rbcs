# 服务部署

---

## 本地`Docker`容器部署验证基本功能
### 0本地部署`mysql`
```shell
apt-get update
apt-get install mysql-server
```
### 1 创建数据库并设置访问权限
```sql
CREATE DATABASE rbcs;
CREATE USER 'rbcs'@'%' IDENTIFIED WITH mysql_native_password BY 'rbcs';
GRANT ALL PRIVILEGES ON *.* TO 'rbcs'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;
```
### 2 构建容器镜像
见[构建文档](!./build.md)描述

### 3 本地运行Docker容器
```shell
docker run -p 8080:8080 --name rbcs -e DB_URL="jdbc:mysql://172.29.33.213:3306/rbcs" \
 -e DB_USERNAME="rbcs" -e DB_PASSWORD="rbcs"  \
 -it crpi-u4kl0nx0bi63pklq.cn-hangzhou.personal.cr.aliyuncs.com/seanlau2019/rbcs:0.1
```
---

## 使用阿里云ACK服务部署

## 0 购买阿里云ACK集群
参考阿里云[文档](https://csnew.console.aliyun.com/#/k8s), 并为ACK集群 `API server`绑定公网IP，以便于外部访问。

## 1 本地安装`kubectl`

```shell
curl -LO "https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/amd64/kubectl"
chmod +x ./kubectl
sudo mv ./kubectl /usr/local/bin/kubectl
```
## 2 在阿里云购买资源
- 在阿里云控制台购买ACK集群，并扩容节点。在*链接信息*中拷贝公网访问集群凭证，并将其保存在本地的`$HOME/.kube/config`路径中
- 在阿里云`云数据库`服务页面购买MySQL数据库示例，创建数据库以及登录用户名，并将用户名、登录密码、链接URL配置到`deployment/charts_template/values.yaml`中

## 3 配置K8s集群访问阿里云的容器镜像访问
```shell
kubectl -n hsbc create secret docker-registry acr-secret \
  --docker-server=crpi-u4kl0nx0bi63pklq.cn-hangzhou.personal.cr.aliyuncs.com  \
   --docker-username=seanlau2019  \
    --docker-password=<your-acr-password>
```

## 4. 创建命名空间

```shell
kubectl create namespace hsbc
```

## 5. 使用`helm`部署服务
```shell
cd deployment/charts_template
helm install rbcs-service --namespace hsbc . -f values.yaml
```