package com.example.market_apple.Service;

import com.example.market_apple.Dto.response.TokenResponse;
import com.example.market_apple.Entity.User;
import com.example.market_apple.Enum.LoginStatus;
import jakarta.mail.MessagingException;

public interface AuthService {
    User register(String username, String password,String email, String role);
    TokenResponse login(String username, String password, LoginStatus status) throws MessagingException;
    User logout(String email) throws MessagingException;
}
