package com.hospital.hospital.controller;

import com.hospital.hospital.dto.PatientFollowupDTO;
import com.hospital.hospital.entity.TbPatientFollowUp;
import com.hospital.hospital.service.PatientFollowupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/followup")
@RequiredArgsConstructor
public class PatientFollowupController {

    private final PatientFollowupService service;

    // =========================
    // SAVE FOLLOW-UP
    // =========================
    @PostMapping("/save")
    public ResponseEntity<TbPatientFollowUp> saveFollowup(
            @RequestBody PatientFollowupDTO dto
    ) {
        TbPatientFollowUp saved = service.saveFollowup(dto);
        return ResponseEntity.ok(saved);
    }

    // =========================
    // GET FOLLOW-UP HISTORY BY PATIENT
    // =========================
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<TbPatientFollowUp>> getByPatient(
            @PathVariable Long patientId
    ) {
        List<TbPatientFollowUp> list = service.getByPatientId(patientId);
        return ResponseEntity.ok(list);
    }

    // =========================
    // GET FOLLOW-UP HISTORY BY HOSPITAL
    // =========================
    @GetMapping("/hospital/{clientId}")
    public ResponseEntity<List<TbPatientFollowUp>> getByHospital(
            @PathVariable Long clientId
    ) {
        List<TbPatientFollowUp> list = service.getByClientId(clientId);
        return ResponseEntity.ok(list);
    }

    // =========================
    // TODAY FOLLOW-UP REMINDERS
    // =========================
    @GetMapping("/reminder/today/{clientId}")
    public ResponseEntity<List<TbPatientFollowUp>> getTodayReminders(
            @PathVariable Long clientId
    ) {
        return ResponseEntity.ok(service.getTodayReminders(clientId));
    }

    // =========================
    // UPCOMING FOLLOW-UP REMINDERS
    // =========================
    @GetMapping("/reminder/upcoming/{clientId}")
    public ResponseEntity<List<TbPatientFollowUp>> getUpcomingReminders(
            @PathVariable Long clientId,
            @RequestParam(defaultValue = "7") int days
    ) {
        return ResponseEntity.ok(service.getUpcomingReminders(clientId, days));
    }
}
