package com.srp.order.service.impl;

import com.srp.order.entity.OrderEntity;
import com.srp.order.enums.OrderStatus;
import com.srp.order.pojos.Order;
import com.srp.order.repository.OrderRepository;
import com.srp.order.service.OrderService;
import com.srp.order.utils.OrderMapper;
import org.springframework.util.Assert;

import javax.inject.Singleton;

@Singleton
public class OrderServiceImpl implements OrderService {


    private final OrderRepository orderRepository;

    OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Long placeOrder(Order order) {
        Assert.isTrue(order != null, "Invalid Parameters");
        Assert.isTrue(order.getUserId() != null, "Invalid Parameters");
        Assert.isTrue(order.getCartId() != null, "Invalid Parameters");
        Assert.isTrue(order.getAddressId() != null, "Invalid Parameters");

        OrderEntity entity = OrderMapper.getNewEntity(order);
        OrderEntity saved = this.orderRepository.save(entity);
        return saved.getId();
    }

    @Override
    public boolean deleteOrder(Long id) {
        Assert.isTrue(id != null, "Invalid Parameters");
        OrderEntity orderEntity = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No record found"));
        orderEntity.setStatus(OrderStatus.Cancelled);
        orderRepository.save(orderEntity);
        return true;
    }

    @Override
    public Order getOrderDetails(Long id) {
        Assert.isTrue(id != null, "Invalid Argument");
        OrderEntity entity = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found"));
        return OrderMapper.getOrder(entity);
    }
}
