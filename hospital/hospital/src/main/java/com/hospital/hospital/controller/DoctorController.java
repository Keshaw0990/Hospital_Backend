package com.hospital.hospital.controller;

import com.hospital.hospital.dto.DoctorDTO;
import com.hospital.hospital.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService service;

    @PostMapping("/add")
    public ResponseEntity<DoctorDTO> add(@RequestBody DoctorDTO dto) {
        return ResponseEntity.ok(service.addDoctor(dto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<DoctorDTO>> getAll() {
        return ResponseEntity.ok(service.getAllDoctors());
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<DoctorDTO> update(@PathVariable Long id, @RequestBody DoctorDTO dto) {
        return ResponseEntity.ok(service.updateDoctor(id, dto));
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<DoctorDTO>> getByDepartment(@PathVariable Long departmentId) {
        return ResponseEntity.ok(service.getDoctorsByDepartment(departmentId));
    }
}
