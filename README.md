<h1 align="center">Welcome to JPA用户管理系统 </h1>
<p>
  <img alt="Version" src="https://img.shields.io/badge/version-1.0.0-blue.svg?cacheSeconds=2592000" />
</p>


> 基于SpringBoot JPA构建的RESTFUL API ，实现用户的CRUD操作，支持分页查询，动态排序

## 功能特性

- 基础功能

  ​	用户创建/查询/更新/删除（CRUD）
  ​	分页查询（支持多字段组合排序）

- 安全认证

  ​	密码加密

  ​	用户名/邮箱唯一性约束

- 高级特性

  ​	JPA自动生成SQL

  ​	支持MYSQL数据库

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

## API接口

| 方法   | 端点              | 描述         |
| ------ | ----------------- | ------------ |
| POST   | `/api/users`      | 创建用户     |
| GET    | `/api/users`      | 分页查询用户 |
| GET    | `/api/users/{id}` | 获取单个用户 |
| PUT    | `/api/users/{id}` | 更新用户     |
| DELETE | `/api/users/{id}` | 删除用户     |

## 数据库设计

### 建表语句

```
CREATE TABLE `user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL UNIQUE,
  `password` VARCHAR(60) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

## 项目结构

```
src/main/java/
└── com.csis
    ├── config
    │   └── SecurityConfig.java       # Spring Security密码加密配置
    ├── controller
    │   └── UserController.java       # RESTful API端点定义
    ├── pojo
    │   └── User.java                 # JPA实体类（数据模型）
    ├── repository
    │   └── UserRepository.java       # Spring Data JPA仓库接口
    ├── service
    │   └── UserService.java          # 业务逻辑实现层
    └── Study1UserControllerApplication.java # Spring Boot启动类
```

## 测试案例

<img src="C:\Users\张浩益\AppData\Roaming\Typora\typora-user-images\image-20250323203954668.png" alt="image-20250323203954668" style="zoom:67%;" />