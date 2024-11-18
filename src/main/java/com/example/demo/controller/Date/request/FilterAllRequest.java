package com.example.demo.controller.Date.request;

import java.time.LocalDate;

public record FilterAllRequest(
        Long idHeadquarter,
        Long idDoctor,
        LocalDate date
) {
}
