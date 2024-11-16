package com.example.demo.controller.Date.response;

import com.example.demo.entity.RoomEntity;
import com.example.demo.entity.StatusEntity;


import java.time.LocalTime;

public record DatePackResponse (
        Long Id,
        String name,
        LocalTime startDate,
        LocalTime endDate,
        String nameDate,
        RoomEntity module,
        StatusEntity status
){
}
