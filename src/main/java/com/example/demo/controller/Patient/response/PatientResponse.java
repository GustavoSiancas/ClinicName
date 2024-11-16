package com.example.demo.controller.Patient.response;

public record PatientResponse(
        Long id,
        String fullname,
        String dni,
        String phone
) {
}
