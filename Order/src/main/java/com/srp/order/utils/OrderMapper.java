package com.srp.order.utils;

import com.srp.order.entity.OrderEntity;
import com.srp.order.enums.OrderStatus;
import com.srp.order.pojos.Order;

public class OrderMapper {
    public static OrderEntity getNewEntity(Order source) {
        OrderEntity entity = new OrderEntity();
        entity.setDiscount(source.getDiscounts());
        entity.setDeliveryAddressId(source.getAddressId());
        entity.setCartId(source.getCartId());
        entity.setStatus(OrderStatus.Raised);
        entity.setVoucherId(source.getVoucherId());
        entity.setUserId(source.getUserId());
        return entity;
    }

    public static Order getOrder(OrderEntity source) {
        Order order = new Order();
        order.setId(source.getId());
        order.setCartId(source.getCartId());
        order.setAddressId(source.getDeliveryAddressId());
        order.setUserId(source.getUserId());
        order.setDiscounts(source.getDiscount());
        order.setStatus(source.getStatus());
        order.setVoucherId(source.getVoucherId());
        return order;
    }
}
