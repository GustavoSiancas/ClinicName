package com.example.demo.controller.Date.response;

import com.example.demo.entity.RoomEntity;
import com.example.demo.entity.StatusEntity;


import java.time.LocalTime;

public record DatePackResponse (
        String nameDay,
        String nameHour,
        String PatientName,
        String DescripcionDate,
        String module,
        StatusEntity status
){
}
