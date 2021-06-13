package com.srp.inventory.service.impl;

import com.google.gson.Gson;
import com.srp.inventory.entities.StockEntity;
import com.srp.inventory.enums.StockStatus;
import com.srp.inventory.pojos.OrderStock;
import com.srp.inventory.pojos.Stock;
import com.srp.inventory.repository.StockRepository;
import com.srp.inventory.service.InventoryService;
import com.srp.inventory.utils.StockMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.Assert;

import javax.inject.Singleton;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

@Singleton
public class InventoryServiceImpl implements InventoryService {

    StockRepository stockRepository;

    private final Logger log = LoggerFactory.getLogger(InventoryServiceImpl.class);
    private final Gson gson = new Gson();

    InventoryServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public Long getStockCount(Long productId) {
        log.info("getStockCount: {}", productId);
        return stockRepository.countOfProductAvailable(productId);
    }

    @Override
    public Long addStock(Stock stock) {
        Assert.isTrue(stock != null, "Invalid Stock");
        Assert.isTrue(stock.getProductId() != null, "Invalid Stock");
        Assert.isTrue(stock.getSupplierId() != null, "Invalid Stock");
        Assert.isTrue(stock.getQuantity() != null, "Invalid Stock");

        log.info("addStock: {}", gson.toJson(stock));
        List<StockEntity> stocks = IntStream.range(0, stock.getQuantity().intValue())
                .mapToObj(x -> new StockEntity(stock.getSupplierId(), stock.getProductId()))
                .collect(Collectors.toList());

        stockRepository.saveAll(stocks);
        return (long) stocks.size();
    }

    @Override
    public Long addInOrderStock(OrderStock stock) {
        Assert.isTrue(stock != null, "Invalid Stock");
        Assert.isTrue(stock.getProductId() != null, "Invalid Stock");
        Assert.isTrue(stock.getOrderId() != null, "Invalid Stock");
        Assert.isTrue(stock.getQuantity() != null, "Invalid Stock");

        log.info("addInOrderStock: {}", gson.toJson(stock));

        List<StockEntity> stockEntityList = stockRepository.findByProductIdAndOrderIdIsNull(stock.getProductId(), PageRequest.of(0, stock.getQuantity().intValue()))
                .peek(x -> {
                    x.setOrderId(stock.getOrderId());
                    x.setStatus(StockStatus.BookedForOrder);
                }).collect(Collectors.toList());

        stockRepository.saveAll(stockEntityList);
        return (long) stockEntityList.size();
    }

    @Override
    public void markDelivered(List<Long> orderIds) {
        log.info("markDelivered: {}", gson.toJson(orderIds));
        this.markOrder(orderIds, x -> {
            x.setStatus(StockStatus.Delivered);
        });
    }

    @Override
    public void markOrderCancelled(List<Long> orderIds) {
        log.info("markOrderCancelled: {}", gson.toJson(orderIds));
        this.markOrder(orderIds, x -> {
            x.setStatus(StockStatus.InStock);
            x.setOrderId(null);
        });
    }

    @Override
    public List<Stock> getAllStock() {
        return StreamSupport.stream(stockRepository.findAll().spliterator(), false)
                .map(StockMapper::getStock)
                .collect(Collectors.toList());
    }

    private void markOrder(List<Long> orderIds, Consumer<StockEntity> action) {
        Assert.isTrue(orderIds != null && !orderIds.isEmpty(), "Invalid arguments");
        List<StockEntity> stockEntityList = stockRepository.findByOrderIdIn(orderIds)
                .peek(action)
                .collect(Collectors.toList());
        stockRepository.saveAll(stockEntityList);
    }
}
