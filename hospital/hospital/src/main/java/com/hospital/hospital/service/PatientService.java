package com.hospital.hospital.service;

import com.hospital.hospital.dto.PatientDTO;
import com.hospital.hospital.dto.PhoneVerifyDTO;
import com.hospital.hospital.entity.TbClientMaster;
import com.hospital.hospital.entity.TbPatient;
import com.hospital.hospital.repo.ClientMasterRepository;
import com.hospital.hospital.repo.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepo;
    private final ClientMasterRepository clientRepo;

    // ============================================================
    // ENTITY → DTO
    // ============================================================
    private PatientDTO toDTO(TbPatient p) {

        PatientDTO dto = new PatientDTO();

        dto.setPatientId(p.getPatientId());
        dto.setFullName(p.getFullName());
        dto.setPhone(p.getPhone());
        dto.setGender(p.getGender());
        dto.setDob(p.getDob());
        dto.setAddress(p.getAddress());
        dto.setStatus(p.getStatus());
        dto.setStateId(p.getStateId());

        // ✅ CLIENT ID
        dto.setClientId(p.getClientId());

        // ✅ CLIENT MOBILE
        clientRepo.findById(p.getClientId())
                .ifPresent(client ->
                        dto.setClientMobileNo(client.getMobileNo())
                );

        return dto;
    }

    // ============================================================
    // ADD PATIENT (NO CHANGE)
    // ============================================================
    public TbPatient addPatient(PatientDTO dto, Long clientId) {

        TbPatient p = new TbPatient();
        p.setFullName(dto.getFullName());
        p.setPhone(dto.getPhone());
        p.setGender(dto.getGender());
        p.setDob(dto.getDob());
        p.setAddress(dto.getAddress());
        p.setStatus(dto.getStatus());
        p.setStateId(dto.getStateId());

        // clientId from header
        p.setClientId(clientId);

        return patientRepo.save(p);
    }


    // ============================================================
    // UPDATE PATIENT (NO CHANGE)
    // ============================================================
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

    // ============================================================
    // DELETE PATIENT (NO CHANGE)
    // ============================================================
    public void deletePatient(Long id) {
        patientRepo.deleteById(id);
    }

    // ============================================================
    // GET ALL (DTO RESPONSE)
    // ============================================================
    public List<PatientDTO> getAllPatients() {
        return patientRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ============================================================
    // GET ONE (DTO RESPONSE)
    // ============================================================
    public PatientDTO getPatientById(Long id) {

        TbPatient p = patientRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        return toDTO(p);
    }

    // ============================================================
    // VERIFY PHONE (NO CHANGE)
    // ============================================================

//  This is my first corrected code

//    public PhoneVerifyDTO verifyPhoneByClientMobile(
//            String patientPhone,
//            String clientMobileNo
//    ) {
//
//        // 1️⃣ Find client using client mobile number
//        TbClientMaster client = clientRepo.findByMobileNo(clientMobileNo)
//                .orElseThrow(() ->
//                        new RuntimeException("Client not found")
//                );
//
//        Long clientId = client.getPkClientId();
//
//        // 2️⃣ Check patient phone inside same client
//        return patientRepo.findByPhoneAndClientId(patientPhone, clientId)
//                .map(patient -> new PhoneVerifyDTO(
//                        true,
//                        "Patient already exists",
//                        patient.getPatientId(),
//                        clientId
//                ))
//                .orElseGet(() -> new PhoneVerifyDTO(
//                        false,
//                        "Patient not exists",
//                        null,
//                        null
//                ));
//    }





    public PhoneVerifyDTO verifyPhoneByClientMobile(
            String patientPhone,
            String clientMobileNo
    ) {
        // 1️⃣ Find client using client mobile number
        TbClientMaster client = clientRepo.findByMobileNo(clientMobileNo)
                .orElseThrow(() ->
                        new RuntimeException("Client not found")
                );

        Long clientId = client.getPkClientId();

        // 2️⃣ Check patient phone inside same client
        return patientRepo.findByPhoneAndClientId(patientPhone, clientId)
                .map(patient -> new PhoneVerifyDTO(
                        true,
                        "Patient already exists",
                        patient.getPatientId(),
                        clientId
                ))
                .orElseGet(() -> new PhoneVerifyDTO(
                        false,
                        "Patient not exists",
                        null,
                        clientId   // ✅ SEND CLIENT ID EVEN WHEN PATIENT NOT FOUND
                ));
    }


//    public PhoneVerifyDTO verifyPhone(String phone, String OfficeclientId) {
//
//        return patientRepo.findByPhoneAndClientId(phone, OfficeclientId)
//                .map(patient -> new PhoneVerifyDTO(
//                        true,
//                        "Patient already exists",
//                        patient.getPatientId(),
//                        patient.getClientId()   // ✅ RETURN CLIENT ID
//                ))
//                .orElseGet(() -> new PhoneVerifyDTO(
//                        false,
//                        "Patient not found",
//                        null,
//                        clientId                // ✅ still return clientId
//                ));
//    }

}
