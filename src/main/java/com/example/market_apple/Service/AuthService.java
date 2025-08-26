package com.example.market_apple.Service;

import com.example.market_apple.Dto.response.TokenResponse;
import com.example.market_apple.Enum.LoginStatus;
import jakarta.mail.MessagingException;

public interface AuthService {
    TokenResponse login(String username, String password, LoginStatus status) throws MessagingException;
}
