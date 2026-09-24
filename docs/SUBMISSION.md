# 商品管理应用 · 交付运行说明

## 文件清单

| 文件 | 用途 |
| --- | --- |
| product-crud.jar | 可执行后端，内置前端和 Swagger UI |
| openapi.json | Swagger / OpenAPI 文档，可导入接口工具 |
| index.html | 内联样式和脚本的前端成品 |
| init.sql | MySQL 建库脚本 |
| start.ps1 | PowerShell 7 启动脚本 |
| README.md | 本说明 |

## 运行

1. 解压整个提交包，准备 Java 21、MySQL、PowerShell 7。
2. 在 MySQL 中执行 `init.sql`；该脚本不会删除现有数据库。应用启动后自动创建商品表。
3. 在解压目录执行 `pwsh -File ./start.ps1`，按提示输入 MySQL 密码。
4. 打开 http://localhost:8080/ 使用商品管理；Swagger 地址为 http://localhost:8080/swagger-ui.html。

默认 MySQL 为 `localhost:3307`，用户 `root`。如需修改：

```powershell
pwsh -File ./start.ps1 -Username your_user -DatabaseUrl 'jdbc:mysql://localhost:3306/product_crud?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai'
```

数据库密码也可通过 `MYSQL_PASSWORD` 环境变量提供。没有 PowerShell 时，设置 `MYSQL_URL`、`MYSQL_USERNAME`、`MYSQL_PASSWORD` 后执行 `java -jar product-crud.jar`。应用默认监听本机 8080，端口冲突时可以通过 Java 启动参数 `--server.port=8081` 修改。

`index.html` 是前端交付件；请使用后端提供的 HTTP 入口操作数据，不通过 file:// 双击模式调用接口。无需 Maven、Node.js 或联网下载前端依赖。

## 建议验收流程

1. 新增一个商品，填写名称、价格和库存。
2. 搜索该商品，打开编辑弹窗，修改价格和库存并保存。
3. 刷新页面，确认修改保留。
4. 在 Swagger 中尝试负数价格等非法输入，确认返回 400。
5. 删除测试商品，查询其 ID 确认返回 404。
6. 保留一条自建数据，停止并重新启动应用，确认数据库持久化。

按 Ctrl+C 退出。出现数据库连接错误时，检查 MySQL 服务、端口、数据库名称、账号密码和账号权限。
