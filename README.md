# 实时金融转账服务

一个基于SpringBoot的微服务，用于实现两个账户之间实时金融交易。

- 服务构建，参考[文档](doc/build.md)
- 服务部署，参考[文档](doc/deployment.md)
- 快速开始，验证基本功能，参考[文档](doc/quick_start.md)

## 1. 微服务目标：
- 实现实时处理金融交易以及更新账户余额的能力
- 每一笔交易包含唯一的交易ID、源账号、目标账号、交易金额和时间戳信息
- 服务需要同步处理交易和账户余额信息

## 2. 高可用和弹性伸缩
- 服务使用`helm`部署在阿里云的ACK集群中，[部署配置](/deployment/charts_template)
- 使用阿里云ACK集群部署HPA支持水平扩展和伸缩
- 使用阿里云的RDS云数据库
- 代码中使用了Java的`Resilience`库来保证请求失败的情况下重试机制
- 在金融交易接口实现中使用了数据库事务保证了数据一致性

## 3. 性能
- TODO: 使用阿里云Redis分布式缓存服务来保证服务性能

## 4. 测试
- 代码包含完备的单元测试，能够覆盖主要代码逻辑（见[代码路径](/src/test/))
- 代码包含集成测试，能够端到端测试Spring应用的流程（数据库层mock了, 见[代码路径](/src/test/java/com/hsbc/rbcs/integration))
- - 代码包含简单的性能测试（数据库层mock了, 见[代码路径](/src/test/java/com/hsbc/rbcs/performance))

## 5. 接口设计
- 代码使用openapi 3.0的yaml格式定义接口，生成接口`controller`层代码，以及对象模型
- 代码使用领域驱动设计风格（DDD）实现