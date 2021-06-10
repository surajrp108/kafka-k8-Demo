package com.srp.order.service;


import com.srp.order.pojos.Cart;

public interface CartService {
    Cart getCartOfUser(Long userId);

    Cart getCart(Long id);

    Cart addItemInCart(Long userId, Long productId);

    Cart addItemInCart(Long userId, Long productId, Long quantity);

    Cart clearCartWithProduct(Long userId, Long productId);

    Cart clearCart(Long userId);

    Cart removeNumberOfProductFromCart(Long userId, Long productId, Long quantity);
}
