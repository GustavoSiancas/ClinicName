package com.example.demo.service;

import com.example.demo.controller.Headquarter.response.HeadquarterResponse;
import com.example.demo.entity.Headquarter;
import com.example.demo.repository.HeadquarterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HeadquarterService {
    @Autowired
    private HeadquarterRepository headquarterRepository;

    public List<HeadquarterResponse> getListbyClinicId(Long clinicId) {
        List<HeadquarterResponse> list = new ArrayList<>();
        for (Headquarter headquarter : headquarterRepository.findAll()) {
            HeadquarterResponse headquarterResponse = new HeadquarterResponse(headquarter.getId(),headquarter.getName());
            list.add(headquarterResponse);
        }
        return list;
    }
}
