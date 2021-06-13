package com.srp.order.service.impl;

import com.google.gson.Gson;
import com.srp.order.entity.OrderEntity;
import com.srp.order.enums.OrderStatus;
import com.srp.order.pojos.Cart;
import com.srp.order.pojos.Order;
import com.srp.order.pojos.OrderStock;
import com.srp.order.repository.OrderRepository;
import com.srp.order.restclient.InventoryClient;
import com.srp.order.service.CartService;
import com.srp.order.service.OrderService;
import com.srp.order.utils.OrderMapper;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.Arrays;

@Singleton
public class OrderServiceImpl implements OrderService {

    private final Logger log = LoggerFactory.getLogger(CartServiceImpl.class);
    private final Gson gson = new Gson();

    private final OrderRepository orderRepository;
    private final CartService cartService;

    @RestClient
    @Inject
    InventoryClient inventoryClient;

    OrderServiceImpl(OrderRepository orderRepository, CartService cartService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
    }

    @Override
    @Transactional
    public Long placeOrder(Order order) {
        Assert.isTrue(order != null, "Invalid Parameters");
        Assert.isTrue(order.getUserId() != null, "Invalid Parameters");
        Assert.isTrue(order.getCartId() != null, "Invalid Parameters");
        Assert.isTrue(order.getAddressId() != null, "Invalid Parameters");

        log.info("placeOrder: {}", gson.toJson(order));

        OrderEntity entity = OrderMapper.getNewEntity(order);
        OrderEntity saved = this.orderRepository.save(entity);
        this.updateInventory(saved.getId());
        return saved.getId();
    }

    private void updateInventory(Long orderId) {
        log.info("updateInventory: {}", orderId);
        this.orderRepository.findById(orderId)
                .ifPresent(order -> {
                    Cart cart = this.cartService.getById(order.getCartId());
                    cart.getItems().forEach(item -> {
                        inventoryClient.prepareStockForOrder(new OrderStock(item.getProductId(), item.getQuantity(), orderId));
                    });
                });
    }

    @Override
    public boolean deleteOrder(Long id) {
        Assert.isTrue(id != null, "Invalid Parameters");
        log.info("deleteOrder: {}", id);

        OrderEntity orderEntity = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No record found"));
        orderEntity.setStatus(OrderStatus.Cancelled);
        orderRepository.save(orderEntity);

        inventoryClient.cancelOrder(Arrays.asList(id));
        return true;
    }

    @Override
    public Order getOrderDetails(Long id) {
        Assert.isTrue(id != null, "Invalid Argument");
        log.info("getOrderDetails: {}", id);

        OrderEntity entity = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found"));
        return OrderMapper.getOrder(entity);
    }
}
