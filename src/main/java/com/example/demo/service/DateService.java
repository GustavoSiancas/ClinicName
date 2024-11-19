package com.example.demo.service;

import com.example.demo.controller.Date.request.*;
import com.example.demo.controller.Date.response.*;
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
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.time.format.TextStyle;
import java.util.Locale;

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
    private BigDecimal payment=BigDecimal.ZERO;

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

        dates.sort((date1, date2) -> {
            // Comparar por fecha y hora
            if (date1.getDateTimeStart().isBefore(date2.getDateTimeStart())) {
                return -1; // date1 es antes que date2
            } else if (date1.getDateTimeStart().isAfter(date2.getDateTimeStart())) {
                return 1;  // date1 es después que date2
            }
            return 0;  // Son iguales
        });

        List<DatePackResponse> responses = new ArrayList<>();
        for (DateEntity dateEntity : dates) {
            if (dateEntity.getDateTimeStart().isAfter(request.dateStart()) && dateEntity.getDateTimeEnd().isBefore(request.dateEnd()) && dateEntity.getHeadquarter().getId().equals(request.HeadquarterId())) {
                Duration duration =Duration.between(dateEntity.getDateTimeStart(), dateEntity.getDateTimeEnd());
                LocalTime timeStart=dateEntity.getDateTimeStart().toLocalTime();
                String nameDay=dateEntity.getDateTimeStart().getDayOfWeek()
                        .getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
                nameDay = nameDay.substring(0, 1).toUpperCase() + nameDay.substring(1).toLowerCase();
                int minutes = (int) duration.toMinutes();
                int intervalCount=minutes/30;
                String PatientName = "Pac: "+dateEntity.getPatient().getFullName();
                String module="Sala: "+dateEntity.getRoom().toString();
                StatusEntity status=dateEntity.getStatus();
                for (int i = 0; i < intervalCount; i++) {
                    String nameHour=timeStart.toString();
                    responses.add(new DatePackResponse(nameDay,nameHour,PatientName,dateEntity.getDescription(),module,status));
                    timeStart=timeStart.plusMinutes(30);
                }

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
    public List<DateViewResponse> getallVDates(FilterAllRequest filter) {
        List<DateViewResponse> responses = new ArrayList<>();
        List<DateEntity> dateEntities=dateRepository.findByHeadquarterAndOptionalDoctorAndDateRange(
                filter.idHeadquarter(),
                filter.idDoctor(),
                filter.date().atStartOfDay(),
                filter.date().plusMonths(1).atTime(23, 59, 59)
        );

        dateEntities.sort((date1, date2) -> {
            // Comparar por fecha y hora
            if (date1.getDateTimeStart().isBefore(date2.getDateTimeStart())) {
                return -1; // date1 es antes que date2
            } else if (date1.getDateTimeStart().isAfter(date2.getDateTimeStart())) {
                return 1;  // date1 es después que date2
            }
            return 0;  // Son iguales
        });
        return mapToList(dateEntities);
    }

    public List<PaymentDateDoctor> getPaymentDates(FilterPaymentDate filter){
        List<DateEntity> dateEntities=dateRepository.findByHeadquarterAndOptionalDoctorAndDateRange(
                filter.idHeadquarter(),
                filter.idDoctor(),
                filter.dateStart().atStartOfDay(),
                filter.dateEnd().atTime(23, 59, 59)
        );
        List<PaymentDateDoctor> responses=new ArrayList<>();
        dateEntities.sort((date1, date2) -> {
            // Comparar por fecha y hora
            if (date1.getDateTimeStart().isBefore(date2.getDateTimeStart())) {
                return -1; // date1 es antes que date2
            } else if (date1.getDateTimeStart().isAfter(date2.getDateTimeStart())) {
                return 1;  // date1 es después que date2
            }
            return 0;  // Son iguales
        });
        this.payment=BigDecimal.ZERO;
        for (DateEntity dateEntity : dateEntities) {
            if (dateEntity.getStatus().equals(StatusEntity.ATENDIDO)) {
                String porcentaje = String.format("%.1f%%", dateEntity.getPercentage() * 100);
                responses.add(new PaymentDateDoctor(
                        dateEntity.getDateTimeStart().toLocalDate(),
                        dateEntity.getDescription(),
                        dateEntity.getPatient().getFullName(),
                        dateEntity.getMoney(),
                        porcentaje,
                        dateEntity.getMoney_price()
                ));
            }
            this.payment=this.payment.add(dateEntity.getMoney_price());
        }
        return responses;
    }


    public List<DateViewResponse> mapToList(List<DateEntity> dateEntities){
        List<DateViewResponse> responses = new ArrayList<>();
        for (DateEntity dateEntity : dateEntities) {
            String fecha=dateEntity.getDateTimeStart().toLocalDate().toString();
            String horaInicio=dateEntity.getDateTimeStart().toLocalTime().toString();
            String horaFin=dateEntity.getDateTimeEnd().toLocalTime().toString();
            responses.add(new DateViewResponse(
                    dateEntity.getId(),
                    fecha,
                    horaInicio,
                    horaFin,
                    dateEntity.getDescription(),
                    dateEntity.getStatus().toString(),
                    dateEntity.getPatient().getFullName(),
                    dateEntity.getDoctor().getFullName(),
                    dateEntity.getMoney(),
                    dateEntity.getPercentage()
            ));
        }
        return responses;
    }

    public DateEntity updateSimpleDateRequest(UpdateSimpleDateRequest request) {
        DateEntity dateEntity=dateRepository.findById(request.id()).orElse(null);
        dateEntity.setStatus(request.status());
        dateEntity.setMoney(request.money());
        dateEntity.setPercentage(request.percentage());
        dateEntity.setTarifa();
        return dateRepository.save(dateEntity);
    }

    public DateEntity updateStatus(Long id, StatusEntity status) {
        DateEntity dateEntity = dateRepository.findById(id).orElse(null);
        dateEntity.setStatus(status);
        return dateRepository.save(dateEntity);
    }

    public boolean deleteDateById(Long id) {
        if (dateRepository.existsById(id)) {
            dateRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Payment getPayment(){
        return new Payment(payment);
    }

}
