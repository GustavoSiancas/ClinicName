package com.example.demo.controller.Date.response;

import com.example.demo.entity.RoomEntity;
import com.example.demo.entity.StatusEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DateResponse(
        Long Id,
        LocalDateTime dateTimeStart,
        LocalDateTime dateTimeEnd,
        StatusEntity status,
        String description,
        RoomEntity room,
        BigDecimal money,
        BigDecimal moneyp,
        Float percentage,
        String patientName,
        String doctorName,
        String localName
) {
}
