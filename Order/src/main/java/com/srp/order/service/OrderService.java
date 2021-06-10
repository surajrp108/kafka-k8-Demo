package com.srp.order.service;

import com.srp.order.pojos.Order;

import javax.transaction.SystemException;

public interface OrderService {

    Long placeOrder(Order order) throws SystemException;

    boolean deleteOrder(Long id);

    Order getOrderDetails(Long id);
}
