package com.example.demo.controller.Date.request;

import java.time.LocalDate;


public record DayDateRequest (
        Long doctorId,
        LocalDate date
){
}
