package com.example.market_apple.Service;

import com.example.market_apple.Entity.User;

public interface UserService {
    User getUserByID  (Long id);
    User findByUsername(String username);
    User createUser (String username, String password, String role);
    User register(String username, String password,String email, String role);
    User findByEmail(String email);
}
