package com.srp.product.pojos;

import com.srp.product.enums.ProductCategory;

public class Product {
    public Long id;
    public String name;
    public ProductCategory category;
    public String description;
    public Double price;

    public Product(Long id, String name, ProductCategory category, String description, Double price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
