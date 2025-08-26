package com.example.market_apple.Controller;

import com.example.market_apple.Dto.request.LoginRequest;
import com.example.market_apple.Dto.response.TokenResponse;
import com.example.market_apple.Entity.User;
import com.example.market_apple.Security.JwtUtil;
import com.example.market_apple.Service.AuthService;
import com.example.market_apple.Service.UserService;
import com.example.market_apple.Service.EmailService;
import com.example.market_apple.annotation.NoAuth;
import com.example.market_apple.Enum.LoginStatus;
import jakarta.mail.MessagingException;
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
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private EmailService emailService;

    // API login
    @PostMapping("/login")
    @NoAuth
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws MessagingException {
//        User user = userService.findByEmail(loginRequest.getEmail());
//        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
//            return ResponseEntity.badRequest().body("Invalid username or password");
//        }
//        emailService.sendLoginNotification(user.getUsername(),user.getEmail(),user.getRole(), user.getStatus());
//        String accessToken = jwtUtil.generateAccessToken(user.getUsername());
//        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());
        TokenResponse token = authService.login(loginRequest.getUsername(), loginRequest.getPassword(),loginRequest.getStatus());

        return ResponseEntity.ok(BaseResponse.success(200,"Get successfully",token));
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(BaseResponse.success(201, "Register successfully", token));
    }

    // API refresh token
    @PostMapping("/refresh")
    @NoAuth
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
    @NoAuth
    public ResponseEntity<BaseResponse<TokenResponse>> register(@Valid @RequestBody RegisterRequest req) {
        User user = userService.register(req.getUsername(), req.getPassword(), req.getEmail(), req.getRole());
        String accessToken = jwtUtil.generateAccessToken(user.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());
        TokenResponse token = new TokenResponse(accessToken, refreshToken);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(201, "Register successfully", token));
    }
}
