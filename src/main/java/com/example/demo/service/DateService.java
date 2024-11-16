package com.example.demo.service;

import com.example.demo.controller.Date.request.CompleteDateRequest;
import com.example.demo.controller.Date.request.DateSimpleRequest;
import com.example.demo.controller.Date.request.DayDateRequest;
import com.example.demo.controller.Date.request.FilterDateRequest;
import com.example.demo.controller.Date.response.DatePackResponse;
import com.example.demo.controller.Date.response.DateResponse;
import com.example.demo.entity.DateEntity;
import com.example.demo.entity.StatusEntity;
import com.example.demo.repository.DateRepository;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.HeadquarterRepository;
import com.example.demo.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DateService {
    @Autowired
    private DateRepository dateRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private HeadquarterRepository headquarterRepository;

    public DateResponse getDatebyId(Long id) {
        DateEntity date=dateRepository.findById(id).orElse(null);
        DateResponse dateResponse= new DateResponse(
                date.getId(),
                date.getDateTimeStart(),
                date.getDateTimeEnd(),
                date.getStatus(),
                date.getDescription(),
                date.getRoom(),
                date.getMoney(),
                date.getMoney_price(),
                date.getPercentage(),
                date.getPatient().getFullName(),
                date.getDoctor().getFullName(),
                date.getHeadquarter().getName()
        );
        return dateResponse;
    }

    public DateEntity saveSimple(DateSimpleRequest request) {
        // Verificación individual para cada ID
        if (request.Patient_id() == null) {
            throw new IllegalArgumentException("El ID de Patient no puede ser nulo.");
        }

        if (request.Doctor_id() == null) {
            throw new IllegalArgumentException("El ID de Doctor no puede ser nulo.");
        }

        if (request.Headquarter_id() == null) {
            throw new IllegalArgumentException("El ID de Headquarter no puede ser nulo.");
        }

        DateEntity dateEntity = new DateEntity();
        dateEntity.setDateTimeStart(request.dateTime());
        dateEntity.setDateTimeEnd(request.dateTimeEnd());
        dateEntity.setDescription(request.description());
        dateEntity.setRoom(request.room());

        // Buscar las entidades sólo si los IDs no son nulos
        dateEntity.setPatient(patientRepository.findById(request.Patient_id()).orElse(null));
        dateEntity.setDoctor(doctorRepository.findById(request.Doctor_id()).orElse(null));
        dateEntity.setHeadquarter(headquarterRepository.findById(request.Headquarter_id()).orElse(null));

        return dateRepository.save(dateEntity);
    }


    public DateEntity updateCompleteDate(Long id, CompleteDateRequest request) {
        DateEntity dateEntity = dateRepository.findById(id).orElse(null);
        dateEntity.setDateTimeStart(request.dateTimeStart());
        dateEntity.setDateTimeEnd(request.dateTimeEnd());
        dateEntity.setDescription(request.description());
        dateEntity.setRoom(request.room());
        dateEntity.setMoney(request.money());
        dateEntity.setPercentage(request.percentage());
        dateEntity.setStatus(request.status());
        BigDecimal moneyPrice= request.money().multiply(BigDecimal.valueOf(request.percentage()));
        moneyPrice = moneyPrice.setScale(2, RoundingMode.HALF_UP);
        dateEntity.setMoney_price(moneyPrice);
        return dateRepository.save(dateEntity);
    }

    public List<DatePackResponse> getScheduleWithFilter(FilterDateRequest request) {
        List<DateEntity> dates = dateRepository.findByDoctor_Id(request.DoctorId());
        List<DatePackResponse> responses = new ArrayList<>();
        for (DateEntity dateEntity : dates) {
            if (dateEntity.getDateTimeStart().isAfter(request.dateStart()) && dateEntity.getDateTimeEnd().isBefore(request.dateEnd()) && dateEntity.getHeadquarter().getId().equals(request.HeadquarterId())) {
                String dateString = dateEntity.getDateTimeStart().getDayOfWeek().toString()
                        + " "
                        + dateEntity.getDateTimeStart().getDayOfMonth();

                DatePackResponse response=new DatePackResponse(
                        dateEntity.getId(),
                        dateEntity.getPatient().getFullName(),
                        dateEntity.getDateTimeStart().toLocalTime(),
                        dateEntity.getDateTimeEnd().toLocalTime(),
                        dateString,
                        dateEntity.getRoom(),
                        dateEntity.getStatus()
                );
                responses.add(response);
            }
        }
        return responses;
    }

    public List<DateResponse> getDayDoctor(DayDateRequest request) {
        List<DateEntity> dateEntities=dateRepository.findByDoctor_Id(request.doctorId());
        List<DateResponse> responses = new ArrayList<>();
        for (DateEntity date : dateEntities) {
            LocalDate localDate = date.getDateTimeStart().toLocalDate();
            if (localDate.equals(request.date()) && date.getStatus().equals(StatusEntity.ATENDIDO)){
                DateResponse dateResponse= new DateResponse(
                        date.getId(),
                        date.getDateTimeStart(),
                        date.getDateTimeEnd(),
                        date.getStatus(),
                        date.getDescription(),
                        date.getRoom(),
                        date.getMoney(),
                        date.getMoney_price(),
                        date.getPercentage(),
                        date.getPatient().getFullName(),
                        date.getDoctor().getFullName(),
                        date.getHeadquarter().getName()
                );
                responses.add(dateResponse);
            }
        }
        return responses;
    }

    public BigDecimal allMoneyByDoctorDay(DayDateRequest request) {
        BigDecimal money = BigDecimal.ZERO; // Usar BigDecimal.ZERO es más recomendable
        for (DateResponse date : getDayDoctor(request)) {
            // Asumo que la propiedad que contiene el dinero es 'getMoney'
            money = money.add(date.moneyp()); // Asegúrate de que el método se llama getMoney() o algo similar
        }
        return money;
    }

}
