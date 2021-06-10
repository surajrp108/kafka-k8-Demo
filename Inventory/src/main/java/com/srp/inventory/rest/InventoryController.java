package com.srp.inventory.rest;

import com.srp.inventory.pojos.InventoryStock;
import com.srp.inventory.pojos.OrderStock;
import com.srp.inventory.pojos.Stock;
import com.srp.inventory.service.InventoryService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

@Path("/stock")
public class InventoryController {

    InventoryService inventoryService;

    InventoryController(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }

    @GET
    public Multi<InventoryStock> getAllStock(){
        return inventoryService.getAllStockDetails();
    }

    @GET
    @Path("/{productId}")
    public Uni<Long> isStockOfProduct(@PathParam("productId")Long productId){
        return this.inventoryService.getStockCount(productId);
    }

    @POST
    @Path("/new")
    public void addNewStock(Stock stock){
        this.inventoryService.addStock(stock);
    }

    @POST
    @Path("/prepareToOrder")
    public void prepareStockForOrder(OrderStock stock){
        this.inventoryService.addInOrderStock(stock);
    }

}
