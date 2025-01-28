# 快速入门

## 1. 基本接口功能验证
### 1） 服务健康检查
```shell
curl -v http://127.0.0.1:8080/balance/v1/health
```
若服务正常启动则返回`200`状态码。
### 2） 预置测试用户数据
```shell
curl -H "Content-Type: application/json" http://127.0.0.1:8080/balance/v1/init-account -d '{"account": "user1", "balance": 10000}'
curl -H "Content-Type: application/json" http://127.0.0.1:8080/balance/v1/init-account -d '{"account": "user2", "balance": 20002}'
```

### 3) 查询预置的用户数据
测试用户1：
```shell
curl -v http://127.0.0.1:8080/balance/v1/query-account?account=user1
```
返回：

```
{"account":"user1","balance":10000.0}
```
测试用户2：
```shell
curl -v http://127.0.0.1:8080/balance/v1/query-account?account=user2
```
返回：
```
{"account":"user2","balance":20000.0}
```

### 4) 用户之间转账交易
```shell
curl -H "Content-Type: application/json" http://127.0.0.1:8080/balance/v1/transaction -d '{"sourceAccount": "user2", "destAccount": "user1", "amount": 5000}'
```
响应：
```shell
{"transactionId":"699cba4b-abae-4cbf-9c78-f13a57c65218","sourceAccount":"user2","destAccount":"user1","amount":5000.0,"timestamp":"2025-01-28T15:25:17.542337286"}
```
查询`user1`用户信息：
```shell
curl -v http://127.0.0.1:8080/balance/v1/query-account?account=user1
```
响应：
```
{"account":"user1","balance":15000.0}
```
查询`user2`用户信息
```shell
curl -v http://127.0.0.1:8080/balance/v1/query-account?account=user2
```
响应：
```
{"account":"user2","balance":15000.0}
```