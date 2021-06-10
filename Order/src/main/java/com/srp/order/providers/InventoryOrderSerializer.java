package com.srp.order.providers;

import com.srp.order.pojos.InventoryOrder;
import io.quarkus.kafka.client.serialization.ObjectMapperSerializer;

import javax.ws.rs.ext.Provider;

@Provider
public class InventoryOrderSerializer extends ObjectMapperSerializer<InventoryOrder> {
}
