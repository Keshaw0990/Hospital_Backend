package com.hospital.hospital.repo;

import com.hospital.hospital.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    List<Medicine> findByDoctor_PkDoctorId(Long doctorId);
}
