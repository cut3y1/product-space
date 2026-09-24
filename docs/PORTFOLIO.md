# GitHub 与作品集展示

## 仓库内容

提交 `src/`、`scripts/`、`database/`、`docs/`、`.github/`、Maven 配置和项目说明。`delivery/`、`dist/`、`target/`、Maven 缓存、真实密码及本地配置已由 `.gitignore` 排除。

建议仓库名：`product-space`。建议描述：

> Spring Boot + MySQL 商品管理应用，包含 REST API、Swagger 文档和响应式原生前端。

建议标签：`java`、`spring-boot`、`mysql`、`crud`、`swagger`。

## 发布步骤

1. 先运行构建和集成验收，检查待提交文件中没有密码或本机私有信息。
2. 在 GitHub 新建空仓库；复制实际仓库地址。
3. 在项目根目录执行以下命令，将占位地址替换为自己的仓库地址：

```powershell
git init -b main
git add .
git status
git commit -m "Initial product management application"
git remote add origin https://github.com/YOUR_USERNAME/product-space.git
git push -u origin main
```

项目仓库：https://github.com/cut3y1/product-space 。推送后 GitHub Actions 会编译、运行控制器测试并保存 JAR 构建产物。发布正式展示版本时，可将 `dist/product-crud-submission.zip` 上传至 GitHub Release，不必把二进制文件放入源码历史。

## 演示建议

录制 1–2 分钟演示：商品列表 → 新增 → 搜索 → 修改库存 → Swagger 调试 → 删除。截图使用自建演示数据，避免展示真实账号和密码。说明自己实现的分层设计、BigDecimal 金额处理、字段校验和交付自动化。

当前项目没有指定开源许可证。公开仓库前，可根据自己的授权意愿选择许可证并添加 LICENSE；不要使用未获授权的学校或第三方素材。

