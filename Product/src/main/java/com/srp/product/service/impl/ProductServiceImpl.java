package com.srp.product.service.impl;

import com.google.gson.Gson;
import com.srp.product.entity.ProductEntity;
import com.srp.product.enums.ProductCategory;
import com.srp.product.exceptions.GeneralException;
import com.srp.product.pojos.Product;
import com.srp.product.repository.ProductRepository;
import com.srp.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Singleton
public class ProductServiceImpl implements ProductService {

    ProductRepository repository;
    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final Gson gson = new Gson();

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Long save(Product product) {
        log.info("save: {}", gson.toJson(product));
        ProductEntity entity = new ProductEntity(product.getId(), product.getName(), product.getDescription(), product.getCategory(), product.getPrice());
        ProductEntity save = repository.save(entity);
        return save.getId();
    }

    @Override
    public Product get(Long id) {
        log.info("get: {}", id);
        Optional<ProductEntity> entityOptional = repository.findById(id);
        if (entityOptional.isPresent()) {
            ProductEntity entity = entityOptional.get();
            return new Product(entity.getId(), entity.getName(), entity.getCategory(), entity.getDescription(), entity.getPrice());
        }
        return null;
    }

    @Override
    public List<Product> getAll() {
        log.info("getAll: null");
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(x -> new Product(x.getId(), x.getName(), x.getCategory(), x.getDescription(), x.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean delete(Long id) {
        try{
            log.info("delete: {}", id);
            repository.deleteById(id);
        }
        catch (Exception ex){
            throw GeneralException.getInstance("Unable to delete id " + id);
        }

        return true;
    }

    @Override
    public Long update(Product product) {
        Assert.isTrue(product != null, "Product cannot be null");
        Assert.isTrue(product.getId() != null, "Product id cannot be null");

        log.info("update: {}", gson.toJson(product));
        Optional<ProductEntity> entityOptional = repository.findById(product.getId());
        if (entityOptional.isPresent()) {
            ProductEntity entity = entityOptional.get();
            entity.setCategory(product.getCategory());
            entity.setDescription(product.getDescription());
            entity.setName(product.getName());
            entity.setPrice(product.getPrice());

            ProductEntity saved = repository.save(entity);
            return saved.getId();
        }
        throw GeneralException.getInstance("Product does not exist");
    }
}
