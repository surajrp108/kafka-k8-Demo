package com.srp.inventory.service;

import com.srp.inventory.pojos.OrderStock;
import com.srp.inventory.pojos.Stock;

import java.util.List;

public interface InventoryService {
    public Long getStockCount(Long productId);
    public Long addStock(Stock stock);
    public Long addInOrderStock(OrderStock stock);
    void markDelivered(List<Long> orderId);
    void markOrderCancelled(List<Long> orderIds);
}
