package com.example.market_apple.Entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // Tên khách hàng

    @Column(nullable = false, unique = true)
    private String email; // Email khách hàng

    private String phone; // Số điện thoại

    private String address; // Địa chỉ giao hàng

    @JsonIgnore // Ngăn Jackson serialize trường orders
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>(); // Danh sách đơn hàng của khách
}
