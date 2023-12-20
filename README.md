## 🐾 项目介绍

本项目主要讲解使用SpringBoot集成各种中间件

配套文档：[SpringBoot集成中间件-笑小枫](https://blog.csdn.net/qq_34988304/category_12424073.html)

SpringBoot版本：2.7.12

## 🍁 项目一览

### 🧐数据库相关

* [用户自定义数据库源](/maple-input-db)

启动项目无默认数据源，项目启动后，用户通过画面输入数据库信息，然后连接数据库。

* [数据库初始化](/maple-flyway)

项目启动时，自动初始化数据库表结构及数据，可以维护数据库版本，表结构变更时，其他环境可以自动通过项目配置进行更新。

* [多数据源](/maple-dynamic-datasource)

项目配置多个数据源，然后根据配置的数据源访问不同数据库的数据。

* [整合Redis](/maple-redis)

项目整合Redis、Redisson，让你在SpringBoot中使用Redisson更加方便。

* [整合Lucene](/maple-lucene)

项目整合Lucene，实现全文检索，搜索内容高亮展示。通过多个实例详细演示了索引操作，和多种模式搜索。

* [整合RabbitMQ](/maple-mq-rabbit)

项目整合RabbitMQ消息队列，以案例的形式展示了不同模式的交换机实现过程，演示了死信队列和延时队列应该怎么实现。