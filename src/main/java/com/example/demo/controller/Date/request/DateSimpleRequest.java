package com.example.demo.controller.Date.request;

import com.example.demo.entity.RoomEntity;

import java.time.LocalDateTime;

public record DateSimpleRequest(
        LocalDateTime dateTime,
        LocalDateTime dateTimeEnd,
        String description,
        RoomEntity room,
        Long Patient_id,
        Long Doctor_id,
        Long Headquarter_id
) {
}
