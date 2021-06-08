package com.srp.inventory.entities;

import com.srp.inventory.enums.StockStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class StockEntity {
    @Id
    @GeneratedValue
    @Column
    Long id;

    @Column
    Long supplierId;

    @Column
    Long productId;

    @Column
    Long orderId;

    @Column
    StockStatus status = StockStatus.InStock;

    public StockEntity() {
    }

    public StockEntity(Long supplierId, Long productId) {
        this.supplierId = supplierId;
        this.productId = productId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public StockStatus getStatus() {
        return status;
    }

    public void setStatus(StockStatus status) {
        this.status = status;
    }
}
