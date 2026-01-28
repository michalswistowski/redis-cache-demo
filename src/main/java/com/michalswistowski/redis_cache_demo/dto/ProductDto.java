package com.michalswistowski.redis_cache_demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductDto(Long id, @NotBlank String name, @Positive BigDecimal price) {
}
