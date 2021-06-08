package com.srp.product.service;

import com.srp.product.pojos.Product;

import java.util.List;

public interface ProductService {
    public Long save(Product product);
    public Product get(Long id);
    public List<Product> getAll();

    boolean delete(Long id);

    Long update(Product product);
}
