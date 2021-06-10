package com.srp.order.rest;

import com.srp.order.pojos.Order;
import com.srp.order.service.OrderService;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.transaction.SystemException;
import javax.ws.rs.*;

@Path("/order")
public class OrderController {

    private final OrderService orderService;

    OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @GET
    @Path("/{id}")
    public Order getOrderDetails(@PathParam("id") Long id){
        return orderService.getOrderDetails(id);
    }

    @POST
    public Long placeOrder(Order order) throws SystemException {
        return orderService.placeOrder(order);
    }

    @PATCH
    @Path("/{id}")
    public boolean cancelOder(@PathParam("id") Long id){
        return orderService.deleteOrder(id);
    }

}
