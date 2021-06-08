package com.srp.order.service;

import com.srp.order.pojos.Order;

public interface OrderService {

    Long placeOrder(Order order);

    boolean deleteOrder(Long id);

    Order getOrderDetails(Long id);
}
