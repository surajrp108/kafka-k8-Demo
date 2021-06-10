package com.srp.inventory.service.impl;

import com.google.gson.Gson;
import com.srp.inventory.entities.StockEntity;
import com.srp.inventory.enums.StockStatus;
import com.srp.inventory.pojos.InventoryStock;
import com.srp.inventory.pojos.OrderStock;
import com.srp.inventory.pojos.Stock;
import com.srp.inventory.repository.StockRepository;
import com.srp.inventory.service.InventoryService;
import com.srp.inventory.utils.Assert;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Singleton
public class InventoryServiceImpl implements InventoryService {

    private static Logger log = LoggerFactory.getLogger(InventoryServiceImpl.class);

    private Gson gson = new Gson();

    StockRepository stockRepository;

    InventoryServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public Uni<Long> getStockCount(Long productId) {
        return stockRepository.countOfProductAvailable(productId);
    }

    @Override
    public void addStock(Stock stock) {
        Assert.isTrue(stock != null, "Invalid Stock");
        Assert.isTrue(stock.getProductId() != null, "Invalid Stock");
        Assert.isTrue(stock.getSupplierId() != null, "Invalid Stock");
        Assert.isTrue(stock.getQuantity() != null, "Invalid Stock");

        log.debug("Adding new stock {}", gson.toJson(stock));

        List<StockEntity> stocks = IntStream.range(0, stock.getQuantity().intValue())
                .mapToObj(x -> new StockEntity(stock.getSupplierId(), stock.getProductId()))
                .collect(Collectors.toList());
        stockRepository.persist(stocks);
        log.debug("Stock added");
    }

    @Override
    @Incoming("newOrderPlaced")
    public void addInOrderStock(OrderStock stock) {
        Assert.isTrue(stock != null, "Invalid Stock");
        Assert.isTrue(stock.getProductId() != null, "Invalid Stock");
        Assert.isTrue(stock.getOrderId() != null, "Invalid Stock");
        Assert.isTrue(stock.getQuantity() != null, "Invalid Stock");

        log.info("Updating order details for each stock {}", gson.toJson(stock));
        stockRepository.findByProductIdAndOrderIdIsNull(stock.getProductId(), stock.getQuantity().intValue())
                .invoke(res -> {
                    Stream<StockEntity> entityStream = res.stream().peek(x -> {
                        x.setOrderId(stock.getOrderId());
                        x.setStatus(StockStatus.BookedForOrder);
                    });
                    stockRepository.persist(entityStream);
                    log.info("Updating of Stock has been done");
                });

    }

    @Override
    public Multi<InventoryStock> getAllStockDetails() {
        return stockRepository.findAll().stream().onItem()
                .transform(x -> new InventoryStock(x.getId(), x.getSupplierId(), x.getProductId(), x.getOrderId(), x.getStatus()));

    }

    @Override
    public void markDelivered(List<Long> orderIds) {
        this.markOrder(orderIds, x -> x.setStatus(StockStatus.Delivered));
    }

    @Override
    public void markOrderCancelled(List<Long> orderIds) {
        this.markOrder(orderIds, x -> {
            x.setStatus(StockStatus.InStock);
            x.setOrderId(null);
        });
    }

    private void markOrder(List<Long> orderIds, Consumer<StockEntity> action) {
        Assert.isTrue(orderIds != null && !orderIds.isEmpty(), "Invalid arguments");
        stockRepository.findByOrderIdIn(orderIds)
                .invoke(res -> {
                    Stream<StockEntity> entityStream = res.stream().peek(action);
                    stockRepository.persist(entityStream);
                });
    }

}
