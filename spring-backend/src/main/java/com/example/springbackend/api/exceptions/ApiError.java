package com.example.springbackend.api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {

    private int code;
    private String status;
    private LocalDateTime timestamp;
    private String message;
}
