package com.example.projet_inaf.model;

import jakarta.persistence.Transient;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorException(
        LocalDateTime LocalDateTime,
        String message,
        @Transient
        int HttpStatus,
        String error
) {
}
