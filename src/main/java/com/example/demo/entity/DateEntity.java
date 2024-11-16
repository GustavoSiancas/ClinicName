package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name="date")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateTimeStart;
    private LocalDateTime dateTimeEnd;

    private String description;

    @Enumerated(EnumType.STRING)
    private StatusEntity status;

    private BigDecimal money;

    @Enumerated(EnumType.STRING)
    private RoomEntity room;

    private Float percentage;

    private BigDecimal money_price;

    @ManyToOne
    @JoinColumn(name = "patient_id",referencedColumnName = "id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "Doctor_id",referencedColumnName = "id")
    private DoctorEntity doctor;

    @ManyToOne
    @JoinColumn(name = "headquarter_id",referencedColumnName = "id")
    private Headquarter headquarter;

    @PrePersist
    protected void onCreate() {
        this.money=new BigDecimal("0");
        this.money_price=new BigDecimal("0");
        this.status=StatusEntity.CITADO;
    }
}
