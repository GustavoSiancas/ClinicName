package com.example.demo.repository;

import com.example.demo.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity,Long> {
    List<DoctorEntity> findByClinic_Id(Long id);
}
