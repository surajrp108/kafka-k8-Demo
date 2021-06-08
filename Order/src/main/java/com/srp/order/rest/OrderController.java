package com.srp.order.rest;

import com.srp.order.pojos.Order;
import com.srp.order.service.OrderService;

import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/order")
public class OrderController {

    private final OrderService orderService;

    OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @POST
    @Path("/{id}")
    public Order getOrderDetails(@PathParam("id") Long id){
        return orderService.getOrderDetails(id);
    }

    @POST
    public Long placeOrder(Order order){
        return orderService.placeOrder(order);
    }

    @PATCH
    @Path("/{id}")
    public boolean cancelOder(@PathParam("id") Long id){
        return orderService.deleteOrder(id);
    }

}
