package com.hospital.hospital.controller;

import com.hospital.hospital.dto.SlotDTO;
import com.hospital.hospital.service.SlotService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/slots")
@RequiredArgsConstructor
public class SlotController {


    private final SlotService service;

    @PostMapping("/add")
    public ResponseEntity<SlotDTO> add(@RequestBody SlotDTO dto) {
        return ResponseEntity.ok(service.addSlot(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SlotDTO> update(@PathVariable Long id, @RequestBody SlotDTO dto) {
        return ResponseEntity.ok(service.updateSlot(id, dto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<SlotDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.deleteSlot(id);
        return ResponseEntity.ok("Slot deleted");
    }

    @GetMapping("/available")
    public ResponseEntity<List<SlotDTO>> getAvailableSlots(
            @RequestParam Long doctorId,
            @RequestParam LocalDate date
    ) {
        return ResponseEntity.ok(service.getAvailableSlotsByDoctorAndDate(doctorId, date));
    }

}
