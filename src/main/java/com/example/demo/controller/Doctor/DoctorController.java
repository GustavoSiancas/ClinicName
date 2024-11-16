package com.example.demo.controller.Doctor;

import com.example.demo.controller.Doctor.request.DoctorRequest;
import com.example.demo.controller.Doctor.response.SimpleResponse;
import com.example.demo.entity.DoctorEntity;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @PostMapping
    public ResponseEntity<DoctorEntity> addDoctor(@RequestBody DoctorRequest doctor) {
        return ResponseEntity.ok(doctorService.save(doctor));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<SimpleResponse>> getAllDoctors(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.getallSimpleDoctors(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateImageDoctor(@PathVariable Long id, @RequestBody String image) {
        return ResponseEntity.ok(doctorService.udpdateImage(id,image));
    }
}
