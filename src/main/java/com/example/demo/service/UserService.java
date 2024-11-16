package com.example.demo.service;

import com.example.demo.controller.User.Request.LoginRequest;
import com.example.demo.controller.User.Request.RegisterRequest;
import com.example.demo.controller.User.Response.LoginResponse;
import com.example.demo.entity.ClinicEntity;
import com.example.demo.entity.Headquarter;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.ClinicRepository;
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
    @Autowired
    private ClinicRepository clinicRepository;

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

    public UserEntity register(RegisterRequest registerRequest) {
        ClinicEntity clinic=clinicRepository.findById(registerRequest.clinicId()).orElseThrow(()->new RuntimeException("Clinic no encontrado"));
        UserEntity user=new UserEntity();
        user.setUsername(registerRequest.username());
        user.setPassword(registerRequest.password());
        user=userRepository.save(user);
        Headquarter headquarter=new Headquarter();
        headquarter.setClinic(clinic);
        headquarter.setUser(user);
        headquarter.setName(registerRequest.fullNameHeadquarter());
        headquarter.setCity(registerRequest.city());
        headquarterRepository.save(headquarter);
        return user;
    }

}
