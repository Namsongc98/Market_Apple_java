package com.example.market_apple.Controller;

import com.example.market_apple.Dto.BaseResponse;
import com.example.market_apple.Service.OrderDetailService;
import com.example.market_apple.Entity.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import java.util.List;

@RestController
@RequestMapping("/api/order-details")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    // Tạo chi tiết đơn hàng mới
    @PostMapping
    public ResponseEntity<BaseResponse<OrderDetail>> createOrderDetail(@RequestBody OrderDetail orderDetail) {
        OrderDetail createdOrderDetail = orderDetailService.createOrderDetail(orderDetail);
        return ResponseEntity.ok(BaseResponse.success(200,"Post Successfully",createdOrderDetail));
    }

    @GetMapping("/page")
    public ResponseEntity<BaseResponse<Page<OrderDetail>>> getAllOrderDetailsFollowPage(@PageableDefault(size = 10) Pageable pageable) {
        Page<OrderDetail> orderDetails = orderDetailService.getAllOrderDetailsFollowPage(pageable);
        return ResponseEntity.ok(BaseResponse.success(200,"Get Successfully",orderDetails));
    }

    // Lấy chi tiết đơn hàng theo ID
    @GetMapping("/order_detail_id/{id}")
    public ResponseEntity<BaseResponse<OrderDetail>> getOrderDetailById(@PathVariable Long id) {
        OrderDetail orderDetail = orderDetailService.getOrderDetailById(id);
        return ResponseEntity.ok(BaseResponse.success(200,"Get Successfully",orderDetail));
    }

    // Lấy danh sách tất cả chi tiết đơn hàng
    @GetMapping
    public ResponseEntity<BaseResponse<List<OrderDetail>>> getAllOrderDetails() {
        List<OrderDetail> orderDetails = orderDetailService.getAllOrderDetails();
        return ResponseEntity.ok(BaseResponse.success(200,"Get Successfully",orderDetails));
    }

    // Cập nhật chi tiết đơn hàng
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<OrderDetail>> updateOrderDetail(@PathVariable Long id, @RequestBody OrderDetail orderDetailDetails) {
        OrderDetail updatedOrderDetail = orderDetailService.updateOrderDetail(id, orderDetailDetails);
        return ResponseEntity.ok(BaseResponse.success(200,"Put Successfully",updatedOrderDetail));
    }

    // Xóa chi tiết đơn hàng
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<String>> deleteOrderDetail(@PathVariable Long id) {
        orderDetailService.deleteOrderDetail(id);
        return ResponseEntity.ok(BaseResponse.success(204,"OrderDetail deleted successfully",null));
    }
}
