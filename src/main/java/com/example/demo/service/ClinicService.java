package com.example.demo.service;

import com.example.demo.entity.ClinicEntity;
import com.example.demo.repository.ClinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClinicService {
    @Autowired
    private ClinicRepository clinicRepository;

    public ClinicEntity saveClinic(ClinicEntity clinic) {
        return clinicRepository.save(clinic);
    }

    private Optional<ClinicEntity> getClinicEntityById(Long id) {
        return clinicRepository.findById(id);
    }
}
