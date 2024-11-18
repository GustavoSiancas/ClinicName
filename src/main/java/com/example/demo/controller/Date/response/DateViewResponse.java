package com.example.demo.controller.Date.response;


import java.math.BigDecimal;

public record DateViewResponse (
        Long id,
        String fecha,
        String horaInicio,
        String horaFinal,
        String description,
        String estado,
        String pacientName,
        String doctorName,
        BigDecimal Pago,
        float porcentaje
){
}
