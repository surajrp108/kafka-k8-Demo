package com.srp.product.rest;

import com.fasterxml.jackson.core.json.JsonReadContext;
import com.srp.product.pojos.Product;
import com.srp.product.service.ProductService;

import javax.annotation.PostConstruct;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/product")
public class ProductController {

    ProductService service;

    ProductController(ProductService service){
        this.service = service;
    }

    @GET
    @Path("/{id}")
    public Product getProduct(@PathParam("id") Long id){
        return service.get(id);
    }

    @DELETE
    @Path("/{id}")
    public boolean deleteProduct(@PathParam("id") Long id){
        return service.delete(id);
    }

    @GET
    public List<Product> getAll(){
        return service.getAll();
    }

    @POST
    public Product addNewProduct(Product product){
        Long newId = service.save(product);
        return service.get(newId);
    }
    @PUT
    public Product updateProduct(Product product){
        Long newId = service.update(product);
        return service.get(newId);
    }
}
