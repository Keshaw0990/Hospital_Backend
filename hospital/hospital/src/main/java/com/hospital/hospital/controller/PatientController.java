package com.hospital.hospital.controller;

import com.hospital.hospital.dto.PatientDTO;
import com.hospital.hospital.dto.PhoneVerifyDTO;
import com.hospital.hospital.dto.PhoneVerifyRequestDTO;
import com.hospital.hospital.entity.TbPatient;
import com.hospital.hospital.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService service;

    @PostMapping("/add")
    public TbPatient addPatient(@RequestBody PatientDTO dto) {

        return service.addPatient(dto);
    }

    @PutMapping("/update/{id}")
    public TbPatient updatePatient(@PathVariable Long id, @RequestBody PatientDTO dto) {
        return service.updatePatient(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        service.deletePatient(id);
        return "Patient deleted successfully";
    }

    @GetMapping("/all")
    public List<TbPatient> getAllPatients() {
        return service.getAllPatients();
    }

    @GetMapping("/{id}")
    public TbPatient getPatient(@PathVariable Long id) {
        return service.getPatientById(id);
    }

    @PostMapping("/verify-phone")
    public ResponseEntity<PhoneVerifyDTO> verifyPhone(
            @RequestBody PhoneVerifyRequestDTO dto) {

        // 🔹 basic validation
        if (dto == null || dto.getPhone() == null || dto.getPhone().isBlank()) {
            return ResponseEntity.badRequest()
                    .body(new PhoneVerifyDTO(
                            false,
                            "Phone number is required",
                            null
                    ));
        }

        try {
            PhoneVerifyDTO response = service.verifyPhone(dto.getPhone());
            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            // 🔹 unexpected error
            return ResponseEntity.internalServerError()
                    .body(new PhoneVerifyDTO(
                            false,
                            "Something went wrong. Please try again later",
                            null
                    ));
        }
    }



}
