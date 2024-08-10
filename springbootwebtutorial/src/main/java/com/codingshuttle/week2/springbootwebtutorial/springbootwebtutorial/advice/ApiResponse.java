package com.codingshuttle.week2.springbootwebtutorial.springbootwebtutorial.advice;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiResponse<T> {
    @JsonFormat(pattern =  "hh:mm:ss dd-mm-yyyy")
    private LocalDateTime timestamp;
    private T data;
    private ApiError apiError;


    public ApiResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponse(T data) {
        this();
        this.data = data;
    }

    public ApiResponse(ApiError apiError) {
        this();
        this.apiError = apiError;
    }
}
