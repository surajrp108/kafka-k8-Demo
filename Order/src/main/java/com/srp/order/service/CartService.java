package com.srp.order.service;


import com.srp.order.pojos.Cart;

public interface CartService {
    Cart getById(Long cartId);

    Cart getCartByUserId(Long userId);

    Cart addItemInCart(Long userId, Long productId);

    Cart addItemInCart(Long userId, Long productId, Long quantity);
}
