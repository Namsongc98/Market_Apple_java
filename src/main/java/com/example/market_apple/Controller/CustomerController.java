package com.example.market_apple.Controller;

import com.example.market_apple.Dto.BaseResponse;
import com.example.market_apple.Service.CustomerService;
import com.example.market_apple.Entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Tạo khách hàng mới
    @PostMapping
    public ResponseEntity<BaseResponse<Customer>> createCustomer(@RequestBody Customer customer) {
        Customer createdCustomer = customerService.createCustomer(customer);
        return ResponseEntity.ok(BaseResponse.success(200,"Post Successfully",createdCustomer));
    }

    @GetMapping("/page")
    public ResponseEntity<BaseResponse<Page<Customer>>> getAllCustomersFollowPage(@PageableDefault(size = 10) Pageable pageable) {
        Page<Customer> customers = customerService.getAllCustomersFollowPage(pageable);
        return ResponseEntity.ok(BaseResponse.success(200,"Get successfully",customers));
    }
    // Lấy danh sách tất cả khách hàng
    @GetMapping
    public ResponseEntity<BaseResponse<List<Customer>>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(BaseResponse.success(200,"Get successfully",customers));
    }

    // Lấy khách hàng theo ID
    @GetMapping("/customer_id/{id}")
    public ResponseEntity<BaseResponse<Customer>> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(BaseResponse.success(200,"Get successfully",customer));
    }

    // Cập nhật khách hàng
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<Customer>> updateCustomer(@PathVariable Long id, @RequestBody Customer customerDetails) {
        Customer updatedCustomer = customerService.updateCustomer(id, customerDetails);
        return ResponseEntity.ok(BaseResponse.success(200,"Put successfully",updatedCustomer));
    }

    // Xóa khách hàng
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<String>> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok(BaseResponse.success(204, "Customer deleted successfully", null));
    }
}
