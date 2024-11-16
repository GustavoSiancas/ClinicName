package com.example.demo.repository;

import com.example.demo.entity.DateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DateRepository extends JpaRepository<DateEntity, Long> {
    List<DateEntity> findByDoctor_Id(Long doctorId);
    List<DateEntity> findByDateTimeStart(LocalDateTime dateTimeStart);
}
