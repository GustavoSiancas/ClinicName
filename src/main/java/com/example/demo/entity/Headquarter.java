package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="headquarter")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Headquarter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String city;

    @ManyToOne
    @JoinColumn(name="clinic_id",referencedColumnName = "id")
    private ClinicEntity clinic;

    @OneToOne
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private UserEntity user;
}
