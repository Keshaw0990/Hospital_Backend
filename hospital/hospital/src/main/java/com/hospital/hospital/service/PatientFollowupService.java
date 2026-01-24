package com.hospital.hospital.service;

import com.hospital.hospital.dto.PatientFollowupDTO;
import com.hospital.hospital.entity.TbPatientFollowUp;
import com.hospital.hospital.repo.PatientFollowupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientFollowupService {

    private final PatientFollowupRepository repository;

    // =========================
    // SAVE FOLLOW-UP
    // =========================
    public TbPatientFollowUp saveFollowup(PatientFollowupDTO dto) {

        TbPatientFollowUp followUp = new TbPatientFollowUp();

        // patient reference
        followUp.setPatientId(dto.getPatientId());

        // patient details (snapshot)
        followUp.setFullName(dto.getFullName());
        followUp.setPhone(dto.getPhone());
        followUp.setGender(dto.getGender());
        followUp.setDob(dto.getDob());
        followUp.setAddress(dto.getAddress());

        // follow-up details
        followUp.setFollowUpDate(
                dto.getFollowUpDate() != null
                        ? dto.getFollowUpDate()
                        : LocalDate.now()
        );

        followUp.setNextFollowUpDate(dto.getNextFollowUpDate());

        // hospital
        followUp.setClientId(dto.getClientId());

        return repository.save(followUp);
    }

    // =========================
    // GET FOLLOW-UPS BY PATIENT
    // =========================
    public List<TbPatientFollowUp> getByPatientId(Long patientId) {
        return repository.findByPatientId(patientId);
    }

    // =========================
    // GET FOLLOW-UPS BY HOSPITAL
    // =========================
    public List<TbPatientFollowUp> getByClientId(Long clientId) {
        return repository.findByClientId(clientId);
    }

    // =========================
    // TODAY REMINDERS
    // =========================
    public List<TbPatientFollowUp> getTodayReminders(Long clientId) {
        return repository.findByClientIdAndNextFollowUpDate(
                clientId,
                LocalDate.now()
        );
    }

    // =========================
    // UPCOMING REMINDERS
    // =========================
    public List<TbPatientFollowUp> getUpcomingReminders(Long clientId, int days) {

        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusDays(days);

        return repository.findByClientIdAndNextFollowUpDateBetween(
                clientId,
                today,
                endDate
        );
    }
}
