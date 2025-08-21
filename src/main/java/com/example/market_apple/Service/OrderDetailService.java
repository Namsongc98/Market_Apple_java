package com.example.market_apple.Service;

import com.example.market_apple.Entity.OrderDetail;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderDetailService {
    OrderDetail createOrderDetail(OrderDetail orderDetail);
    OrderDetail updateOrderDetail(Long id, OrderDetail orderDetailDetails);
    void deleteOrderDetail(Long id);
    OrderDetail getOrderDetailById(Long id);
    List<OrderDetail> getAllOrderDetails();
    Page<OrderDetail> getAllOrderDetailsFollowPage(Pageable pageable);
}
