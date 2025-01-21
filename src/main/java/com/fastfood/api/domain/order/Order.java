package com.fastfood.api.domain.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private Long id;
    private Long customerId;
    private BigDecimal total;
    private OrderStatus status;
    private Long waitingTimeInMinutes;
    private List<OrderItem> items;
    private LocalDateTime createdAt;

    public Order() {
    }
    public Order(Long id, Long customerId, BigDecimal total, OrderStatus status, Long waitingTimeInMinutes, List<OrderItem> items, LocalDateTime createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.total = total;
        this.status = status;
        this.waitingTimeInMinutes = waitingTimeInMinutes;
        this.items = items;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Long getWaitingTimeInMinutes() {
        return waitingTimeInMinutes;
    }

    public void setWaitingTimeInMinutes(Long waitingTime) {
        this.waitingTimeInMinutes = waitingTime;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
