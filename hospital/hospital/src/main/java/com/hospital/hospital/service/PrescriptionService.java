package com.hospital.hospital.service;

import com.hospital.hospital.dto.PrescriptionDTO;
import com.hospital.hospital.entity.Medicine;
import com.hospital.hospital.entity.TbDoctor;
import com.hospital.hospital.entity.TbPatient;
import com.hospital.hospital.entity.TbPrescription;
import com.hospital.hospital.repo.PrescriptionRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final EntityManager entityManager;

    // =========================
    // ADD PRESCRIPTION
    // =========================
    public PrescriptionDTO addPrescription(PrescriptionDTO dto) {

        Medicine medicine = entityManager.getReference(Medicine.class, dto.getMedicineId());
        TbDoctor doctor = entityManager.getReference(TbDoctor.class, dto.getDoctorId());
        TbPatient patient = entityManager.getReference(TbPatient.class, dto.getPatientId());

        TbPrescription prescription = TbPrescription.builder()
                .medicineDoctor(medicine)
                .doctor(doctor)
                .patient(patient)
                .dose(dto.getDose())
                .quantity(dto.getQuantity())
                .instruction(dto.getInstruction())
                .morning(dto.getMorning())
                .afternoon(dto.getAfternoon())
                .evening(dto.getEvening())
                .night(dto.getNight())
                .build();

        TbPrescription saved = prescriptionRepository.save(prescription);
        return mapToDTO(saved);
    }

    // =========================
    // GET ALL PRESCRIPTIONS
    // =========================
    public List<PrescriptionDTO> getAllPrescriptions() {
        return prescriptionRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // =========================
    // DTO MAPPER
    // =========================
    private PrescriptionDTO mapToDTO(TbPrescription p) {

        PrescriptionDTO dto = new PrescriptionDTO();

        dto.setPrescriptionId(p.getPkPrescriptionId());

        // Doctor
        dto.setDoctorId(p.getDoctor().getPkDoctorId());
        dto.setDoctorName(p.getDoctor().getFullName());

        // Medicine
        dto.setMedicineId(p.getMedicineDoctor().getMedicineId()); // ✅ FIX
        dto.setMedicineName(p.getMedicineDoctor().getMedicineName());

        // Patient
        dto.setPatientId(p.getPatient().getPatientId());
        dto.setPatientName(p.getPatient().getFullName()); // ✅ FIX

        dto.setDose(p.getDose());
        dto.setQuantity(p.getQuantity());
        dto.setInstruction(p.getInstruction());

        dto.setMorning(p.getMorning());
        dto.setAfternoon(p.getAfternoon());
        dto.setEvening(p.getEvening());
        dto.setNight(p.getNight());

        return dto;
    }

}
