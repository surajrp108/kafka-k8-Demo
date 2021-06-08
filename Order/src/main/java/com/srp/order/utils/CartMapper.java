package com.srp.order.utils;

import com.srp.order.entity.CartEntity;
import com.srp.order.pojos.Cart;

public class CartMapper {

    public static Cart getCart(CartEntity sourceEntity) {
        Cart cart = new Cart();
        cart.setUserId(sourceEntity.getUserId());
        cart.setId(sourceEntity.getId());
        sourceEntity.getItems().forEach(x-> cart.getItems().add(ItemMapper.getItem(x)));

        return cart;
    }
}
