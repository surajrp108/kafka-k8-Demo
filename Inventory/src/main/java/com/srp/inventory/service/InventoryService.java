package com.srp.inventory.service;

import com.srp.inventory.pojos.InventoryStock;
import com.srp.inventory.pojos.OrderStock;
import com.srp.inventory.pojos.Stock;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface InventoryService {
    Uni<Long> getStockCount(Long productId);
    Uni<Void> addStock(Stock stock);
    void addInOrderStock(OrderStock stock);
    Multi<InventoryStock> getAllStockDetails();

    void markDelivered(List<Long> orderId);
    void markOrderCancelled(List<Long> orderIds);
}
