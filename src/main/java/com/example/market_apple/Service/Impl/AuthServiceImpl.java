package com.example.market_apple.Service.Impl;

import com.example.market_apple.Dto.response.TokenResponse;
import com.example.market_apple.Repository.UserRepository;
import com.example.market_apple.Security.JwtUtil;
import com.example.market_apple.Service.AuthService;
import com.example.market_apple.Entity.User;
import com.example.market_apple.Enum.LoginStatus;
import com.example.market_apple.Service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.logging.ErrorManager;


@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public TokenResponse login(String username, String password, LoginStatus status) {
        try {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (passwordEncoder.matches(password, user.getPassword())) {
                // gửi mail thông báo login
                user.setStatus(LoginStatus.ONLINE);
                User userUpdate = userRepository.updateStatusAndReturn(user.getId(), user.getStatus().name());
                try {
                    emailService.sendLoginNotification(
                            userUpdate.getUsername(),
                            userUpdate.getEmail(),
                            userUpdate.getRole(),
                            userUpdate.getStatus()
                    );
                } catch (Exception e) {
                    // log lỗi email nhưng không chặn login
                    System.out.println(e);
                }

                String accessToken = jwtUtil.generateAccessToken(user.getUsername());
                String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());

                return new TokenResponse(accessToken, refreshToken);
            } else {
                user.setStatus(LoginStatus.FAILED);
               User userUpdate = userRepository.updateStatusAndReturn(user.getId(), user.getStatus().name());
                throw new RuntimeException("Invalid username or password");
            }
        } catch (Exception e) {
            // trả JSON error thay vì để Exception bay thẳng ra ngoài
            throw new NoSuchElementException("Login failed: " + e.getMessage());
        }
    }

    public User logout(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setStatus(LoginStatus.OFFLINE);
        return userRepository.updateStatusAndReturn(user.getId(), user.getStatus().name());
    }

    public User register(String username, String rawPassword, String email, String role) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }
        String finalRole = (role == null || role.isBlank()) ? "ROLE_USER" : role;
        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(rawPassword))
                .email(email)
                .role(finalRole)
                .build();
        return userRepository.save(user);
    }

}
