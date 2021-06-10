package com.srp.order.rest;

import com.srp.order.pojos.Cart;
import com.srp.order.service.CartService;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/cart")
public class CartController {

    private final CartService cartService;

    CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GET
    @Path("/{userId}")
    public Cart getCart(@PathParam("userId") Long userId) {
        return cartService.getCartOfUser(userId);
    }

    @GET
    @Path("/add/{userId}/{productId}")
    public Cart addProduct(@PathParam("userId") Long userId, @PathParam("productId") Long productId) {
        return cartService.addItemInCart(userId, productId);
    }

    @DELETE
    @Path("/clear/{userId}/{productId}")
    public Cart clearCartWithProduct(@PathParam("userId") Long userId, @PathParam("productId") Long productId) {
        return cartService.clearCartWithProduct(userId, productId);
    }

    @DELETE
    @Path("/clear/{userId}")
    public Cart clearCart(@PathParam("userId") Long userId) {
        return cartService.clearCart(userId);
    }
    @DELETE
    @Path("/clear/{userId}/{productId}/{quantity}")
    public Cart clearCartWithProduct(@PathParam("userId") Long userId, @PathParam("productId") Long productId, @PathParam("quantity") Long quantity) {
        return cartService.removeNumberOfProductFromCart(userId, productId, quantity);
    }
}
