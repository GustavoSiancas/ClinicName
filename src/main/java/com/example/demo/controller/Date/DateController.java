package com.example.demo.controller.Date;

import com.example.demo.controller.Date.request.CompleteDateRequest;
import com.example.demo.controller.Date.request.DateSimpleRequest;
import com.example.demo.controller.Date.request.DayDateRequest;
import com.example.demo.controller.Date.request.FilterDateRequest;
import com.example.demo.controller.Date.response.DatePackResponse;
import com.example.demo.controller.Date.response.DateResponse;
import com.example.demo.entity.DateEntity;
import com.example.demo.service.DateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/date")
public class DateController {
    @Autowired
    private DateService dateService;

    @PostMapping
    public ResponseEntity<DateEntity> saveSimple(@RequestBody DateSimpleRequest request) {
        return ResponseEntity.ok(dateService.saveSimple(request));
    }

    @PostMapping("/pack")
    public ResponseEntity<List<DatePackResponse>> getdates(@RequestBody FilterDateRequest filter) {
        return ResponseEntity.ok(dateService.getScheduleWithFilter(filter));
    }

    @PostMapping("/doctor")
    public ResponseEntity<List<DateResponse>> getDoctorbyId(@RequestBody DayDateRequest dayDateRequest) {
        return ResponseEntity.ok(dateService.getDayDoctor(dayDateRequest));
    }

    @PostMapping("/doctor/money")
    public ResponseEntity<BigDecimal> getDoctorbyMoney(@RequestBody DayDateRequest dayDateRequest) {
        return ResponseEntity.ok(dateService.allMoneyByDoctorDay(dayDateRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DateResponse> getDatebyId(@PathVariable Long id) {
        return ResponseEntity.ok(dateService.getDatebyId(id));
    }



    @PutMapping("/{id}")
    public ResponseEntity<DateEntity> updateDate(@PathVariable Long id, @RequestBody CompleteDateRequest completeDateRequest){
        return ResponseEntity.ok(dateService.updateCompleteDate(id,completeDateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDate(@PathVariable Long id) {
        boolean isDeleted = dateService.deleteDateById(id);

        if (isDeleted) {
            return ResponseEntity.noContent().build(); // HTTP 204: No Content
        } else {
            return ResponseEntity.notFound().build(); // HTTP 404: Not Found
        }
    }


}
