package com.hospital.hospital.controller;

import com.hospital.hospital.dto.PrescriptionDTO;
import com.hospital.hospital.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    // =========================
    // ADD PRESCRIPTION
    // =========================
    @PostMapping("/add")
    public ResponseEntity<PrescriptionDTO> addPrescription(
            @RequestBody PrescriptionDTO dto
    ) {
        return ResponseEntity.ok(
                prescriptionService.addPrescription(dto)
        );
    }

    // =========================
    // GET ALL PRESCRIPTIONS
    // =========================
    @GetMapping("/all")
    public ResponseEntity<List<PrescriptionDTO>> getAllPrescriptions() {
        return ResponseEntity.ok(
                prescriptionService.getAllPrescriptions()
        );
    }
}
