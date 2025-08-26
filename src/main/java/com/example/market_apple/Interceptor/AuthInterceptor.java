package com.example.market_apple.Interceptor;

import com.example.market_apple.Dto.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.market_apple.annotation.NoAuth;
import com.example.market_apple.Security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.AccessDeniedException;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
            if (!(handler instanceof HandlerMethod handlerMethod)) {
                return true;
            }

            // Kiểm tra annotation @NoAuth
            NoAuth noAuth = handlerMethod.getMethodAnnotation(NoAuth.class);
            if (noAuth == null) {
                noAuth = handlerMethod.getBeanType().getAnnotation(NoAuth.class);
            }
            if (noAuth != null) {
                return true; // API public, bỏ qua check
            }

            // Lấy token từ header
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String jwtToken = authHeader.substring(7);
                if (jwtUtil.validateToken(jwtToken)) {
                    String username = jwtUtil.extractUsername(jwtToken);
                    // Ở đây anh có thể set vào request attribute để dùng tiếp
                    request.setAttribute("username", username);
                    return true;
                }
            }

            // Token sai hoặc không có → 401
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(mapper.writeValueAsString(
                    BaseResponse.error(HttpServletResponse.SC_UNAUTHORIZED,
                            "Unauthorized - Invalid or missing JWT")));

        return false;
    };
}