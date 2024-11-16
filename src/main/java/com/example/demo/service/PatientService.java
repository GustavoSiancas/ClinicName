package com.example.demo.service;

import com.example.demo.controller.Patient.request.PatientRequest;
import com.example.demo.controller.Patient.response.PatientResponse;
import com.example.demo.entity.Patient;
import com.example.demo.repository.ClinicRepository;
import com.example.demo.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private ClinicRepository clinicRepository;

    public List<PatientResponse> findAll(Long id) {
        List<Patient> patients= patientRepository.findByClinic_Id(id);
        List<PatientResponse> patientResponses = new ArrayList<>();
        for (Patient patient : patients) {
            patientResponses.add(new PatientResponse(patient.getId(),patient.getFullName(),patient.getDni(),patient.getPhone()));
        }
        return patientResponses;
    }

    public PatientResponse savePatient(PatientRequest patientRequest) {
        Patient patient = new Patient();
        patient.setFullName(patientRequest.fullname());
        patient.setDni(patientRequest.dni());
        patient.setPhone(patientRequest.phone());
        patient.setClinic(clinicRepository.findById(patientRequest.clinicId()).orElse(null));
        patientRepository.save(patient);
        return new PatientResponse(patient.getId(), patient.getFullName(), patient.getDni(), patient.getPhone());
    }

}
