package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Table(name="doctor")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DoctorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String DNI;

    private String email;

    private String phone;

    private String photo;

    private Float percentage;

    @ManyToOne
    @JoinColumn(name = "clinic_id",referencedColumnName = "id")
    private ClinicEntity clinic;
}
