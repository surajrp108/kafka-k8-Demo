package com.srp.order.service.impl;

import com.srp.order.entity.CartEntity;
import com.srp.order.entity.ItemEntity;
import com.srp.order.pojos.Cart;
import com.srp.order.repository.CartRepository;
import com.srp.order.service.CartService;
import com.srp.order.utils.CartMapper;

import javax.inject.Singleton;

@Singleton
public class CartServiceImpl implements CartService {

    CartRepository cartRepository;

    CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart getCart(Long userId) {
        return cartRepository.findByUserIdAndActive(userId)
                .map(CartMapper::getCart)
                .orElseThrow(() -> new IllegalArgumentException("Cart is empty kindly add product to get cart"));
    }

    @Override
    public Cart addItemInCart(Long userId, Long productId) {
        return addItemInCart(userId, productId, 1L);
    }

    @Override
    public Cart addItemInCart(Long userId, Long productId, Long quantity) {
        CartEntity cartEntity = cartRepository.findByUserIdAndActive(userId).orElse(new CartEntity(userId));

        ItemEntity itemEntity = cartEntity.getItems().stream()
                .filter(x -> x.getProductId().equals(productId))
                .findFirst()
                .orElse(new ItemEntity(productId, 0L, cartEntity));

        itemEntity.addQuantity(quantity);
        cartEntity.getItems().add(itemEntity);
        CartEntity saved = cartRepository.save(cartEntity);

        return CartMapper.getCart(saved);
    }
}
