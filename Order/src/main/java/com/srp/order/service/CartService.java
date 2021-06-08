package com.srp.order.service;


import com.srp.order.pojos.Cart;

public interface CartService {
    Cart getCart(Long userId);

    Cart addItemInCart(Long userId, Long productId);

    Cart addItemInCart(Long userId, Long productId, Long quantity);
}
