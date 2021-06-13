package com.srp.inventory.utils;

import com.srp.inventory.entities.StockEntity;
import com.srp.inventory.pojos.Stock;

public class StockMapper {

    public static Stock getStock(StockEntity source){
        return new Stock(source.getId(),source.getProductId(), 1L, source.getSupplierId(), source.getOrderId(), source.getStatus());
    }
}
