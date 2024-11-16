package com.example.demo.controller.Patient;

import com.example.demo.controller.Patient.request.PatientRequest;
import com.example.demo.controller.Patient.response.PatientResponse;
import com.example.demo.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "*")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @PostMapping
    public ResponseEntity<PatientResponse> createPatient(@RequestBody PatientRequest patientRequest) {
        return ResponseEntity.ok(patientService.savePatient(patientRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<PatientResponse>> getAllPatientsbyClinic(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.findAll(id));
    }
}
