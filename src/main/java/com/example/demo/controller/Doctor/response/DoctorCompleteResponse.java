package com.example.demo.controller.Doctor.response;

public record DoctorCompleteResponse(
        String fullName,
        String DNI,
        String email,
        String phone,
        String photo,
        Float percentage
) {
}
