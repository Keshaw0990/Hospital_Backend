package com.hospital.hospital.repo;

import com.hospital.hospital.entity.TbPrescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository <TbPrescription, Long> {

}
