package com.srp.order.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class ItemEntity {
    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column
    private Long productId;

    @Column
    private Long quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cartId")
    private CartEntity cart;

    public ItemEntity() {
    }

    public ItemEntity(Long productId, Long quantity, CartEntity cart) {
        this.productId = productId;
        this.quantity = quantity;
        this.cart = cart;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public CartEntity getCart() {
        return cart;
    }

    public void setCart(CartEntity cart) {
        this.cart = cart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemEntity that = (ItemEntity) o;
        return Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }

    public boolean addQuantity(long quantity) {
        this.quantity += quantity;
        return true;
    }
}
