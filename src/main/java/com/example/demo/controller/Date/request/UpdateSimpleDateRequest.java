package com.example.demo.controller.Date.request;

import com.example.demo.entity.StatusEntity;

import java.math.BigDecimal;

public record UpdateSimpleDateRequest(
        Long id,
        StatusEntity status,
        BigDecimal money,
        float percentage
) {
}
