package com.example.demo.controller.Patient.request;

public record PatientRequest (
        String fullname,
        String dni,
        String phone,
        Long clinicId
){
}
