package com.example.demo.repository;

import com.example.demo.entity.Headquarter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface HeadquarterRepository extends JpaRepository<Headquarter, Long> {
    Optional<Headquarter> findByClinic_Id(Long id);
    Optional<Headquarter> findByUser_Id(Long id);
}
