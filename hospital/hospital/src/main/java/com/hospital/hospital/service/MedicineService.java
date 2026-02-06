package com.hospital.hospital.service;

import com.hospital.hospital.dto.MedicineDTO;
import com.hospital.hospital.entity.Medicine;
import com.hospital.hospital.entity.TbDoctor;
import com.hospital.hospital.repo.MedicineRepository;
import com.hospital.hospital.repo.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicineService {

    private final MedicineRepository medicineRepository;
    private final DoctorRepository doctorRepository;

    // 🔹 ADD MEDICINE
    public MedicineDTO addMedicine(MedicineDTO dto) {

        TbDoctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        Medicine medicine = new Medicine();
        medicine.setMedicineName(dto.getMedicineName());
        medicine.setDoctor(doctor);
        medicine.setUnit(dto.getUnit());
        medicine.setStatus(dto.getStatus());

        Medicine saved = medicineRepository.save(medicine);

        MedicineDTO response = new MedicineDTO();
        response.setMedicineId(saved.getMedicineId());
        response.setMedicineName(saved.getMedicineName());
        response.setUnit(saved.getUnit());
        response.setStatus(saved.getStatus());
        response.setDoctorId(doctor.getPkDoctorId());

        return response;
    }

    // 🔹 GET MEDICINES BY DOCTOR ID
    public List<MedicineDTO> getMedicinesByDoctorId(Long doctorId) {

        return medicineRepository.findByDoctor_PkDoctorId(doctorId)
                .stream()
                .map(m -> {
                    MedicineDTO dto = new MedicineDTO();
                    dto.setMedicineId(m.getMedicineId());
                    dto.setMedicineName(m.getMedicineName());
                    dto.setUnit(m.getUnit());
                    dto.setStatus(m.getStatus());
                    dto.setDoctorId(m.getDoctor().getPkDoctorId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // 🔹 UPDATE MEDICINE + STATUS (DOCTOR-WISE | SINGLE LOGIC)
    public MedicineDTO updateDoctorMedicine(
            Long doctorId,
            Long medicineId,
            MedicineDTO dto
    ) {

        Medicine medicine = medicineRepository
                .findByMedicineIdAndDoctor_PkDoctorId(medicineId, doctorId)
                .orElseThrow(() ->
                        new RuntimeException("Medicine not found for this doctor"));

        medicine.setMedicineName(dto.getMedicineName());
        medicine.setUnit(dto.getUnit());
        medicine.setStatus(dto.getStatus());

        Medicine updated = medicineRepository.save(medicine);

        MedicineDTO response = new MedicineDTO();
        response.setMedicineId(updated.getMedicineId());
        response.setMedicineName(updated.getMedicineName());
        response.setUnit(updated.getUnit());
        response.setStatus(updated.getStatus());
        response.setDoctorId(updated.getDoctor().getPkDoctorId());

        return response;
    }
}
