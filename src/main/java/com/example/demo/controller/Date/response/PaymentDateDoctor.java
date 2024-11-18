package com.example.demo.controller.Date.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaymentDateDoctor (
        LocalDate fechaDate,
        String Descripcion,
        String PatientName,
        BigDecimal plata,
        String Porcentaje,
        BigDecimal Money
){
}
