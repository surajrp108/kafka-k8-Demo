package com.srp.inventory.rest;

import com.srp.inventory.pojos.InventoryStock;
import com.srp.inventory.pojos.OrderStock;
import com.srp.inventory.pojos.Stock;
import com.srp.inventory.service.InventoryService;

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
    public List<InventoryStock> getAllStock(){
        return inventoryService.getAllStockDetails();
    }

    @GET
    @Path("/{productId}")
    public boolean isStockOfProduct(@PathParam("productId")Long productId){
        return this.inventoryService.getStockCount(productId) > 0;
    }

    @POST
    @Path("/new")
    public Long addNewStock(Stock stock){
        return this.inventoryService.addStock(stock);
    }

    @POST
    @Path("/prepareToOrder")
    public Long prepareStockForOrder(OrderStock stock){
        return this.inventoryService.addInOrderStock(stock);
    }

}
