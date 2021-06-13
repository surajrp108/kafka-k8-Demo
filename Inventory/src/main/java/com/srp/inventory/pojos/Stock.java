package com.srp.inventory.pojos;

import com.srp.inventory.enums.StockStatus;

public class Stock {
    private Long id;
    private Long productId;
    private Long quantity;
    private Long supplierId;
    private Long orderId;
    private StockStatus status;

    public Stock(Long id, Long productId, Long quantity, Long supplierId, Long oderId, StockStatus status) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.supplierId = supplierId;
        this.orderId = oderId;
        this.status = status;
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

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public StockStatus getStatus() {
        return status;
    }
}
