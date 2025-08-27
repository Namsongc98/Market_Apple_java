package com.example.market_apple.Service;

import com.example.market_apple.Entity.User;
import com.example.market_apple.Enum.LoginStatus;

public interface UserService {
    User getUserByID  (Long id);
    User findByUsername(String username);
    User createUser (String username, String password, String role);
    User findByEmail(String email);
    User updateUserStatus(Long userId, LoginStatus status);
}
