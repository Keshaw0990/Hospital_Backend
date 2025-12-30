package com.hospital.hospital.controller;

import com.hospital.hospital.dto.UserDoctorMappingDTO;
import com.hospital.hospital.dto.UserDoctorResponseDTO;
import com.hospital.hospital.service.UserDoctorMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-doctor")
@RequiredArgsConstructor
public class UserDoctorMappingController {

    private final UserDoctorMappingService service;

    // ============================================================
    // SAVE MAPPING (POST)
    // ============================================================
    @PostMapping("/save")
    public ResponseEntity<String> saveMapping(@RequestBody UserDoctorMappingDTO dto) {
        return ResponseEntity.ok(service.saveUserDoctorMapping(dto));
    }

    // ============================================================
    // GET ALL MAPPED DOCTORS FOR ONE USER (GET)
    // ============================================================
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserDoctorResponseDTO>> getMappings(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getDoctorsByUser(userId));
    }

    // ============================================================
    // GET ALL USER–DOCTOR MAPPINGS (GET)
    // ============================================================
    @GetMapping("/all")
    public ResponseEntity<List<UserDoctorResponseDTO>> getAllMappings() {
        return ResponseEntity.ok(service.getAllMappings());
    }

    // DELETE one mapping
    @DeleteMapping("/delete/{mappingId}")
    public ResponseEntity<String> deleteMapping(@PathVariable Long mappingId) {
        return ResponseEntity.ok(service.deleteMapping(mappingId));
    }

}
