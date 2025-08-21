package com.example.market_apple.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // 👈 ẩn field null trong JSON
public class BaseResponse<T> {
    private int status;
    private String message;
    private T data;
    private long timestamp;

    // Constructor đầy đủ
    public BaseResponse(int status, String message, T data, long timestamp) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }

    // ✅ Success response (có data)
    public static <T> BaseResponse<T> success(int status, String message, T data) {
        return BaseResponse.<T>builder()
                .status(status)
                .message(message)
                .data(data)
                .timestamp(System.currentTimeMillis())
                .build();
    }


    // ❌ Error response
    public static <T> BaseResponse<T> error(int status, String message) {
        return BaseResponse.<T>builder()
                .status(status)
                .message(message)
                .data(null)
                .timestamp(System.currentTimeMillis())
                .build();
    }
}
