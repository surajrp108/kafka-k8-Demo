package com.srp.order.utils;

import com.srp.order.entity.ItemEntity;
import com.srp.order.pojos.Item;

public class ItemMapper {
    public static Item getItem(ItemEntity sourceEntity) {
        Item item = new Item();
        item.setProductId(sourceEntity.getProductId());
        item.setQuantity(sourceEntity.getQuantity());
        item.setId(sourceEntity.getId());
        return item;
    }
}
