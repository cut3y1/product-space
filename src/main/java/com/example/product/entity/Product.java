package com.example.product.entity;

import com.example.product.dto.ProductRequest;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;
    @Column(nullable = false)
    private Integer stock;
    @Column(length = 1000)
    private String description;

    protected Product() {}
    public Product(ProductRequest input) { apply(input); }
    public void apply(ProductRequest input) {
        name = input.name().trim(); price = input.price(); stock = input.stock();
        description = input.description() == null ? "" : input.description().trim();
    }
    public Long getId() { return id; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public Integer getStock() { return stock; }
    public String getDescription() { return description; }
}

