package com.srp.inventory.pojos;

import com.srp.inventory.enums.StockStatus;

public class InventoryStock {
    private Long id;
    private Long supplierId;
    private Long productId;
    private Long orderId;
    private StockStatus status = StockStatus.InStock;

    public InventoryStock(Long id, Long supplierId, Long productId, Long orderId, StockStatus status) {
        this.id = id;
        this.supplierId = supplierId;
        this.productId = productId;
        this.orderId = orderId;
        this.status = status;
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
