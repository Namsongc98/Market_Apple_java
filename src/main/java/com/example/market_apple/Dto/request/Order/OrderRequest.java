package com.example.market_apple.Dto.request.Order;

import com.example.market_apple.Entity.OrderDetail;

import java.util.List;

 public class OrderRequest {
    private Long customerId;
    private List<OrderDetail> orderDetails;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
