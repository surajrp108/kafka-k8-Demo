package com.srp.order.entity;

import com.srp.order.enums.StatusEnum;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class CartEntity {

    @Id
    @Column
    @GeneratedValue
    private Long id;

    @Column
    private Long userId;

    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ItemEntity> items = new HashSet<>();

    @Column
    private StatusEnum status = StatusEnum.Active;

    public CartEntity() {
    }

    public CartEntity(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<ItemEntity> getItems() {
        return items;
    }

    public void setItems(Set<ItemEntity> items) {
        this.items = items;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}
