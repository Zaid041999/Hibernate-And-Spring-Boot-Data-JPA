package com.codingshuttle.week2.springbootwebtutorial.springbootwebtutorial.advice;

import lombok.Builder;
import lombok.Data;
import org.apache.logging.log4j.message.Message;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
public class ApiError {
    private HttpStatus status ;
    private String message;
    private List<String> subError;
}
