package com.hospital.hospital.controller;

import com.hospital.hospital.dto.HolidayDTO;
import com.hospital.hospital.service.HolidayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/holidays")
@RequiredArgsConstructor
public class HolidayController {

    private final HolidayService service;

    // ===============================
    // ADD HOLIDAY
    // ===============================
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody HolidayDTO dto) {
        try {
            return ResponseEntity.ok(service.addHoliday(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ===============================
    // UPDATE HOLIDAY
    // ===============================
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody HolidayDTO dto) {
        try {
            return ResponseEntity.ok(service.updateHoliday(id, dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ===============================
    // GET ALL HOLIDAYS
    // ===============================
    @GetMapping("/all")
    public ResponseEntity<List<HolidayDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // ===============================
    // DELETE HOLIDAY
    // ===============================
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.deleteHoliday(id);
        return ResponseEntity.ok("Holiday deleted");
    }

    // =====================================================
    // ✅ NEW API: GET AVAILABLE DATES FOR DOCTOR
    // =====================================================
    @GetMapping("/available-dates")
    public ResponseEntity<List<LocalDate>> getAvailableDates(
            @RequestParam Long doctorId
    ) {
        return ResponseEntity.ok(
                service.getAvailableDatesForDoctor(doctorId)
        );
    }

}
