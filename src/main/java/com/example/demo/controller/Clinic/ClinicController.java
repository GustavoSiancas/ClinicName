package com.example.demo.controller.Clinic;

import com.example.demo.entity.ClinicEntity;
import com.example.demo.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clinic")
public class ClinicController {
    @Autowired
    private ClinicService clinicService;

    @PostMapping
    public ResponseEntity<ClinicEntity> createClinic(@RequestBody ClinicEntity clinic){
        return ResponseEntity.ok(clinicService.saveClinic(clinic));
    }
}
