package com.example.market_apple.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import  com.example.market_apple.Entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}