package com.hospital.hospital.controller;

import com.hospital.hospital.dto.PatientDTO;
import com.hospital.hospital.dto.PhoneVerifyDTO;
import com.hospital.hospital.dto.PhoneVerifyRequestDTO;
import com.hospital.hospital.entity.TbClientMaster;
import com.hospital.hospital.entity.TbPatient;
import com.hospital.hospital.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.hospital.hospital.repo.ClientMasterRepository;


import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService service;
    private final ClientMasterRepository clientRepo; // ✅ ADD THIS

    // ===============================
    // ADD PATIENT (NO CHANGE)
    // ===============================
    @PostMapping("/add")
    public TbPatient addPatient(
            @RequestBody PatientDTO dto,
            @RequestHeader("X-CLIENT-ID") Long clientId
    ) {
        return service.addPatient(dto, clientId);
    }

    // ===============================
    // UPDATE PATIENT (NO CHANGE)
    // ===============================
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePatient(
            @PathVariable Long id,
            @RequestBody PatientDTO dto
    ) {
        try {
            return ResponseEntity.ok(service.updatePatient(id, dto));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.internalServerError()
                    .body("Something went wrong while updating patient");
        }
    }

    // ===============================
    // DELETE PATIENT (NO CHANGE)
    // ===============================
    @DeleteMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        service.deletePatient(id);
        return "Patient deleted successfully";
    }

    // ===============================
    // GET ALL (DTO)
    // ===============================
    @GetMapping("/all")
    public List<PatientDTO> getAllPatients() {
        return service.getAllPatients();
    }

    // ===============================
    // GET ONE (DTO)
    // ===============================
    @GetMapping("/{id}")
    public PatientDTO getPatient(@PathVariable Long id) {
        return service.getPatientById(id);
    }

    // ===============================
    // VERIFY PHONE (NO CHANGE)
    // ===============================
//    @PostMapping("/verify-phone")
//    public ResponseEntity<PhoneVerifyDTO> verifyPhone(
//            @RequestBody PhoneVerifyRequestDTO dto
//    ) {
//
//        if (dto == null ||
//                dto.getPhone() == null || dto.getPhone().isBlank() ||
//                dto.getClientMobileNo() == null || dto.getClientMobileNo().isBlank()
//        ) {
//            return ResponseEntity.badRequest()
//                    .body(new PhoneVerifyDTO(
//                            false,
//                            "Patient phone and client mobile are required",
//                            null,
//                            null
//                    ));
//        }
//
//        try {
//            PhoneVerifyDTO response =
//                    service.verifyPhoneByClientMobile(
//                            dto.getPhone(),
//                            dto.getClientMobileNo()
//                    );
//
//            // ✅ DIFFERENT STATUS CODES
//            if (response.isExists()) {
//                return ResponseEntity.ok(response); // 200
//            } else {
//                return ResponseEntity.status(404).body(response); // 404
//            }
//
//        } catch (RuntimeException ex) {
//            return ResponseEntity.status(404)
//                    .body(new PhoneVerifyDTO(
//                            false,
//                            ex.getMessage(),
//                            null,
//                            null
//                    ));
//        } catch (Exception ex) {
//            return ResponseEntity.status(500)
//                    .body(new PhoneVerifyDTO(
//                            false,
//                            "Internal server error",
//                            null,
//                            null
//                    ));
//        }
//    }


    @PostMapping("/verify-phone")
    public ResponseEntity<PhoneVerifyDTO> verifyPhone(
            @RequestBody PhoneVerifyRequestDTO dto
    ) {

        if (dto == null ||
                dto.getPhone() == null || dto.getPhone().isBlank() ||
                dto.getClientMobileNo() == null || dto.getClientMobileNo().isBlank()
        ) {
            return ResponseEntity.badRequest()
                    .body(new PhoneVerifyDTO(
                            false,
                            "Patient phone and client mobile are required",
                            null,
                            null,
                            null
                    ));
        }

        Long clientId = null;

        try {
            clientId = clientRepo.findByMobileNo(dto.getClientMobileNo())
                    .map(TbClientMaster::getPkClientId)
                    .orElse(null);

            PhoneVerifyDTO response =
                    service.verifyPhoneByClientMobile(
                            dto.getPhone(),
                            dto.getClientMobileNo()
                    );

            return response.isExists()
                    ? ResponseEntity.ok(response)
                    : ResponseEntity.status(404).body(response);

        } catch (RuntimeException ex) {
            return ResponseEntity.status(404)
                    .body(new PhoneVerifyDTO(
                            false,
                            ex.getMessage(),
                            clientId,
                            null,
                            null
                    ));
        } catch (Exception ex) {
            return ResponseEntity.status(500)
                    .body(new PhoneVerifyDTO(
                            false,
                            "Internal server error",
                            clientId,
                            null,
                            null
                    ));
        }
    }

}
