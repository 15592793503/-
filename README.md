<h1 align="center">Welcome to 商城库存管理系统 </h1>
<p>
  <img alt="Version" src="https://img.shields.io/badge/version-1.0.0-blue.svg?cacheSeconds=2592000" />
</p>



> 一个基于Spring Boot的高并发库存管理系统，演示了乐观锁（@Version）和悲观锁（@Lock）的实现方案

## 功能特性

- 基础功能

  - 商品库存的创建与查询
  - 高并发安全库存扣减（支持乐观锁/悲观锁）
  - 库存防超卖机制

- 技术亮点

  - 双锁机制实现：`@Version` + `SELECT FOR UPDATE`
  - Spring Retry自动重试（乐观锁冲突时）
  - OpenAPI 3.0文档集成
  - JMeter压力测试方案

- 

  ## 技术栈


| 组件         | 技术实现              |
| ------------ | --------------------- |
| 框架         | Spring Boot 3.4.4     |
| 数据持久化   | Spring Data JPA       |
| 密码加密     | BCryptPasswordEncoder |
| 数据库       | MySQL 8.0             |
| API 测试工具 | Postman               |

## 快速开始

### 环境要求

- JDK22
- Maven 9.0
- MySQL 8.0

### 项目部署

1.git克隆仓库

```
git clone https://github.com/15592793503/-.git
```

2.配置数据库

```
spring.datasource.url=jdbc:mysql://localhost:3306/study
spring.datasource.username=root
spring.datasource.password=020924
spring.jpa.hibernate.ddl-auto=update
```

## 数据库设计

### 建表语句

```
CREATE TABLE IF NOT EXISTS product (
    id     BIGINT UNSIGNED  NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    name      VARCHAR(100)  NOT NULL COMMENT '商品名称',
    stock_quantity INT      NOT NULL DEFAULT 0 COMMENT '库存数量',
    version        INT      NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
    PRIMARY KEY (id),
    INDEX idx_name (name)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';
```

## 项目结构

```
src/main/java/
└── com.csis
    ├── config
    │   └── Knife4jConfig.java          #日志配置
    ├── controller
    │   └── ProductController.java      # RESTful API端点定义
    ├── pojo
    │   └── Product.java                # JPA实体类（数据模型）
    ├── repository
    │   └── ProductRepository.java   # Spring Data JPA仓库接口
    ├── service
    │   └── ProductService.java         # 业务逻辑实现层
    └── Study3ShoppingCenterApplication.java # Spring Boot启动类
```

