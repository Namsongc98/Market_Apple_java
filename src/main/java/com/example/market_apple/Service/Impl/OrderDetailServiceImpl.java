package com.example.market_apple.Service.Impl;

import com.example.market_apple.Service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.market_apple.Repository.OrderDetailRepository;
import com.example.market_apple.Repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.market_apple.Entity.OrderDetail;
import com.example.market_apple.Entity.Product;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public OrderDetail createOrderDetail(OrderDetail orderDetail) {
        Product product = productRepository.findById(orderDetail.getProduct().getId())
                .orElseThrow(() -> new NoSuchElementException("Product not found"));
        if (product.getStock() < orderDetail.getQuantity()) {
            throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
        }
        orderDetail.setUnitPrice(product.getPrice());
        product.setStock(product.getStock() - orderDetail.getQuantity());
        productRepository.save(product);
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail updateOrderDetail(Long id, OrderDetail orderDetailDetails) {
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("OrderDetail not found"));
        Product product = productRepository.findById(orderDetailDetails.getProduct().getId())
                .orElseThrow(() -> new NoSuchElementException("Product not found"));

        // Hoàn lại tồn kho của sản phẩm cũ
        Product oldProduct = orderDetail.getProduct();
        oldProduct.setStock(oldProduct.getStock() + orderDetail.getQuantity());
        productRepository.save(oldProduct);

        // Kiểm tra tồn kho sản phẩm mới
        if (product.getStock() < orderDetailDetails.getQuantity()) {
            throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
        }
        orderDetail.setProduct(product);
        orderDetail.setQuantity(orderDetailDetails.getQuantity());
        orderDetail.setUnitPrice(product.getPrice());
        product.setStock(product.getStock() - orderDetailDetails.getQuantity());
        productRepository.save(product);
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public void deleteOrderDetail(Long id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("OrderDetail not found"));
        // Hoàn lại tồn kho
        Product product = orderDetail.getProduct();
        product.setStock(product.getStock() + orderDetail.getQuantity());
        productRepository.save(product);
        orderDetailRepository.delete(orderDetail);
    }

    @Override
    public OrderDetail getOrderDetailById(Long id) {
        return orderDetailRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("OrderDetail not found"));
    }

    @Override
    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailRepository.findAll();
    }

    @Override
    public Page<OrderDetail> getAllOrderDetailsFollowPage(Pageable pageable) {
        return orderDetailRepository.findAll(pageable);
    }
}
