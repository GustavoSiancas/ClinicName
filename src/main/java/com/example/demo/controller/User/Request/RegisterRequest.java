package com.example.demo.controller.User.Request;

public record RegisterRequest (
        Long clinicId,
        String fullNameHeadquarter,
        String city,
        String username,
        String password
){
}
