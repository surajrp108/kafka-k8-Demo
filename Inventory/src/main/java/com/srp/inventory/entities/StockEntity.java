package com.srp.inventory.entities;

import com.srp.inventory.enums.StockStatus;

import javax.persistence.*;

@Entity
@Table(name = "stock")
public class StockEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    Long id;

    @Column(name = "supplier_id")
    Long supplierId;

    @Column(name = "product_id")
    Long productId;

    @Column(name = "order_id")
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
