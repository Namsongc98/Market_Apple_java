package com.example.market_apple.Service;

import com.example.market_apple.Entity.Order;
import com.example.market_apple.Entity.OrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    Order createOrder(Long customerId, List<OrderDetail> orderDetails);
    Order getOrderById(Long id);
    List<Order> getAllOrders();
    void deleteOrder(Long id);
    Page<Order> getAllOrdersFollowPage(Pageable pageable);
}
