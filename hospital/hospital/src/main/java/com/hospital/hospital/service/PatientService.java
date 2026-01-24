package com.hospital.hospital.service;

import com.hospital.hospital.dto.PatientDTO;
import com.hospital.hospital.dto.PhoneVerifyDTO;
import com.hospital.hospital.entity.TbClientMaster;
import com.hospital.hospital.entity.TbPatient;
import com.hospital.hospital.repo.ClientMasterRepository;
import com.hospital.hospital.repo.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.hospital.hospital.dto.PatientBasicDTO;   // ✅ ADD THIS
import org.springframework.web.server.ResponseStatusException;

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

    @Transactional
    public TbPatient addPatient(PatientDTO dto, Long clientId) {

        TbClientMaster client = clientRepo.findById(clientId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.BAD_REQUEST,
                                "Client not found"
                        )
                );

        Integer allowedCount = client.getPatientCount();
        if (allowedCount == null || allowedCount <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Patient limit not configured for this client"
            );
        }

        long existingCount = patientRepo.countByClientIdAndPhone(
                clientId,
                dto.getPhone()
        );

        // ✅ MAIN VALIDATION
        if (existingCount >= allowedCount) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Patient limit is full for this mobile number"
            );
        }

        TbPatient p = new TbPatient();
        p.setFullName(dto.getFullName());
        p.setPhone(dto.getPhone());
        p.setGender(dto.getGender());
        p.setDob(dto.getDob());
        p.setAddress(dto.getAddress());
        p.setStatus(dto.getStatus());
        p.setStateId(dto.getStateId());
        p.setClientId(clientId);

        return patientRepo.save(p);
    }

//    public TbPatient addPatient(PatientDTO dto, Long clientId) {
//        TbClientMaster c = new TbClientMaster();
//        TbPatient p = new TbPatient();
//        p.setFullName(dto.getFullName());
//        p.setPhone(dto.getPhone());
//        p.setGender(dto.getGender());
//        p.setDob(dto.getDob());
//        p.setAddress(dto.getAddress());
//        p.setStatus(dto.getStatus());
//        p.setStateId(dto.getStateId());
//
//        // clientId from header
//        p.setClientId(clientId);
//
//        return patientRepo.save(p);
//    }


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
        // 1️⃣ Find client
        TbClientMaster client = clientRepo.findByMobileNo(clientMobileNo)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Long clientId = client.getPkClientId();
        Integer maxPatientCount = client.getPatientCount();

        // 2️⃣ Count TOTAL patients for this client (capacity check)
        int currentCount = (int) patientRepo.countByClientId(clientId);

        // 3️⃣ Check if THIS phone exists for THIS client
        List<TbPatient> patientList =
                patientRepo.findAllByPhoneAndClientId(patientPhone, clientId);

        List<PatientBasicDTO> patients = patientList.stream()
                .map(p -> new PatientBasicDTO(
                        p.getPatientId(),
                        p.getFullName()
                ))
                .toList();

        boolean phoneExists = !patients.isEmpty();
        boolean limitFull = (maxPatientCount != null && currentCount >= maxPatientCount);

        // =====================================================
        // 🔑 FINAL DECISION LOGIC (CORRECT & CLEAR)
        // =====================================================

        // CASE 1️⃣ : Phone EXISTS + Limit FULL
        if (phoneExists && limitFull) {
            return new PhoneVerifyDTO(
                    true,
                    "Patient limit is full",
                    clientId,
                    maxPatientCount,
                    patients
            );
        }

        // CASE 2️⃣ : Phone EXISTS + Limit NOT FULL
        if (phoneExists) {
            return new PhoneVerifyDTO(
                    true,
                    "Patient(s) already exist",
                    clientId,
                    maxPatientCount,
                    patients
            );
        }

        // CASE 3️⃣ : Phone NOT EXISTS + Limit FULL
//        if (limitFull) {
//            return new PhoneVerifyDTO(
//                    false,
//                    "Patient is not registered, please register",
//                    clientId,
//                    maxPatientCount,
//                    null
//            );
//        }

        // CASE 4️⃣ : Phone NOT EXISTS + Limit NOT FULL
        return new PhoneVerifyDTO(
                false,
                "Patient is not registered, please register",
                clientId,
                maxPatientCount,
                null
        );
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
