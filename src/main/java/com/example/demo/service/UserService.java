package com.example.demo.service;

import com.example.demo.controller.User.Request.LoginRequest;
import com.example.demo.controller.User.Response.LoginResponse;
import com.example.demo.entity.Headquarter;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.HeadquarterRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HeadquarterRepository headquarterRepository;

    public LoginResponse login(LoginRequest loginRequest) {
        UserEntity user=userRepository.findByUsername(loginRequest.username()).orElseThrow(()->new RuntimeException("Usuario no encontrado"));
        if (user.getPassword().equals(loginRequest.password())) {
            Headquarter headquarter=headquarterRepository.findByUser_Id(user.getId()).orElseThrow(()->new RuntimeException("Usuario no encontrado"));
            return new LoginResponse(
                    headquarter.getClinic().getId(),
                    headquarter.getClinic().getFullName(),
                    headquarter.getId(),
                    headquarter.getName()
            );
        }
        return null;
    }
}
