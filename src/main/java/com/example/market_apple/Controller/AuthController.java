package com.example.market_apple.Controller;

import com.example.market_apple.Dto.request.LoginRequest;
import com.example.market_apple.Dto.response.TokenResponse;
import com.example.market_apple.Entity.User;
import com.example.market_apple.Security.JwtUtil;
import com.example.market_apple.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.market_apple.Dto.BaseResponse;
import com.example.market_apple.Dto.request.RegisterRequest;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // API login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.findByEmail(loginRequest.getEmail());
        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }

        String accessToken = jwtUtil.generateAccessToken(user.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());

        return ResponseEntity.ok(new TokenResponse(accessToken, refreshToken));
    }

    // API refresh token
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody TokenResponse tokenRequest) {
        String refreshToken = tokenRequest.getRefreshToken();
        if (jwtUtil.validateToken(refreshToken)) {
            String username = jwtUtil.extractUsername(refreshToken);
            String newAccessToken = jwtUtil.generateAccessToken(username);
            String newRefreshToken = jwtUtil.generateRefreshToken(username);

            return ResponseEntity.ok(new TokenResponse(newAccessToken, newRefreshToken));
        }
        return ResponseEntity.badRequest().body("Invalid refresh token");
    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<TokenResponse>> register(@Valid @RequestBody RegisterRequest req) {
        User user = userService.register(req.getUsername(), req.getPassword(), req.getEmail(), req.getRole());
        String accessToken = jwtUtil.generateAccessToken(user.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());
        TokenResponse token = new TokenResponse(accessToken, refreshToken);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(201, "Register successfully", token));
    }
}
