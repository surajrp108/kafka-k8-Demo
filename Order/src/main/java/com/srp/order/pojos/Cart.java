package com.srp.order.pojos;

import java.util.HashSet;
import java.util.Set;

public class Cart {
    private Long id;
    private Long userId;
    private Set<Item> items = new HashSet<>();

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
}
