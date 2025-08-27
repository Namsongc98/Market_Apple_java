package com.example.market_apple.Service.Impl;

import com.example.market_apple.Entity.User;
import com.example.market_apple.Service.UserService;
import com.example.market_apple.Repository.UserRepository;
import com.example.market_apple.Enum.LoginStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User getUserByID(Long id){
         return userRepository.findById(id)
                 .orElseThrow(() -> new NoSuchElementException("No value present"));
    }

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User createUser(String username, String password, String role) {
        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(role)
                .build();
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Transactional
    public User updateUserStatus(Long userId, LoginStatus status) {
        User  user = userRepository.updateStatusAndReturn(userId, status.name());
        return user;
    }

}
