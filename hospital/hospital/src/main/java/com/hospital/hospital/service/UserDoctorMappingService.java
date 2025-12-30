package com.hospital.hospital.service;

import com.hospital.hospital.dto.UserDoctorMappingDTO;
import com.hospital.hospital.dto.UserDoctorResponseDTO;
import com.hospital.hospital.entity.TbDoctor;
import com.hospital.hospital.entity.TbUserDoctorMapping;
import com.hospital.hospital.entity.TbUserMaster;
import com.hospital.hospital.repo.DoctorRepository;
import com.hospital.hospital.repo.UserDoctorMappingRepository;
import com.hospital.hospital.repo.UserMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDoctorMappingService {

    private final UserDoctorMappingRepository mappingRepo;
    private final UserMasterRepository userRepo;
    private final DoctorRepository doctorRepo;

    // ============================================================
    // SAVE MAPPING (POST)
    // ============================================================
    public String saveUserDoctorMapping(UserDoctorMappingDTO dto) {

        TbUserMaster user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Delete old mappings first
        mappingRepo.deleteByUser_PkUserId(dto.getUserId());

        // Add new mappings
        for (Long doctorId : dto.getDoctorIds()) {

            TbDoctor doctor = doctorRepo.findById(doctorId)
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));

            TbUserDoctorMapping mapping = new TbUserDoctorMapping();
            mapping.setUser(user);
            mapping.setDoctor(doctor);

            mapping.setStatus(true);   // ← NEW: By default active

            mappingRepo.save(mapping);
        }

        return "Mapping saved successfully!";
    }

    // ============================================================
    // GET ALL MAPPINGS FOR ONE USER
    // ============================================================
    public List<UserDoctorResponseDTO> getDoctorsByUser(Long userId) {

        List<TbUserDoctorMapping> mappings = mappingRepo.findByUser_PkUserId(userId);

        return mappings.stream().map(m -> {
            UserDoctorResponseDTO dto = new UserDoctorResponseDTO();

            dto.setMappingId(m.getPkMappingId());
            dto.setUserId(m.getUser().getPkUserId());
            dto.setDoctorId(m.getDoctor().getPkDoctorId());

            dto.setDoctorName(m.getDoctor().getFullName());
            dto.setPhone(m.getDoctor().getPhone());
            dto.setSpecialty(m.getDoctor().getSpecialty());

            dto.setStatus(m.getStatus());   // ← NEW

            return dto;
        }).toList();
    }

    public String deleteMapping(Long mappingId) {

        TbUserDoctorMapping mapping = mappingRepo.findById(mappingId)
                .orElseThrow(() -> new RuntimeException("Mapping not found"));

        mappingRepo.delete(mapping);

        return "Mapping deleted successfully!";
    }


    // ============================================================
    // GET ALL MAPPINGS (POSTMAN LIST REQUEST)
    // ============================================================
    public List<UserDoctorResponseDTO> getAllMappings() {

        List<TbUserDoctorMapping> mappings = mappingRepo.findAll();

        return mappings.stream().map(m -> {
            UserDoctorResponseDTO dto = new UserDoctorResponseDTO();

            dto.setMappingId(m.getPkMappingId());
            dto.setUserId(m.getUser().getPkUserId());
            dto.setDoctorId(m.getDoctor().getPkDoctorId());

            dto.setDoctorName(m.getDoctor().getFullName());
            dto.setPhone(m.getDoctor().getPhone());
            dto.setSpecialty(m.getDoctor().getSpecialty());

            dto.setStatus(m.getStatus());   // ← NEW

            return dto;
        }).toList();
    }
}
