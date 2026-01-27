package com.churnInsight.churnInsight.exception;

import java.time.Instant;
import java.util.List;

public record ApiError(
        String error,
        List<ApiFieldError> detalles,
        Instant timestamp
) {
    public record ApiFieldError(String campo, String mensaje) {}
}