package com.srp.order.service.impl;

import com.google.gson.Gson;
import com.srp.order.entity.OrderEntity;
import com.srp.order.enums.OrderStatus;
import com.srp.order.exceptions.GeneralException;
import com.srp.order.pojos.Cart;
import com.srp.order.pojos.InventoryOrder;
import com.srp.order.pojos.Order;
import com.srp.order.repository.OrderRepository;
import com.srp.order.service.CartService;
import com.srp.order.service.OrderService;
import com.srp.order.utils.OrderMapper;
import io.smallrye.mutiny.Multi;
import io.smallrye.reactive.messaging.annotations.Broadcast;
import org.eclipse.microprofile.reactive.messaging.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.transaction.Transactional;

@Singleton
public class OrderServiceImpl implements OrderService {

    private final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final CartService cartService;

    private Gson gson = new Gson();

    @Inject
    @Channel("sendProductIdToOutgoing")
    /*
     * 'Emitter{channel:'sendProductIdToOutgoing'}' supports a single downstream consumer, but found 2 [method:'notifyOthers', method:'updateInventory']
     * You may want to enable broadcast using '@Broadcast' on the injected emitter field.
     * */
    @Broadcast
    private Emitter<Long> emitter;

    @Inject
    TransactionManager transactionManager;

    OrderServiceImpl(OrderRepository orderRepository, CartService cartService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
    }

    @Override
    public Long placeOrder(Order order) throws SystemException {
        Assert.isTrue(order != null, "Invalid Parameters");
        Assert.isTrue(order.getUserId() != null, "Invalid Parameters");
        Assert.isTrue(order.getCartId() != null, "Invalid Parameters");
        Assert.isTrue(order.getAddressId() != null, "Invalid Parameters");

        log.debug("Placing nee Order {}", this.gson.toJson(order));

        try {
            transactionManager.begin();
            log.debug("Started Transaction ");
            OrderEntity entity = OrderMapper.getNewEntity(order);
            OrderEntity saved = this.orderRepository.save(entity);
            transactionManager.commit();
            log.debug("Committing Transaction ");

            emitter.send(saved.getId());
            return saved.getId();
        } catch (Exception ex) {
            transactionManager.rollback();
            throw GeneralException.getInstance("Unable to save order: " + ex.getMessage(), ex);
        }
    }


    @Outgoing("newOrderPlaced")
    @Incoming("sendProductIdToOutgoing")
    public Multi<InventoryOrder> updateInventory(Long orderId) {
        log.debug("Event as received for orderId: {}", orderId);
        Order orderDetails = this.getOrderDetails(orderId);
        log.debug("order details: {}", gson.toJson(orderDetails));

        log.debug("getting cart details: {}", orderDetails.getCartId());
        Cart cart = this.cartService.getCart(orderDetails.getCartId());
        log.debug("cart details: {}", gson.toJson(cart));

        log.debug("Sending kafka events");
        return Multi.createFrom().items(cart.getItems().stream()
                .map(x -> new InventoryOrder(x.getProductId(), x.getQuantity(), orderId)));
    }

    //@Outgoing("notification")
    //@Incoming("sendProductIdToOutgoing")
    public Multi<String> notifyOthers(Long orderId) {
        return Multi.createFrom().item("" + orderId);
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
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Order getOrderDetails(Long id) {
        Assert.isTrue(id != null, "Invalid Argument");
        log.debug("get details of orderId: {}", id);
        OrderEntity entity = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found"));
        return OrderMapper.getOrder(entity);
    }
}
