package com.srp.order.service.impl;

import com.srp.order.entity.CartEntity;
import com.srp.order.entity.ItemEntity;
import com.srp.order.pojos.Cart;
import com.srp.order.repository.CartRepository;
import com.srp.order.repository.ItemRepository;
import com.srp.order.service.CartService;
import com.srp.order.utils.CartMapper;

import javax.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class CartServiceImpl implements CartService {

    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;

    CartServiceImpl(CartRepository cartRepository, ItemRepository itemRepository) {
        this.cartRepository = cartRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public Cart getCartOfUser(Long userId) {
        return cartRepository.findByUserIdAndActive(userId)
                .map(CartMapper::getCart)
                .orElseThrow(() -> new IllegalArgumentException("Cart is empty kindly add product to get cart"));
    }

    @Override
    public Cart getCart(Long id) {
        return cartRepository.findById(id)
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

    @Override
    public Cart clearCartWithProduct(Long userId, Long productId) {
        CartEntity cartEntity = cartRepository.findByUserIdAndActive(userId).orElse(new CartEntity(userId));

        List<ItemEntity> itemEntity = cartEntity.getItems().stream()
                .filter(x -> x.getProductId().equals(productId))
                .collect(Collectors.toList());

        cartEntity.getItems().removeAll(itemEntity);
        CartEntity saved = cartRepository.save(cartEntity);
        itemRepository.deleteAll(itemEntity);
        return CartMapper.getCart(saved);
    }

    @Override
    public Cart clearCart(Long userId) {
        CartEntity cartEntity = cartRepository.findByUserIdAndActive(userId).orElse(new CartEntity(userId));
        cartRepository.deleteById(cartEntity.getId());
        return null;
    }

    @Override
    public Cart removeNumberOfProductFromCart(Long userId, Long productId, Long quantity) {
        CartEntity cartEntity = cartRepository.findByUserIdAndActive(userId).orElse(new CartEntity(userId));

        List<ItemEntity> itemEntity = cartEntity.getItems().stream()
                .filter(x -> x.getProductId().equals(productId))
                .limit(quantity)
                .collect(Collectors.toList());

        cartEntity.getItems().removeAll(itemEntity);
        CartEntity saved = cartRepository.save(cartEntity);
        itemRepository.deleteAll(itemEntity);
        return CartMapper.getCart(saved);
    }
}
