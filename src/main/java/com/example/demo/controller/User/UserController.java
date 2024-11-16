package com.example.demo.controller.User;

import com.example.demo.controller.User.Request.LoginRequest;
import com.example.demo.controller.User.Request.RegisterRequest;
import com.example.demo.controller.User.Response.LoginResponse;
import com.example.demo.entity.UserEntity;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<UserEntity> register(@RequestBody RegisterRequest registerRequest) {
        return  ResponseEntity.ok(userService.register(registerRequest));
    }
}
