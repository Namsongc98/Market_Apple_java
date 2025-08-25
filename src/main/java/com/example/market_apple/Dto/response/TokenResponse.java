package com.example.market_apple.Dto.response;

import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
}
