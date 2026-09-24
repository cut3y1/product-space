# API 使用说明

根路径：`http://localhost:8080/api/products`。完整模型与参数见 [OpenAPI 文档](openapi.json)，在线调试访问 `/swagger-ui.html`。

| 方法 | 路径 | 成功状态 | 功能 |
| --- | --- | --- | --- |
| GET | `/api/products?keyword=鼠标&page=0&size=10` | 200 | 搜索分页 |
| GET | `/api/products/{id}` | 200 | 商品详情 |
| POST | `/api/products` | 201 | 新增，Location 响应头指向新商品 |
| PUT | `/api/products/{id}` | 200 | 完整修改商品 |
| DELETE | `/api/products/{id}` | 204 | 删除，无响应体 |

POST/PUT 使用 `Content-Type: application/json`：

```json
{"name":"无线鼠标","price":59.90,"stock":100,"description":"静音按键"}
```

名称必填、最长 100 字符；价格非负，最多 10 位整数和 2 位小数；库存非负整数；描述最长 1000 字符。PUT 需要完整传入名称、价格和库存；省略描述会清空描述。

分页响应包含 `content`、`totalElements`、`totalPages`、`page`、`size`。无结果时 content 为空数组。商品不存在返回 404，参数校验或 JSON 格式错误返回 400，例如：

```json
{"status":404,"message":"商品不存在","errors":{}}
```

校验失败时 `errors` 包含字段名与原因。接口未设置认证，仅用于本地演示。OpenAPI 快照由应用导出；修改接口后重新运行 `scripts/export-openapi.ps1`。
