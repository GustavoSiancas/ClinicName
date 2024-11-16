package com.example.demo.controller.Date.request;

import java.time.LocalDateTime;

public record FilterDateRequest(
        Long DoctorId,
        Long HeadquarterId,
        LocalDateTime dateStart,
        LocalDateTime dateEnd
) {
}
