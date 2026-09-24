package com.example.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "商品管理 API", version = "1.0.0", description = "商品新增、分页查询、详情、修改和删除。价格单位为元。"))
public class ProductApplication {
    public static void main(String[] args) { SpringApplication.run(ProductApplication.class, args); }
}
