package com.example.demo.repository;

import com.example.demo.entity.DateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DateRepository extends JpaRepository<DateEntity, Long> {
    List<DateEntity> findByDoctor_Id(Long doctorId);

    List<DateEntity> findByDateTimeStart(LocalDateTime dateTimeStart);

    @Query("SELECT d FROM DateEntity d WHERE d.headquarter.id = :headquarterId " +
            "AND (:doctorId IS NULL OR d.doctor.id = :doctorId)")
    List<DateEntity> findByHeadquarterAndOptionalDoctor(
            @Param("headquarterId") Long headquarterId,
            @Param("doctorId") Long doctorId
    );

    @Query("SELECT d FROM DateEntity d WHERE d.headquarter.id = :headquarterId " +
            "AND (:doctorId IS NULL OR d.doctor.id = :doctorId) " +
            "AND (:startDate IS NULL OR d.dateTimeStart >= :startDate) " +
            "AND (:endDate IS NULL OR d.dateTimeStart <= :endDate)")
    List<DateEntity> findByHeadquarterAndOptionalDoctorAndDateRange(
            @Param("headquarterId") Long headquarterId,
            @Param("doctorId") Long doctorId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );



}
