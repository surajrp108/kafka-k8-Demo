package com.srp.order.entity;

import com.srp.order.enums.OrderStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class OrderEntity {

    @Id
    @Column
    @GeneratedValue
    private Long id;

    @Column
    private Long cartId;

    @Column
    private Long userId;

    @Column
    private Double discount;

    @Column
    private Long voucherId;

    @Column
    private Long deliveryAddressId;

    @Column
    private OrderStatus status;

    @Column
    private Double netAmountPaid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
    }

    public Long getDeliveryAddressId() {
        return deliveryAddressId;
    }

    public void setDeliveryAddressId(Long deliveryAddressId) {
        this.deliveryAddressId = deliveryAddressId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Double getNetAmountPaid() {
        return netAmountPaid;
    }

    public void setNetAmountPaid(Double netAmountPaid) {
        this.netAmountPaid = netAmountPaid;
    }
}
