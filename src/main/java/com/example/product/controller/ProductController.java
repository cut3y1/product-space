package com.example.product.controller;

import com.example.product.entity.Product;
import com.example.product.dto.ProductRequest;
import com.example.product.service.ProductService;

import java.net.URI;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/products")
@Tag(name = "商品管理")
public class ProductController {
    private final ProductService service;
    public ProductController(ProductService service) { this.service = service; }
    public record ProductPage(List<Product> content, long totalElements, int totalPages, int page, int size) {}
    @GetMapping @Operation(summary = "分页查询商品", description = "page 从 0 开始；size 为 1–100；keyword 按名称模糊搜索；按 ID 倒序。")
    public ProductPage list(@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        if (page < 0 || size < 1 || size > 100) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "page 必须非负，size 必须在 1–100 之间");
        var result = service.list(keyword, page, size);
        return new ProductPage(result.getContent(), result.getTotalElements(), result.getTotalPages(), page, size);
    }
    @GetMapping("/{id}") @Operation(summary = "查询商品详情")
    public Product get(@PathVariable long id) { return service.get(id); }
    @PostMapping @Operation(summary = "新增商品")
    public ResponseEntity<Product> create(@Valid @RequestBody ProductRequest input) {
        Product product = service.create(input);
        return ResponseEntity.created(URI.create("/api/products/" + product.getId())).body(product);
    }
    @PutMapping("/{id}") @Operation(summary = "修改商品", description = "完整替换商品名称、价格、库存和描述。")
    public Product update(@PathVariable long id, @Valid @RequestBody ProductRequest input) { return service.update(id, input); }
    @DeleteMapping("/{id}") @Operation(summary = "删除商品")
    public ResponseEntity<Void> delete(@PathVariable long id) { service.delete(id); return ResponseEntity.noContent().build(); }
}

