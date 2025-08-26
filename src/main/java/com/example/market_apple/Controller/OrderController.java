package com.example.market_apple.Controller;

import com.example.market_apple.Dto.BaseResponse;
import com.example.market_apple.Service.OrderService;
import com.example.market_apple.Entity.Order;
import com.example.market_apple.Entity.OrderDetail;
import com.example.market_apple.annotation.NoAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    // Tạo đơn hàng mới với danh sách chi tiết đơn hàng
    @PostMapping
    public ResponseEntity<BaseResponse<Order>> createOrder(@RequestBody OrderRequest orderRequest) {
        Order createdOrder = orderService.createOrder(orderRequest.getCustomerId(), orderRequest.getOrderDetails());
        return ResponseEntity.ok(BaseResponse.success(200,"Get  successfully",createdOrder));
    }

    @GetMapping("/page")
    @NoAuth
    public ResponseEntity<BaseResponse<Page<Order>>> getAllOrdersFollowPage(@PageableDefault(size = 10) Pageable pageable) {
        Page<Order> orders = orderService.getAllOrdersFollowPage(pageable);
        return ResponseEntity.ok(BaseResponse.success(200,"Get successfully",orders));
    }
    // Lấy đơn hàng theo ID
    @GetMapping("/order_id/{id}")
    @NoAuth
    public ResponseEntity<BaseResponse<Order>> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(BaseResponse.success(200,"Get successfully",order));
    }

    // Lấy danh sách tất cả đơn hàng
    @GetMapping
    @NoAuth
    public ResponseEntity<BaseResponse<List<Order>>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(BaseResponse.success(200,"Get successfully",orders));
    }

    // Xóa đơn hàng
    @DeleteMapping("/order_id/{id}")
    public ResponseEntity<BaseResponse<String>> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok(BaseResponse.success(200,"Order deleted successfully",null));
    }
}

class OrderRequest {
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

