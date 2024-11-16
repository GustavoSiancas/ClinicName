package com.example.demo.controller.User.Response;

public record LoginResponse (
        Long clinicId,
        String clinicName,
        Long headquarterId,
        String headquarterName
){
}
    