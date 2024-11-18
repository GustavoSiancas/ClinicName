package com.example.demo.controller.Date;

import com.example.demo.controller.Date.request.*;
import com.example.demo.controller.Date.response.*;
import com.example.demo.entity.DateEntity;
import com.example.demo.entity.StatusEntity;
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

    @PostMapping("/view")
    public ResponseEntity<List<DateViewResponse>> view(@RequestBody FilterAllRequest filter) {
        return ResponseEntity.ok(dateService.getallVDates(filter));
    }

    @PostMapping("/pack")
    public ResponseEntity<List<DatePackResponse>> getdates(@RequestBody FilterDateRequest filter) {
        return ResponseEntity.ok(dateService.getScheduleWithFilter(filter));
    }

    @PostMapping("/doctor/range")
    public ResponseEntity<List<PaymentDateDoctor>> getDoctorbyId(@RequestBody FilterPaymentDate filter) {
        return ResponseEntity.ok(dateService.getPaymentDates(filter));
    }

    @GetMapping("/doctor/money")
    public ResponseEntity<Payment> getDoctorbyMoney() {
        return ResponseEntity.ok(dateService.getPayment());
    }



    @GetMapping("/{id}")
    public ResponseEntity<DateResponse> getDatebyId(@PathVariable Long id) {
        return ResponseEntity.ok(dateService.getDatebyId(id));
    }

    @PutMapping("/status/{id}/{status}")
    public ResponseEntity<DateEntity> updateStatus(@PathVariable Long id, @PathVariable StatusEntity status) {
        return ResponseEntity.ok(dateService.updateStatus(id, status));
    }

    @PutMapping("/simple")
    public ResponseEntity<DateEntity> updateSimple(@RequestBody UpdateSimpleDateRequest request) {
        return ResponseEntity.ok(dateService.updateSimpleDateRequest(request));
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
