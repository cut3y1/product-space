# 物集 · 商品管理系统（Product Space）

一个基于 **Spring Boot + MySQL** 的商品管理应用，提供商品增删改查、名称搜索、分页浏览、库存状态展示和 Swagger 在线调试。前端使用原生 HTML / CSS / JavaScript，由后端统一提供服务。

## 功能亮点

- **完整 CRUD**：新增、详情、编辑、删除，删除前二次确认。
- **搜索与分页**：按商品名称模糊搜索，可切换每页条数。
- **库存可视化**：展示当前页库存总量、有货数量以及低库存状态。
- **输入校验**：后端校验名称、金额和库存，返回明确的 HTTP 错误响应。
- **持久化**：MySQL 存储商品；重启应用后保留数据。
- **开箱可用**：可执行 JAR 内置前端和 Swagger UI，无需单独部署前端。

## 技术栈

| 层次 | 技术 |
| --- | --- |
| 运行环境 | Java 21、Maven 3.9、PowerShell 7 |
| 后端 | Spring Boot 3.5.9、Spring MVC、Bean Validation |
| 数据访问 | Spring Data JPA、Hibernate、MySQL（本地验收为 8.1） |
| 前端 | HTML、CSS、JavaScript Fetch API |
| 接口文档 | springdoc-openapi 2.8.15、Swagger UI |
| 验证 | JUnit 5、MockMvc、PowerShell 集成验收脚本 |

## 快速开始

### 1. 准备数据库

启动 MySQL，用自己的账号连接并执行 [database/init.sql](database/init.sql)。脚本仅在数据库不存在时创建 `product_crud`，不会删除现有商品。

默认连接地址为 `localhost:3307`，用户名为 `root`。使用其他端口或账号时，按下面的启动参数修改。

### 2. 构建

在项目根目录使用 PowerShell 7：

```powershell
pwsh -File ./scripts/build.ps1
```

首次构建需要联网下载依赖。脚本会执行测试，并将成品写入 `delivery/`。也可使用标准 Maven 命令 `mvn clean verify`，输出 JAR 位于 `target/`。

### 3. 启动

```powershell
pwsh -File ./scripts/start.ps1
```

按提示输入 MySQL 密码。密码不会写入项目文件。若使用 3306 端口和独立账号：

```powershell
pwsh -File ./scripts/start.ps1 -Username product_user -DatabaseUrl 'jdbc:mysql://localhost:3306/product_crud?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai'
```

| 入口 | 地址 |
| --- | --- |
| 商品工作台 | http://localhost:8080/ |
| Swagger UI | http://localhost:8080/swagger-ui.html |
| OpenAPI JSON | http://localhost:8080/v3/api-docs |

按 Ctrl+C 停止。默认仅监听本机，适用于本地演示。

## 项目结构

```text
product-crud/
├── .github/workflows/ci.yml       # GitHub 构建及单元测试
├── database/init.sql             # MySQL 建库脚本
├── docs/
│   ├── ARCHITECTURE.md            # 分层设计、数据模型、取舍
│   ├── API.md                     # API 使用与错误响应
│   ├── SUBMISSION.md              # 独立交付包运行说明
│   ├── PORTFOLIO.md               # GitHub 发布与演示指南
│   └── openapi.json               # 可导入的接口文档快照
├── scripts/                      # 构建、启动、验收、导出、打包
├── src/main/java/com/example/product/
│   ├── ProductApplication.java
│   ├── controller/               # HTTP 路由和响应
│   ├── dto/                      # 请求结构和校验
│   ├── entity/                   # JPA 数据实体
│   ├── exception/                # 统一异常响应
│   ├── repository/               # 数据库访问
│   └── service/                  # 业务逻辑和事务
├── src/main/resources/
│   ├── application.properties    # 数据源及服务配置
│   └── static/                   # index.html、css/app.css、js/app.js
├── src/test/                     # 后端测试
├── pom.xml
├── delivery/                     # 自动生成，不提交 Git
└── dist/                         # 作业 ZIP，不提交 Git
```

## 更多说明

- [架构与设计](docs/ARCHITECTURE.md)
- [接口说明](docs/API.md)

本项目定位为课程实践和个人作品展示。目前不包含登录鉴权、多用户权限、商品图片上传或生产环境运维配置；数据库表由 Hibernate 自动维护，尚未引入数据库迁移工具。

