package com.example.market_apple.Entity;

import jakarta.persistence.*;
import lombok.*;
import com.example.market_apple.Enum.LoginStatus;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password; // nên mã hóa bằng BCrypt

    @Column(nullable = false)
    private String role; // ROLE_USER, ROLE_ADMIN

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING) // lưu dạng text thay vì số
    private LoginStatus status; // trạng thái đăng nhập
}