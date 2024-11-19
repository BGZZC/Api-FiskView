package com.fiskview.apifiskview.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data  // Lombok generará los getters, setters, toString(), hashCode(), equals() automáticamente
public class ErrorMessage {
    private int statusCode;
    private String message;
    private String description;
    private LocalDateTime timestamp;

    // Constructor manual
    public ErrorMessage(int statusCode, String message, String description, LocalDateTime timestamp) {
        this.statusCode = statusCode;
        this.message = message;
        this.description = description;
        this.timestamp = timestamp;
    }
}
