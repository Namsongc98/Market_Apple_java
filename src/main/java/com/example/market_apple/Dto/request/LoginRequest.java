package com.example.market_apple.Dto.request;

import com.example.market_apple.Enum.LoginStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    private String username;
    private String password;
    private String email;
    private String role;
    private LoginStatus status;
}