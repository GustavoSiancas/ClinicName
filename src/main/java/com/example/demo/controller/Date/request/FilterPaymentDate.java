package com.example.demo.controller.Date.request;

import java.time.LocalDate;

public record FilterPaymentDate(
        Long idHeadquarter,
        Long idDoctor,
        LocalDate dateStart,
        LocalDate dateEnd
) {
}
