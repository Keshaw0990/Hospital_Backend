package com.hospital.hospital.service;

import com.hospital.hospital.dto.PatientDTO;
import com.hospital.hospital.dto.PhoneVerifyDTO;
import com.hospital.hospital.entity.TbPatient;
import com.hospital.hospital.repo.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepo;

    // ADD PATIENT
    public TbPatient addPatient(PatientDTO dto) {

        // 🔴 VALIDATION: phone already exists
        if (patientRepo.existsByPhone(dto.getPhone())) {
            throw new RuntimeException("Patient with this phone number already exists");
        }

        TbPatient p = new TbPatient();

        p.setFullName(dto.getFullName());
        p.setPhone(dto.getPhone());
        p.setGender(dto.getGender());
        p.setDob(dto.getDob());
        p.setAddress(dto.getAddress());
        p.setStatus(dto.getStatus());
        p.setStateId(dto.getStateId());
        return patientRepo.save(p);
    }

    // UPDATE PATIENT
    public TbPatient updatePatient(Long id, PatientDTO dto) {
        TbPatient p = patientRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        p.setFullName(dto.getFullName());
        p.setPhone(dto.getPhone());
        p.setGender(dto.getGender());
        p.setDob(dto.getDob());
        p.setAddress(dto.getAddress());
        p.setStatus(dto.getStatus());
        p.setStateId(dto.getStateId());
        return patientRepo.save(p);
    }

    // DELETE PATIENT
    public void deletePatient(Long id) {
        patientRepo.deleteById(id);
    }

    // GET ALL
    public List<TbPatient> getAllPatients() {
        return patientRepo.findAll();
    }

    // GET ONE
    public TbPatient getPatientById(Long id) {
        return patientRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
    }

    public PhoneVerifyDTO verifyPhone(String phone) {

        return patientRepo.findByPhone(phone)
                .map(patient -> new PhoneVerifyDTO(
                        true,
                        "Patient already exists",
                        patient.getPatientId()
                ))
                .orElseGet(() -> new PhoneVerifyDTO(
                        false,
                        "Patient not found",
                        null
                ));
    }


}
