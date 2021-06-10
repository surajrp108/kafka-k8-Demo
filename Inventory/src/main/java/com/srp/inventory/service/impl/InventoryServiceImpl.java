package com.srp.inventory.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JacksonUtils;
import com.google.gson.Gson;
import com.srp.inventory.entities.StockEntity;
import com.srp.inventory.enums.StockStatus;
import com.srp.inventory.pojos.InventoryStock;
import com.srp.inventory.pojos.OrderStock;
import com.srp.inventory.pojos.Stock;
import com.srp.inventory.repository.StockRepository;
import com.srp.inventory.service.InventoryService;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.Assert;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Singleton
public class InventoryServiceImpl implements InventoryService {

    private static Logger log = LoggerFactory.getLogger(InventoryServiceImpl.class);

    private Gson gson = new Gson();

    StockRepository stockRepository;

    InventoryServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public Long getStockCount(Long productId) {
        return stockRepository.countOfProductAvailable(productId);
    }

    @Override
    public Long addStock(Stock stock) {
        Assert.isTrue(stock != null, "Invalid Stock");
        Assert.isTrue(stock.getProductId() != null, "Invalid Stock");
        Assert.isTrue(stock.getSupplierId() != null, "Invalid Stock");
        Assert.isTrue(stock.getQuantity() != null, "Invalid Stock");

        log.debug("Adding new stock {}", gson.toJson(stock));

        List<StockEntity> stocks = IntStream.range(0, stock.getQuantity().intValue())
                .mapToObj(x -> new StockEntity(stock.getSupplierId(), stock.getProductId()))
                .collect(Collectors.toList());
        stockRepository.saveAll(stocks);

        log.debug("Stock added");
        return (long) stocks.size();
    }

    @Override
    @Incoming("newOrderPlaced")
    public Long addInOrderStock(OrderStock stock) {
        Assert.isTrue(stock != null, "Invalid Stock");
        Assert.isTrue(stock.getProductId() != null, "Invalid Stock");
        Assert.isTrue(stock.getOrderId() != null, "Invalid Stock");
        Assert.isTrue(stock.getQuantity() != null, "Invalid Stock");

        log.info("Updating order details for each stock {}", gson.toJson(stock));
        List<StockEntity> stockEntityList = stockRepository.findByProductIdAndOrderIdIsNull(stock.getProductId(), PageRequest.of(0, stock.getQuantity().intValue()))
                .peek(x -> {
                    x.setOrderId(stock.getOrderId());
                    x.setStatus(StockStatus.BookedForOrder);
                }).collect(Collectors.toList());
        stockRepository.saveAll(stockEntityList);
        log.info("Updating of Stock has been done");
        return (long) stockEntityList.size();
    }

    @Override
    public List<InventoryStock> getAllStockDetails() {
        return StreamSupport.stream(stockRepository.findAll().spliterator(),false)
                .map(x-> new InventoryStock(x.getId(), x.getSupplierId(), x.getProductId(), x.getOrderId(), x.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public void markDelivered(List<Long> orderIds) {
        this.markOrder(orderIds, x -> {
            x.setStatus(StockStatus.Delivered);
        });
    }

    @Override
    public void markOrderCancelled(List<Long> orderIds) {
        this.markOrder(orderIds, x -> {
            x.setStatus(StockStatus.InStock);
            x.setOrderId(null);
        });
    }

    private void markOrder(List<Long> orderIds, Consumer<StockEntity> action)
    {
        Assert.isTrue(orderIds != null && !orderIds.isEmpty(), "Invalid arguments");
        List<StockEntity> stockEntityList = stockRepository.findByOrderIdIn(orderIds)
                .peek(action)
                .collect(Collectors.toList());
        stockRepository.saveAll(stockEntityList);
    }
}
