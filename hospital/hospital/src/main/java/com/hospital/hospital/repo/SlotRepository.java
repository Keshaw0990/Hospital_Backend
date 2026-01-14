package com.hospital.hospital.repo;

import com.hospital.hospital.entity.TbSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SlotRepository extends JpaRepository<TbSlot, Long> {

    List<TbSlot> findByDoctor_PkDoctorIdAndStatusTrue(Long doctorId);

    long countByDoctor_PkDoctorId(Long doctorId);

}
