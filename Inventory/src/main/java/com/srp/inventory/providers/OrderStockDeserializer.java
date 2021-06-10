package com.srp.inventory.providers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.srp.inventory.pojos.OrderStock;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

import javax.ws.rs.ext.Provider;

@Provider
public class OrderStockDeserializer extends ObjectMapperDeserializer<OrderStock> {
    public OrderStockDeserializer(){
        super(OrderStock.class);
    }
}
