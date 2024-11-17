package com.example.demo.service;

import com.example.demo.controller.Doctor.request.DoctorRequest;
import com.example.demo.controller.Doctor.response.DoctorCompleteResponse;
import com.example.demo.controller.Doctor.response.SimpleResponse;
import com.example.demo.entity.ClinicEntity;
import com.example.demo.entity.DoctorEntity;
import com.example.demo.repository.ClinicRepository;
import com.example.demo.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private ClinicRepository clinicRepository;


    public DoctorEntity save(DoctorRequest doctorRequest) {
        DoctorEntity doctorEntity = new DoctorEntity();
        ClinicEntity clinicEntity = clinicRepository.findById(doctorRequest.clinicId()).orElse(null);
        doctorEntity.setFullName(doctorRequest.fullName());
        doctorEntity.setDNI(doctorRequest.dni());
        doctorEntity.setEmail(doctorRequest.email());
        doctorEntity.setPhone(doctorRequest.phone());
        doctorEntity.setPhoto(doctorRequest.photo());
        doctorEntity.setPercentage(doctorRequest.percentage());
        doctorEntity.setClinic(clinicEntity);
        return doctorRepository.save(doctorEntity);
    }

    public String udpdateImage(Long id, String image){
        DoctorEntity doctorEntity = doctorRepository.findById(id).orElse(null);
        doctorEntity.setPhoto(image);
        doctorRepository.save(doctorEntity);
        return doctorEntity.getPhoto();
    }

    public List<SimpleResponse> getallSimpleDoctors(Long clinicId){
        List<DoctorEntity> lista= doctorRepository.findByClinic_Id(clinicId);
        List<SimpleResponse> listaSimpleDoctors = new ArrayList<>();
        for (DoctorEntity doctor : lista) {
            listaSimpleDoctors.add(new SimpleResponse(doctor.getId(),doctor.getFullName()));
        }
        return listaSimpleDoctors;
    }

    public List<DoctorCompleteResponse> getAllDoctorsComplete(Long clinicId){
        List<DoctorEntity> lista= doctorRepository.findByClinic_Id(clinicId);
        List<DoctorCompleteResponse> listaCompleteDoctors = new ArrayList<>();
        for (DoctorEntity doctor : lista) {
            listaCompleteDoctors.add(new DoctorCompleteResponse(
                    doctor.getId(),
                    doctor.getFullName(),
                    doctor.getDNI(),
                    doctor.getEmail(),
                    doctor.getPhone(),
                    doctor.getPhoto(),
                    doctor.getPercentage()
            ));
        }
        return listaCompleteDoctors;
    }

}
