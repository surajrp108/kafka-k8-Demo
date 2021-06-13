package com.srp.inventory.service;

import com.srp.inventory.pojos.OrderStock;
import com.srp.inventory.pojos.Stock;

import java.util.List;

public interface InventoryService {
    Long getStockCount(Long productId);
    Long addStock(Stock stock);
    Long addInOrderStock(OrderStock stock);
    void markDelivered(List<Long> orderId);
    void markOrderCancelled(List<Long> orderIds);

    List<Stock> getAllStock();
}
