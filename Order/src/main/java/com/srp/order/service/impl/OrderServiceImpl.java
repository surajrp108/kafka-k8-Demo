package com.srp.order.service.impl;

import com.srp.order.entity.OrderEntity;
import com.srp.order.enums.OrderStatus;
import com.srp.order.exceptions.GeneralException;
import com.srp.order.pojos.Order;
import com.srp.order.repository.OrderRepository;
import com.srp.order.service.OrderService;
import com.srp.order.utils.OrderMapper;
import io.smallrye.mutiny.Multi;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.springframework.util.Assert;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;

@Singleton
public class OrderServiceImpl implements OrderService {


    private final OrderRepository orderRepository;

    @Inject
    @Channel("sendProductIdToOutgoing")
    private Emitter<String> emitter;

    OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public Long placeOrder(Order order) {
        Assert.isTrue(order != null, "Invalid Parameters");
        Assert.isTrue(order.getUserId() != null, "Invalid Parameters");
        Assert.isTrue(order.getCartId() != null, "Invalid Parameters");
        Assert.isTrue(order.getAddressId() != null, "Invalid Parameters");

        OrderEntity entity = OrderMapper.getNewEntity(order);
        OrderEntity saved = this.orderRepository.save(entity);
        emitter.send(""+saved.getId());
        return saved.getId();
    }

    @Outgoing("notification")
    @Incoming("sendProductIdToOutgoing")
    public Multi<String> notifyOthers(Long productId) {
        return Multi.createFrom().item(""+productId);
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
