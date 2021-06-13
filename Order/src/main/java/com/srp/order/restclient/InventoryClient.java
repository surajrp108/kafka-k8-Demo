package com.srp.order.restclient;

import com.srp.order.pojos.OrderStock;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.List;

@Path("/stock")
@RegisterRestClient
public interface InventoryClient {

    @POST
    @Path("/prepareToOrder")
    public Long prepareStockForOrder(OrderStock stock);

    @POST
    @Path("/orderCancel")
    public void cancelOrder(List<Long> orderIds);

    @POST
    @Path("/orderDeliver")
    public void deliverOder(List<Long> orderIds);
}
