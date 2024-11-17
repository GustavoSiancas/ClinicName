package com.example.demo.controller.Doctor.response;

public record DoctorCompleteResponse(
        Long id,
        String fullName,
        String DNI,
        String email,
        String phone,
        String photo,
        Float percentage
) {
}
