package com.example.market_apple.Entity;

import lombok.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // Tên hoa quả (ví dụ: Táo, Cam)

    @Column(nullable = false)
    private double price; // Giá mỗi kg hoặc đơn vị

    @Column(nullable = false)
    private double stock; // Số lượng tồn kho (kg hoặc đơn vị)

    private String description; // Mô tả (ví dụ: Táo Fuji nhập khẩu)

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails = new ArrayList<>(); // Danh sách chi tiết đơn hàng liên quan
}
