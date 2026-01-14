package com.hospital.hospital.service;

import com.hospital.hospital.dto.DoctorDTO;
import com.hospital.hospital.entity.TbDepartment;
import com.hospital.hospital.entity.TbDoctor;
import com.hospital.hospital.repo.DepartmentRepository;
import com.hospital.hospital.repo.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepo;
    private final DepartmentRepository deptRepo;

    private DoctorDTO toDTO(TbDoctor d) {
        DoctorDTO dto = new DoctorDTO();

        dto.setDoctorId(d.getPkDoctorId());
        dto.setFullName(d.getFullName());
        dto.setPhone(d.getPhone());
        dto.setSpecialty(d.getSpecialty());
        dto.setConsultationDuration(d.getConsultationDuration());

        // ✅ SLOT COUNT MAPPING
        dto.setDayCount(d.getDayCount());

        if (d.getDepartment() != null) {
            dto.setDepartmentId(d.getDepartment().getPkDepartmentId());
            dto.setDepartmentName(d.getDepartment().getName());

            if (d.getDepartment().getClient() != null) {
                dto.setClientId(d.getDepartment().getClient().getPkClientId());
                dto.setOrgName(d.getDepartment().getClient().getOrgName());
            }
        }

        return dto;
    }



    public DoctorDTO addDoctor(DoctorDTO dto) {

        TbDepartment dept = deptRepo.findById(dto.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        TbDoctor doctor = new TbDoctor();
        doctor.setFullName(dto.getFullName());
        doctor.setPhone(dto.getPhone());
        doctor.setSpecialty(dto.getSpecialty());
        doctor.setConsultationDuration(dto.getConsultationDuration());
        doctor.setDepartment(dept);

        // ✅ NEW
        doctor.setDayCount(dto.getDayCount());

        TbDoctor saved = doctorRepo.save(doctor);
        return toDTO(saved);
    }


    public List<DoctorDTO> getAllDoctors() {
        return doctorRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public DoctorDTO updateDoctor(Long id, DoctorDTO dto) {
        TbDoctor doctor = doctorRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        TbDepartment dept = deptRepo.findById(dto.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        doctor.setFullName(dto.getFullName());
        doctor.setPhone(dto.getPhone());
        doctor.setSpecialty(dto.getSpecialty());
        doctor.setConsultationDuration(dto.getConsultationDuration());
        doctor.setDepartment(dept);
        doctor.setDayCount(dto.getDayCount());

        TbDoctor saved = doctorRepo.save(doctor);
        return toDTO(saved);
    }

    // List all doctors in a department
    public List<DoctorDTO> getDoctorsByDepartment(Long departmentId) {
        return doctorRepo.findByDepartment_PkDepartmentId(departmentId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
