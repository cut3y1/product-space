package com.example.product.service;

import com.example.product.entity.Product;
import com.example.product.dto.ProductRequest;
import com.example.product.repository.ProductRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.*;

@Service
@Transactional
public class ProductService {
    private final ProductRepository repository;
    public ProductService(ProductRepository repository) { this.repository = repository; }
    @Transactional(readOnly = true)
    public Page<Product> list(String keyword, int page, int size) {
        return repository.findByNameContainingIgnoreCase(keyword.trim(), PageRequest.of(page, size, Sort.by("id").descending()));
    }
    @Transactional(readOnly = true)
    public Product get(long id) { return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "商品不存在")); }
    public Product create(ProductRequest input) { return repository.save(new Product(input)); }
    public Product update(long id, ProductRequest input) { Product product = get(id); product.apply(input); return repository.save(product); }
    public void delete(long id) { repository.delete(get(id)); }
}

