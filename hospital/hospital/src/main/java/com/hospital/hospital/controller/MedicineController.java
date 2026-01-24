package com.hospital.hospital.controller;

import com.hospital.hospital.dto.MedicineDTO;
import com.hospital.hospital.service.MedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicine")
@RequiredArgsConstructor
public class MedicineController {

    private final MedicineService medicineService;

    // 🔹 ADD MEDICINE
    @PostMapping("/add")
    public ResponseEntity<MedicineDTO> addMedicine(
            @RequestBody MedicineDTO dto
    ) {
        return ResponseEntity.ok(
                medicineService.addMedicine(dto)
        );
    }

    // 🔹 GET MEDICINES BY DOCTOR ID
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<MedicineDTO>> getMedicinesByDoctor(
            @PathVariable Long doctorId
    ) {
        return ResponseEntity.ok(
                medicineService.getMedicinesByDoctorId(doctorId)
        );
    }
}
