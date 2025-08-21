package com.example.market_apple.Service.Impl;

import com.example.market_apple.Repository.CustomerRepository;
import com.example.market_apple.Repository.OrderDetailRepository;
import com.example.market_apple.Repository.OrderRepository;
import com.example.market_apple.Repository.ProductRepository;
import com.example.market_apple.Service.OrderService;
import com.example.market_apple.Entity.Order;
import com.example.market_apple.Entity.Product;
import com.example.market_apple.Entity.OrderDetail;
import com.example.market_apple.Entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Order createOrder(Long customerId, List<OrderDetail> orderDetails) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException("Customer not found"));

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderDetails(orderDetails);

        // Tính tổng tiền đơn hàng
        double totalAmount = 0;
        for (OrderDetail detail : orderDetails) {
            Product product = productRepository.findById(detail.getProduct().getId())
                    .orElseThrow(() -> new NoSuchElementException("Product not found"));
            if (product.getStock() < detail.getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
            }
            detail.setOrder(order);
            detail.setUnitPrice(product.getPrice());
            totalAmount += detail.getQuantity() * detail.getUnitPrice();
            // Giảm tồn kho
            product.setStock(product.getStock() - detail.getQuantity());
            productRepository.save(product);
        }
        order.setTotalAmount(totalAmount);

        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Order not found"));
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Order not found"));
        // Hoàn lại tồn kho cho các sản phẩm trong đơn hàng
        for (OrderDetail detail : order.getOrderDetails()) {
            Product product = detail.getProduct();
            product.setStock(product.getStock() + detail.getQuantity());
            productRepository.save(product);
        }
        orderRepository.delete(order);
    }

    @Override
    public Page<Order> getAllOrdersFollowPage(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }
}
