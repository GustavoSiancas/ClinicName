package com.example.demo.controller.Date.request;

import com.example.demo.entity.RoomEntity;
import com.example.demo.entity.StatusEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CompleteDateRequest (
        LocalDateTime dateTimeStart,
        LocalDateTime dateTimeEnd,
        StatusEntity status,
        String description,
        RoomEntity room,
        BigDecimal money,
        Float percentage
){
}
