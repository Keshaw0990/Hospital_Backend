package com.hospital.hospital.repo;

import com.hospital.hospital.entity.TbPatientFollowUp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PatientFollowupRepository extends JpaRepository<TbPatientFollowUp, Long> {

    // Today reminders
    List<TbPatientFollowUp> findByClientIdAndNextFollowUpDate(
            Long clientId,
            LocalDate nextFollowUpDate
    );

    // Upcoming reminders (range)
    List<TbPatientFollowUp> findByClientIdAndNextFollowUpDateBetween(
            Long clientId,
            LocalDate startDate,
            LocalDate endDate
    );

    List<TbPatientFollowUp> findByPatientId(Long patientId);

    List<TbPatientFollowUp> findByClientId(Long clientId);
}
