package com.example.demo.controller.Doctor.request;

public record DoctorRequest (
        String fullName,
        String dni,
        String email,
        String phone,
        String photo,
        Float percentage,
        Long clinicId
){
}
