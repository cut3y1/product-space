package com.example.product.dto;



import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

public record ProductRequest(
    @NotBlank @Size(max = 100) @Schema(description = "商品名称", example = "无线鼠标") String name,
    @NotNull @DecimalMin("0.00") @Digits(integer = 10, fraction = 2) @Schema(description = "价格，元，最多两位小数", example = "59.90") BigDecimal price,
    @NotNull @Min(0) @Schema(description = "库存数量", example = "100") Integer stock,
    @Size(max = 1000) @Schema(description = "商品描述", example = "静音按键，USB 接收器") String description
) {}

